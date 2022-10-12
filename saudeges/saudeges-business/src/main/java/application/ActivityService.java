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

import business.handlers.CreateActivityHandler;
import business.handlers.SetNewScheduleHandler;
import business.schedule.WeekDayTimeSlot;
import facade.dtos.InstructorDTO;
import facade.dtos.Pair;
import facade.dtos.RegularDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.remote.IActivityServiceRemote;

@Stateless
public class ActivityService implements IActivityServiceRemote {
	
	/**
	 * The business object facade that handles the use case "Create activity"
	 */
	@EJB
	private CreateActivityHandler createActivityHandler;
	/**
	 * The business object facade that handles the use case "Set new Schedule"
	 */
	@EJB
	private SetNewScheduleHandler SetNewScheduleHandler;

	@Override
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException {
		return createActivityHandler.getSpecialities();
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price, int numParticipants) throws ApplicationException {
		createActivityHandler.addActivity(name, specialtyDesignation, isRegular, numSessions, sessionDuration, price,
				numParticipants);

	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price) throws ApplicationException {
		createActivityHandler.addActivity(name, specialtyDesignation, isRegular, numSessions, sessionDuration, price);

	}

	@Override
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException {
		return SetNewScheduleHandler.getRegularActivities();
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void setSchedule(String[] days, String[] times, LocalDate beginDate, int monthlyDuration)
			throws ApplicationException {
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

	@Override
	public Collection<InstructorDTO> chooseTrainer() throws ApplicationException {
		return SetNewScheduleHandler.chooseTrainer();
	}

	@Override
	public void setInstructor(int instructorID, int instructorDuration) throws ApplicationException {
		SetNewScheduleHandler.setInstructor(instructorID, instructorDuration);

	}

	@Override
	public void setActivity(String activityName) throws ApplicationException {
		SetNewScheduleHandler.setActivity(activityName);
	}

}
