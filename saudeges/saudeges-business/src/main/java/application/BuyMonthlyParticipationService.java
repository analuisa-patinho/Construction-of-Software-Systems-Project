package application;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.handlers.BuyMonthlyParticipationHandler;
import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.RegularDTO;
import facade.dtos.ScheduleDTO;
import facade.exceptions.ApplicationException;
import facade.remote.IBuyMonthlyParticipationServiceRemote;

/**
 * A service that offers the required operations to buy a monthly participation,
 * hiding its implementation from the client.
 *
 */
@Stateless 
public class BuyMonthlyParticipationService implements IBuyMonthlyParticipationServiceRemote{

	/**
	 * The business object facade that handles the use case "Buy Monthly
	 * Participation"
	 */
	@EJB
	private BuyMonthlyParticipationHandler mensalParticipationHandler;
	
	
	@Override
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException {
		return mensalParticipationHandler.chooseRegularActivity();
	}

	/**
	 * Given the activity name, returns the info for schedules of activity
	 * 
	 * @param activityName The name of the activity
	 * @return The pair of the list of schedules with the price of the activity
	 * @throws ApplicationException if an error occurs while attempting to get the data
	 */
	public Pair<List<ScheduleDTO>, Double> getSchedulesInfoForActivity(String activityName) throws ApplicationException {
		return mensalParticipationHandler.getSchedulesInfoForActivity(activityName);
	}
	
	public Collection<ScheduleDTO> getSchedulesForRegular(String activityName) throws ApplicationException {
		return getSchedulesInfoForActivity(activityName).getValue0();
	}
	
	public double getPrice(String activityName) throws ApplicationException {
		return getSchedulesInfoForActivity(activityName).getValue1();
	}

	/**
	 * Given the required data, it creates a reservation for each session during the
	 * given begin and duration
	 * 
	 * @param rasID        The schedule ID
	 * @param begin        The date which begins the reservation
	 * @param duration     The duration of the monthly participation
	 * @param email        The email associated to the reservation
	 * @param activityName The name of the activity in which the schedule is
	 *                     associated
	 * @return The string with the data needed to pay the monthly participation
	 * @throws ApplicationException if an error occurs while attempting to do the
	 *                              reservations
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Pair<PaymentInfoDTO, String> chooseSchedule(int rasID, LocalDate begin, int duration, String email)
			throws ApplicationException {
		return mensalParticipationHandler.chooseSchedule(rasID, begin, duration, email);
	}
	
	public PaymentInfoDTO getPayInfo(int rasID, LocalDate startDate, int duration, String email) throws ApplicationException {
		return chooseSchedule(rasID, startDate, duration, email).getValue0();
	}
	
	public String getPayDayLimit(int rasID, LocalDate startDate, int duration, String email) throws ApplicationException {
		return chooseSchedule(rasID, startDate, duration, email).getValue1();
	}

}
