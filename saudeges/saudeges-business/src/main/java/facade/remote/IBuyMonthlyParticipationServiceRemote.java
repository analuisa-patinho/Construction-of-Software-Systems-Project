package facade.remote;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;

import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.RegularDTO;
import facade.dtos.ScheduleDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IBuyMonthlyParticipationServiceRemote {
	
	public Collection<RegularDTO> getRegularActivities() throws ApplicationException;

	public Pair<List<ScheduleDTO>, Double> getSchedulesInfoForActivity(String activityName) 
			throws ApplicationException;
	
	public Collection<ScheduleDTO> getSchedulesForRegular(String activityName) throws ApplicationException;

	
	public Pair<PaymentInfoDTO, String> chooseSchedule(int rasID, LocalDate begin, int duration,
			String email) throws ApplicationException;
	
	
	public PaymentInfoDTO getPayInfo(int rasID, LocalDate startDate, int duration,
			String email) throws ApplicationException;
	
	public String getPayDayLimit(int rasID, LocalDate startDate, int duration,
			String email) throws ApplicationException;
}
