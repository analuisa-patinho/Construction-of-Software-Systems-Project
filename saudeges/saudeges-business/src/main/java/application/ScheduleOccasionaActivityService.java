package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.handlers.ScheduleOccasionalActivityHandler;
import business.utils.UtilDate;
import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalDTO;
import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateFormatException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;

/**
 * A service that offers the required operations to schedule an occasional
 * activity, hiding its implementation from the client.
 *
 */
@Stateless 
public class ScheduleOccasionaActivityService implements IScheduleOccasionalActivityServiceRemote {

	/**
	 * The business object facade that handles the use case "Schedule Occasional
	 * Activity"
	 */
	@EJB
	private ScheduleOccasionalActivityHandler handler;


	/**
	 * Gets all the specialty designations
	 * 
	 * @return The list of the specialty designations
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException {
		return handler.getSpecialities();
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
	public Collection<OccasionalDTO> getOccasionalActivities(String specialtyDesignation) throws ApplicationException {
		return handler.getOccasionalActivities(specialtyDesignation);
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
	public Collection<Pair<Integer, String>> setScheduleOccasionalActivity(String activityName, List<Date> beinDateSessions)
			throws ApplicationException {
		return handler.setScheduleOccasionalActivity(activityName, beinDateSessions);
	}
	
	@Override
	public Collection<InstructorDTO> getInstructors(String activityName, Collection<String> beinDateSessions)
			throws ApplicationException, InvalidDateFormatException {
		List<Date> dates = new ArrayList<>();
		
		for(String ldt : beinDateSessions) {
			dates.add(UtilDate.parse(ldt));
		}
		
		return handler.setScheduleOccasionalActivity2(activityName, dates);
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
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Pair<PaymentInfoDTO, String> concludeScheduleOccasionalActivityOperation(int instructorId, String email)
			throws ApplicationException {
		return handler.concludeScheduleOccasionalActivityOperation(instructorId, email);
	}

	@Override
	public PaymentInfoDTO getPayInfo(int instructorID, String email) throws ApplicationException {
		return concludeScheduleOccasionalActivityOperation(instructorID, email).getValue0();
	}

	@Override
	public String getPayDayLimit(int instructorID, String email) throws ApplicationException {
		return concludeScheduleOccasionalActivityOperation(instructorID, email).getValue1();
	}

	@Override
	public int getNrSessions() throws ApplicationException {
		return handler.getOccasionalSessions();
	}

}
