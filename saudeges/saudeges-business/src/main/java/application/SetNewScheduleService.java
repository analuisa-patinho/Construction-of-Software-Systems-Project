package application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.handlers.SetNewScheduleHandler;
import business.schedule.WeekDayTimeSlot;
import facade.dtos.InstructorDTO;
import facade.dtos.Pair;
import facade.dtos.RegularDTO;
import facade.exceptions.ApplicationException;
import facade.remote.ISetScheduleServiceRemote;

/**
 * A service that offers the required operations to set new schedule, hiding its
 * implementation from the client.
 *
 */
@Stateless
public class SetNewScheduleService implements ISetScheduleServiceRemote {

	/**
	 * The business object facade that handles the use case "Set new Schedule"
	 */
	@EJB
	private SetNewScheduleHandler SetNewScheduleHandler;
	
	//private String activityName;


	/**
	 * Returns the list of all regular activity names
	 * 
	 * @return list of all occasional activity names
	 * @throws ApplicationException 
	 */
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException {
		
		return SetNewScheduleHandler.getRegularActivities();
	}

	
	/**
	 * Given the required data sets the schedule for the named activity
	 * 
	 * @param activityName       The activity name
	 * @param weekSessionsDates  The list with the weekSessionsDates
	 * @param begin              The beginning date of the schedule
	 * @param monthlyDuration    The monthly duration of the schedule
	 * @param instructorID       The id of the instructor
	 * @param instructorDuration The duration of the instructor instruction
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the schedule
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	/*public void setSchedule(List<Pair<DayOfWeek, LocalTime>> weekSessionsDates,
			LocalDate beginDate, int monthlyDuration) throws ApplicationException {
		
		List<WeekDayTimeSlot> weekSessionTimeSlots = new ArrayList<>();
		for (Pair<DayOfWeek, LocalTime> wsd : weekSessionsDates)
			weekSessionTimeSlots.add(new WeekDayTimeSlot(wsd.getValue0(),wsd.getValue1()));
		
		SetNewScheduleHandler.setSchedule(weekSessionTimeSlots, beginDate, monthlyDuration);
	}*/
	
	public void setSchedule(String[] days, String[] times, LocalDate beginDate, int monthlyDuration) throws ApplicationException {
		List<DayOfWeek> wdays = new ArrayList();
		List<LocalTime> ltimes = new ArrayList();
		List<Pair<DayOfWeek, LocalTime>> weekSessionsDates = new ArrayList();
		
		for (String day : days)
			wdays.add(DayOfWeek.valueOf(day.toUpperCase()));
		
		for(String time : times) 
			ltimes.add(LocalTime.parse(time));
		
		for(int i=0; i<wdays.size(); i++) 
			Pair.with(wdays.get(i), ltimes.get(i));
		
		List<WeekDayTimeSlot> weekSessionTimeSlots = new ArrayList<>();
		for (Pair<DayOfWeek, LocalTime> wsd : weekSessionsDates)
			weekSessionTimeSlots.add(new WeekDayTimeSlot(wsd.getValue0(),wsd.getValue1()));
		
		SetNewScheduleHandler.setSchedule(weekSessionTimeSlots, beginDate, monthlyDuration);
	}
	
	
	public Collection<InstructorDTO> chooseTrainer() throws ApplicationException {
		
		return SetNewScheduleHandler.chooseTrainer();
	}
	
	public void setActivity(String activityName) throws ApplicationException {
		SetNewScheduleHandler.setActivity(activityName);
	}
	
	
	public void setInstructor (int instructorID, int instructorDuration) throws ApplicationException {
		
		SetNewScheduleHandler.setInstructor(instructorID, instructorDuration);
	}
}
