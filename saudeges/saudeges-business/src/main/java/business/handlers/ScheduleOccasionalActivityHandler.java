package business.handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.transaction.Transactional;

import business.activity.ActivityCatalog;
import business.activity.OccasionalActivity;
import business.instructor.Instructor;
import business.instructor.InstructorCatalog;
import business.reservation.PaymentDetails;
import business.reservation.Reservation;
import business.reservation.ReservationCatalog;
import business.session.Session;
import business.specialty.Specialty;
import business.specialty.SpecialtyCatalog;
import business.utils.MockPayment;
import business.utils.UtilDate;
import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalDTO;
import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidNumSessionsException;

/**
 * Handles the schedule occasional activity use case: 1)
 * scheduleOccasionalActivity 2) setScheduleOccasionalActivity 3)
 * concludeScheduleOccasionalActivityOperation
 */
@Stateful
public class ScheduleOccasionalActivityHandler {

	
	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private SpecialtyCatalog sc;
	
	@EJB
	private InstructorCatalog ic;
	
	@EJB
	private ReservationCatalog rc;
	
	private Specialty currentSpecialty;
	private OccasionalActivity currentActivity;
	
	
	/**
	 * Gets all the specialty designations
	 * 
	 * @return The list of the specialty designations
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException {
		
		try {
			List<Specialty> entityList = sc.getAllSpecialties();
			return showSpecialties(entityList);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching sport modalities", e);
		}
	}

	
	/**
	 * Given the specialty designation, return a list with all occasional activity
	 * names ordered by price
	 * 
	 * @param specialtyDesignation The specialty designation
	 * @return list of all occasional activity names
	 * @throws ApplicationException if an error occurs while attempting to get the
	 *                              list
	 */
	public Collection<OccasionalDTO> getOccasionalActivities(String spec) throws ApplicationException {
		
		try {
			currentSpecialty = sc.getSpecialty(spec);
			
			List<OccasionalActivity> result = ac.getOccasionalBySpecialty(currentSpecialty.getID());
			
			if (result.isEmpty()) {
				throw new ActivityNotFoundException("Activity list with specialty does not have activities");
			}
			
			return showOccasionalActivities(result);
		} catch (Exception e) {
			throw new ApplicationException("Error getting list of occasional activities with specialty \"" + spec + "\"", e);
		} 	
	}


	/**
	 * Given the activity name and a list of dates, checks which instructors are
	 * available
	 * 
	 * @param activityName      The activity name
	 * @param beginDateSessions The list of dates
	 * @return list of pairs with the id and the name of the available instructors
	 * @throws ApplicationException if an error occurs while attempting to get the
	 *                              list
	 */
	public Collection<Pair<Integer, String>> setScheduleOccasionalActivity(String activityName, List<Date> beginDateSessions)
			throws ApplicationException {

		try {
			currentActivity = (OccasionalActivity) ac.getActivity(activityName);

			if (currentActivity.getNumSessions() != beginDateSessions.size()) {
				throw new InvalidNumSessionsException("Invalid size of dates for sessions");
			}

			checkSessionsDates(beginDateSessions, currentActivity);
			
			currentActivity.addSessions(beginDateSessions);
		
			List<Instructor> instructorAvailable = ic.getAvailableInstructors(beginDateSessions, currentActivity.getDuration());
			List<Pair<Integer, String>> returnList = new ArrayList<>();
			for (Instructor instructor : instructorAvailable) {
				returnList.add(Pair.with(instructor.getId(), instructor.getName()));
			}
			
			return returnList;
		} catch (Exception e) {
			throw new ApplicationException(
					"Error getting list of available instructors for activity with name \"" + activityName + "\"", e);
		} 
	}
	
	
	public Collection<InstructorDTO> setScheduleOccasionalActivity2(String activityName, List<Date> beginDateSessions)
			throws ApplicationException {

		try {
			currentActivity = (OccasionalActivity) ac.getActivity(activityName);

			if (currentActivity.getNumSessions() != beginDateSessions.size()) {
				throw new InvalidNumSessionsException("Invalid size of dates for sessions");
			}

			checkSessionsDates(beginDateSessions, currentActivity);
			
			currentActivity.addSessions(beginDateSessions);
		
			List<Instructor> instructorAvailable = ic.getAvailableInstructors(beginDateSessions, currentActivity.getDuration());
			
			return showInstructors(instructorAvailable);
		} catch (Exception e) {
			throw new ApplicationException(
					"Error getting list of available instructors for activity with name \"" + activityName + "\"", e);
		} 
	}

