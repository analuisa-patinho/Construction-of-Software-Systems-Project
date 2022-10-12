package business.handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.transaction.Transactional;

import business.activity.Activity;
import business.activity.ActivityCatalog;
import business.activity.RegularActivity;
import business.reservation.PaymentDetails;
import business.reservation.Reservation;
import business.reservation.ReservationCatalog;
import business.schedule.Schedule;
import business.schedule.ScheduleCatalog;
import business.session.Session;
import business.utils.MockPayment;
import business.utils.UtilDate;
import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.RegularDTO;
import facade.dtos.ScheduleDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstanceException;
import facade.exceptions.InvalidReservationException;
import facade.exceptions.ScheduleNotFoundException;

/**
 * Handles the buy monthly participation use case: 1) buyParticipation 2)
 * chooseSchedule
 */
@Stateful
public class BuyMonthlyParticipationHandler {

	
	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private ScheduleCatalog sc;
	
	@EJB
	private ReservationCatalog rc;
	
	private Activity a;
	private RegularActivity ra;
	private Schedule schedule;
	
	
	public Collection<RegularDTO> chooseRegularActivity() throws ApplicationException {
		
		try {
			List<RegularActivity> result = ac.getRegularActivities();
			return showRegularActivities(result);
		} catch (Exception e) {
			throw new ApplicationException("Error getting regular activities", e);
		} 	
	}

	/**
	 * Given the activity name, returns a list with info of schedules 
	 * 
	 * @param activityName The name of the activity
	 * @return The pair of the list of schedules with the price of the activity
	 * @throws ApplicationException if an error occurs while attempting to get the data
	 */
	public Pair<List<ScheduleDTO>, Double> getSchedulesInfoForActivity(String activityName) throws ApplicationException {
		
		try {
			a = ac.getActivity(activityName);

			if (a == null) {
				throw new ActivityNotFoundException("Name does not match any activity");
			}

			if (!(a instanceof RegularActivity)) {
				throw new InvalidInstanceException("Selected activity is not regular");
			}

			ra = (RegularActivity) a;

			List<ScheduleDTO> result = scheduleToDTO(ra.getSchedules());
			
			return Pair.with(result, ra.getPrice());
		} catch (Exception e) {
			throw new ApplicationException("Error getting shedule list from activity \"" + activityName + "\"", e);
		}
	}
	
	/**
	 * Given the required data, it creates a reservation for each session during the
	 * given begin and duration
	 * 
	 * @param scheduleID   The schedule ID
	 * @param begin        The date which begins the reservation
	 * @param duration     The duration of the monthly participation
	 * @param email        The email associated to the reservation
	 * @param activityName The name of the activity in which the schedule is
	 *                     associated
	 * @return The string with the data needed to pay the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to do the
	 *                              reservations
	 */
	//@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Pair<PaymentInfoDTO, String> chooseSchedule(int scheduleID, LocalDate begin, int duration, String email)
			throws ApplicationException {
		
		try {
			schedule = sc.getRegularActivitySchedule(scheduleID);
			
			if (schedule == null) {
				throw new ScheduleNotFoundException("Could not find schedule with id = " + scheduleID);
			}

			if (duration > schedule.getDurationInMonths()) {
				throw new InvalidDurationException("The duration of the reservation is invalid");
			}

			Reservation reservation = rc.createReservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					MockPayment.generateMockEntity(), MockPayment.generateMockReference(), a.getPrice() * duration);
			
			//Reservation reservation = new Reservation(UtilDate.toDate(UtilDate.getMockCurrentDate()), email,
					//MockPayment.generateMockEntity(), MockPayment.generateMockReference(), a.getPrice() * duration);

			addReservations(schedule, reservation, UtilDate.toDate(begin), duration);

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
			throw new ApplicationException("Error setting reservation", e);
		}
	}

	/**
	 * With the required data, add the reservation res in the list of reservations
	 * in session
	 * 
	 * @param schedule The schedule
	 * @param res      The reservation to add
	 * @param begin    The date which begins the reservation
	 * @param duration The duration of the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to add the
	 *                              reservation
	 */
	private void addReservations(Schedule schedule, Reservation res, Date begin, int duration)
			throws InvalidReservationException {
		for (Session session : schedule.getSessionsBetween(begin, duration)) {
			if (session.getNumPlaces() <= 0) {
				throw new InvalidReservationException("No available slots");
			}
			session.addReservation(res);
		}
	}

/////////////////// TO DTO ///////////////////
	
	/**
	 * With the given data, creates the list of scheduleDTOs
	 * 
	 * @param schedules The list of schedules
	 * @return The list of scheduleDTOs
	 */
	private List<ScheduleDTO> scheduleToDTO(List<Schedule> schedules) {
		List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
		for (Schedule schedule : schedules) {
			scheduleDTOs.add(new ScheduleDTO(schedule));
		}
		return scheduleDTOs;
	}
	
	private List<RegularDTO> showRegularActivities(List<RegularActivity> rActivities) {
		List<RegularDTO> result = new ArrayList<>();
		for (RegularActivity ra : rActivities)
			result.add(new RegularDTO(ra.getId(), ra.getName(), ra.getNumSessions(), ra.getNumMaxParticipants()));
		return result;
	}
}
