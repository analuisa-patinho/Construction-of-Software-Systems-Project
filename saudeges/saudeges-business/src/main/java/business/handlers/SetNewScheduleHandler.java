package business.handlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.transaction.Transactional;

import business.activity.ActivityCatalog;
import business.activity.RegularActivity;
import business.instructor.Instructor;
import business.instructor.InstructorCatalog;
import business.schedule.Schedule;
import business.schedule.ScheduleCatalog;
import business.schedule.WeekDayTimeSlot;
import business.session.SessionCatalog;
import business.utils.UtilDate;
import facade.dtos.InstructorDTO;
import facade.dtos.Pair;
import facade.dtos.RegularDTO;
import facade.exceptions.ActivityNotFoundException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstructorNotFoundException;
import facade.exceptions.InvalidDateException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidInstructorException;
import facade.exceptions.InvalidNumSessionsException;
import facade.exceptions.UnavailableInstructorException;

/**
 * Handles the regular activity schedule creation use case: 1) createSchedule 2)
 * setSchedule
 * 
 */
@Stateful
public class SetNewScheduleHandler {
	
	@EJB
	private ActivityCatalog ac;
	
	@EJB
	private InstructorCatalog ic;
	
	@EJB
	private SessionCatalog sc;
	
	@EJB
	private ScheduleCatalog skdc;

	private RegularActivity currentActivity;
	private List<WeekDayTimeSlot> currentWeekSessionsDates;
	private LocalDate currentBeginDate; 
	private int currentMonthlyDuration;

	
	/**
	 * Returns the list of all regular activity names
	 * 
	 * @return list of all occasional activity names
	 * @throws ApplicationException 
	 */
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException {
		
		try {
			List<RegularActivity> result = ac.getRegularActivities();
			return showRegularActivities(result);
		} catch (Exception e) {
			throw new ApplicationException("Error getting regular activities", e);
		} 	
	}

	public void setActivity(String activityName) throws ApplicationException {
		try {
			currentActivity = (RegularActivity) ac.getActivity(activityName);
			if (currentActivity == null) 
				throw new ActivityNotFoundException("Name: " + activityName);
		} catch (Exception e) {
			throw new ApplicationException("Error data not valid", e);
		} 		
	}
	
	
	public void setSchedule(List<WeekDayTimeSlot> weekSessionsDates,
			LocalDate beginDate, int monthlyDuration) throws ApplicationException {
		
		try {
			if (weekSessionsDates.size() > currentActivity.getNumSessions()) 
				throw new InvalidNumSessionsException(
						String.format("weekSessionsDates's size (%d) greater than activity's week sessions (%d)",
								weekSessionsDates.size(), currentActivity.getNumSessions()));
			
			this.currentWeekSessionsDates = weekSessionsDates;
			
			if (!beginDate.isAfter(UtilDate.getMockCurrentDate().toLocalDate())) 
				throw new InvalidDateException("Date of beginning is invalid");
			
			this.currentBeginDate = beginDate;
			
			if (monthlyDuration < 1) 
				throw new InvalidDurationException("Monthly duration invalid");
			
			this.currentMonthlyDuration = monthlyDuration;
		} catch (Exception e) {
			throw new ApplicationException("Error data not valid", e);
		} 
	}
	
	/**
	 * Returns the list of all instructors names
	 * 
	 * @return list of all instructors activity names
	 * @throws ApplicationException 
	 */
	public Collection<InstructorDTO> chooseTrainer() throws ApplicationException {
		
		try {
			List<Instructor> result = ic.getAllInstructors();
			return showInstructors(result);
		} catch (Exception e) {
			throw new ApplicationException("Error getting regular activities", e);
		} 
		
	}
	
	
	public void setInstructor (int instructorID, int instructorDuration) throws ApplicationException {
		
		try {
			Instructor i = ic.getInstructor(instructorID);
			if (i == null) 
				throw new InstructorNotFoundException(
						String.format("Instructor with id \"%d\" not found", instructorID));
			
			if (!i.hasRightCertification(currentActivity.getSpecialty())) 
				throw new InvalidInstructorException(
						"Instructor doesn't have the required certification: " + currentActivity.getSpecialty().getCertification());
	
			if (this.currentMonthlyDuration < instructorDuration) {
				throw new InvalidDurationException(
						String.format("Monthly duration (%d) smaller than instructor duration (%d)", this.currentMonthlyDuration,
								instructorDuration));
			}
			
			List<Pair<Date, Date>> timeSlotsInstructor = this.currentActivity.getTimeSlots(instructorDuration, UtilDate.toDate(this.currentBeginDate), this.currentWeekSessionsDates);
			if (!sc.isInsctructorAvailable(i,timeSlotsInstructor))
				throw new UnavailableInstructorException("Instructor is unavailable");
			
			Schedule skd = skdc.createSchedule(this.currentMonthlyDuration, UtilDate.toDate(this.currentBeginDate), 
					this.currentWeekSessionsDates, i, instructorDuration, this.currentActivity.getNumMaxParticipants(), 
					this.currentActivity.getSessionDuration());
			//Schedule skd = new Schedule(this.currentMonthlyDuration, UtilDate.toDate(this.currentBeginDate), this.currentWeekSessionsDates, i, instructorDuration, this.currentActivity.getNumMaxParticipants(), this.currentActivity.getSessionDuration());
			this.currentActivity.addSchedule(skd);
			//Para o caso de querer retornar os dados do horario criado
			//ScheduleDTO skdDTO = new ScheduleDTO(skd);
		} catch (Exception e) {
			throw new ApplicationException("Error setting timetable with name \"" + this.currentActivity.getName() + "\"", e);
		} 
	}
	
//////////////////////////// TO DTO ////////////////////////////
	
	private List<RegularDTO> showRegularActivities(List<RegularActivity> rActivities) {
		List<RegularDTO> result = new ArrayList<>();
		for (RegularActivity ra : rActivities)
			result.add(new RegularDTO(ra.getId(), ra.getName(), ra.getNumSessions(), ra.getNumMaxParticipants()));
		return result;
	}
	
	
	private List<InstructorDTO> showInstructors(List<Instructor> instructors) {
		List<InstructorDTO> result = new ArrayList<>();
		for (Instructor i : instructors)
			result.add(new InstructorDTO(i.getId(), i.getName()));
		return result;
	}


}