	/**
	 * Given the required data, creates a reservation and sets the instructor for
	 * the each session
	 * 
	 * @param activityName The activity name
	 * @param instructorId The id of the chosen instructor
	 * @param email        The email associated to the reservation
	 * @return The string with the data needed to pay the reservation
	 * @throws ApplicationException if an error occurs while attempting to set the
	 *                              reservations
	 */
	
	public Pair<PaymentInfoDTO, String> concludeScheduleOccasionalActivityOperation(int instructorId, String email)
			throws ApplicationException {

		try {
			Instructor instructor = ic.getInstructor(instructorId);
			if (instructor == null) {
				throw new InstructorNotFoundException("Instructor not found with id = " + instructorId);
			}

			Reservation reservation = rc.createReservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					MockPayment.generateMockEntity(), MockPayment.generateMockReference(), currentActivity.getPrice());
			
			//Reservation res = new Reservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					//MockPayment.generateMockEntity(), MockPayment.generateMockReference(), currentActivity.getPrice());

			addReservations(currentActivity, reservation, instructor);

			String pattern = "dd/MM/yyyy HH:mm";
			DateFormat df = new SimpleDateFormat(pattern);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(UtilDate.toDate(UtilDate.getMockCurrentDate()));
			calendar.add(Calendar.DATE, 1);
			Date limitDate = calendar.getTime();
			String limitDateAsString = df.format(limitDate);

			PaymentDetails pd = reservation.getPaymentDetails();
	    	PaymentInfoDTO payInfo = new PaymentInfoDTO(pd.getEntity(), pd.getReference(), pd.getPrice());
	    	Pair<PaymentInfoDTO, String> paymentInfo = new Pair<>(payInfo, limitDateAsString);
			
			return paymentInfo;
		} catch (Exception e) {
			throw new ApplicationException("Error setting reservation for activity with name  \"" + currentActivity.getName() + "\"", e);
		}
	}

	/**
	 * Given the the list of dates and the activity, checks if the dates are not
	 * incompatible
	 * 
	 * @param beginDateSessions The list of dates
	 * @param activity          The activity
	 * @requires activity != null && beginDateSessions != null
	 * @throws InvalidDateException if an error occurs if the dates are incompatible
	 */
	private void checkSessionsDates(List<Date> beginDateSessions, OccasionalActivity activity) throws InvalidDateException {
		Calendar calendar = Calendar.getInstance();
		for (Date date1 : beginDateSessions) {
			calendar.setTime(date1);
			calendar.add(Calendar.MINUTE, activity.getDuration());
			Date end1 = calendar.getTime();

			for (Date date2 : beginDateSessions) {
				calendar.setTime(date2);
				calendar.add(Calendar.MINUTE, activity.getDuration());
				Date end2 = calendar.getTime();

				final String exceptionName = "Session date intersection";

				if (!date1.equals(date2)) {
					if (date1.after(date2)) {
						if (!date1.after(end2)) {
							throw new InvalidDateException(exceptionName);
						}
					} else if (date2.after(date1)) {
						if (!date2.after(end1)) {
							throw new InvalidDateException(exceptionName);
						}
					} else {
						throw new InvalidDateException(exceptionName);
					}
				}
			}
		}
	}

	/**
	 * Adds the reservations and sets the instructor to the sessions of the
	 * occasional activity
	 * 
	 * @param oa         The occasional activity
	 * @param res        The reservation
	 * @param instructor The instructor
	 * @requires oa != null
	 */
	private void addReservations(OccasionalActivity oa, Reservation res, Instructor instructor) {
		for (Session session : oa.getAllSessions()) {
			session.addReservation(res);
			session.setInstructor(instructor);
		}
	}
	
/////////////////// TO DTO ///////////////////
	
	private List<SpecialtyDTO> showSpecialties(List<Specialty> entityList) {
		List<SpecialtyDTO> result = new ArrayList<>();
		for (Specialty s : entityList)
			result.add(new SpecialtyDTO(s.getID(), s.getDesignation(), s.getMinDuration(), s.getCertification().toString()));
		return result;
	}
	
	private List<OccasionalDTO> showOccasionalActivities(List<OccasionalActivity> oActivities) {
		List<OccasionalDTO> result = new ArrayList<>();
		for (OccasionalActivity oa : oActivities)
			result.add(new OccasionalDTO(oa.getId(), oa.getName(), oa.getNumSessions()));
		return result;
	}
	
	private List<InstructorDTO> showInstructors(List<Instructor> ins) {
		List<InstructorDTO> result = new ArrayList<>();
		for (Instructor i : ins)
			result.add(new InstructorDTO(i.getId(), i.getName()));
		return result;
	}
	
	public int getOccasionalSessions() {
		return currentActivity.getNumSessions();
	}
}
