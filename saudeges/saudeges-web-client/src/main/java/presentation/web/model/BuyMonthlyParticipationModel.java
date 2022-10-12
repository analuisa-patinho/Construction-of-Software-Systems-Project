package presentation.web.model;

import java.util.Collection;
import java.util.LinkedList;

import facade.dtos.RegularDTO;
import facade.dtos.ScheduleDTO;
import facade.exceptions.ApplicationException;
import facade.remote.IBuyMonthlyParticipationServiceRemote;

/***
 * Helper
 * 
 * class to assist in the response of novo cliente
 * 
 *This class is the response information expert.**
 *
 */
public class BuyMonthlyParticipationModel extends Model {

	private Iterable<RegularDTO> regularActivities;
	private String activityName;
	private Iterable<ScheduleDTO> schedules;
	private String skd;
	private String startDate;
	private String duration;
	private String email;
	private String cost;
	private String reference;
	private String entity;
	private String payDay;
	private IBuyMonthlyParticipationServiceRemote monthlyService;

	public void setParticipationService(IBuyMonthlyParticipationServiceRemote monthlyService) {
		this.monthlyService = monthlyService;
	}

	public Iterable<RegularDTO> getRegularActivities() {
		try {
			return monthlyService.getRegularActivities();
		} catch (ApplicationException e) {
			return new LinkedList<RegularDTO>();
		}
	}
	
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	public Iterable<ScheduleDTO> getSchedules() {
		try {
			return monthlyService.getSchedulesForRegular(activityName);
		} catch (ApplicationException e) {
			return new LinkedList<ScheduleDTO>();
		}
	}

	public void setSchedules(Collection<ScheduleDTO> schedules) {
		this.schedules = schedules;
	}
	
	public String getSkd() {
		return skd;
	}

	public void setSkd(String skd) {
		this.skd = skd;
	}
	
	public String getStartDate() {
        return startDate;
    }
	
	public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getCost() {
		return cost;
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
	
	public void clearFields() {
		activityName = email = skd = startDate = duration = "";
	}

}
