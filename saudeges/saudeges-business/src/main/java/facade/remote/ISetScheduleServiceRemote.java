package facade.remote;

import java.time.LocalDate;
import java.util.Collection;

import javax.ejb.Remote;

import facade.dtos.InstructorDTO;
import facade.dtos.RegularDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface ISetScheduleServiceRemote {
	
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException;
	
	//public void setSchedule(List<Pair<DayOfWeek, LocalTime>> weekSessionsDates,
		//	LocalDate beginDate, int monthlyDuration) throws ApplicationException;
	
	public void setSchedule(String[] days, String[] times, LocalDate beginDate, int monthlyDuration) throws ApplicationException;
	
	public Collection<InstructorDTO> chooseTrainer() throws ApplicationException;
	
	public void setInstructor (int instructorID, int instructorDuration) throws ApplicationException;

	public void setActivity(String activityName) throws ApplicationException;

}
