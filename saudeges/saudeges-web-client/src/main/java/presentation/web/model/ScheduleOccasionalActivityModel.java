package presentation.web.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateFormatException;
import facade.remote.IScheduleOccasionalActivityServiceRemote;

public class ScheduleOccasionalActivityModel extends Model {
	
	private Iterable<OccasionalDTO> occasionalActivities;
	private String activityName;
	private Iterable<SpecialtyDTO> spcecialties;
	private String specialty;
	private String date;
	private Collection<String> dates;    
	private Iterable<InstructorDTO> instructors; 
	private String instructorID;
	private String email;
	private String cost;
	private String reference;
	private String entity;
	private String payDay;
	private IScheduleOccasionalActivityServiceRemote occasionalActService;
	private int nrsSessions;
	
	
	public void setOccasionalActivityService(IScheduleOccasionalActivityServiceRemote occasionalActService) {
		this.occasionalActService = occasionalActService;
	}
	
	public Iterable<OccasionalDTO> getOccasionalActivities() {
		try {
			return occasionalActService.getOccasionalActivities(this.specialty);
		} catch (ApplicationException e) {
			return new LinkedList<OccasionalDTO>();
		}
	}
	
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public String getActivityName() {
		return this.activityName;
	}
	
	
	public Iterable<SpecialtyDTO> getSpecialties() {
		try {
			return occasionalActService.getSpecialities();
		} catch (ApplicationException e) {
			return new LinkedList<SpecialtyDTO>();
		}
	}
	
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public String getSpecialty() {
		return this.specialty;
	}
	
	
	public void setOccasionalActivities(Collection<OccasionalDTO> occasionalActivities) {
		this.occasionalActivities = occasionalActivities;
	}
	
	public void setNrsSessions (int sessions) {
		this.nrsSessions = sessions;
	}
	
	
	public Iterable<InstructorDTO> getInstructors() {
		try {
			return occasionalActService.getInstructors(this.activityName, this.dates);
		} catch (ApplicationException | InvalidDateFormatException e) {
			return new LinkedList<InstructorDTO>();
		}
	}
	
	public void setInstructors(Collection<InstructorDTO> instructors) {
		this.instructors = instructors;
	}
	
	public void setInstructorID(String instructorID) {
		this.instructorID = instructorID;
	}
	
	public String getInstructorID() {
		return this.instructorID;
	}
	
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}
	
	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	
	public String getPayDay() {
		return payDay;
	}

	public void setPayDay(String payDay) {
		this.payDay = payDay;
	}
	
	
	//verificar se é para ficar
	public void setDates(int size) {
		this.dates =  Arrays.asList(new String[size]);
	}
	
	public void setDates2(Collection<String> date) {
			this.dates.addAll(date);
	}
	
	public void addDate(String date) {
		this.dates.add(date);
	}
	
	public Collection<String> getDates(){
		return this.dates;
	}
	
	
	public void clearFields() {
		//TODO clear Dates
		activityName = email = specialty = "";
	}
}
