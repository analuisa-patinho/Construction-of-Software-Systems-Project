package facade.remote;

import java.time.LocalDate;
import java.util.Collection;

import javax.ejb.Remote;

import facade.dtos.InstructorDTO;
import facade.dtos.RegularDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IActivityServiceRemote {
	
public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException;
	
	public void addActivity(String specialtyDesignation, String activityName, boolean isRegular, int numSessions,
			int sessionDuration, long price, int numParticipants) throws ApplicationException;
	
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price) throws ApplicationException;

	public Collection<RegularDTO> getRegularActivities() throws ApplicationException;
	
	public void setSchedule(String[] days, String[] times, LocalDate beginDate, int monthlyDuration) throws ApplicationException;
	
	public Collection<InstructorDTO> chooseTrainer() throws ApplicationException;
	
	public void setInstructor (int instructorID, int instructorDuration) throws ApplicationException;

	public void setActivity(String activityName) throws ApplicationException;

}
