package facade.remote;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import facade.dtos.InstructorDTO;
import facade.dtos.OccasionalDTO;
import facade.dtos.Pair;
import facade.dtos.PaymentInfoDTO;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDateFormatException;

@Remote
public interface IScheduleOccasionalActivityServiceRemote {
	
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException;

	
	public Collection<OccasionalDTO> getOccasionalActivities(String specialtyDesignation) throws ApplicationException;
	
	//talvez apagar (por poder ser igual à de baixo)
	public Collection<Pair<Integer, String>> setScheduleOccasionalActivity(String activityName, List<Date> beinDateSessions)
			throws ApplicationException;
	
	
	public Collection<InstructorDTO> getInstructors(String activityName, Collection<String> beinDateSessions)
			throws ApplicationException, InvalidDateFormatException;
	
	
	//public Collection<String> getDates(String activityName) throws ApplicationException;

	
	public Pair<PaymentInfoDTO, String> concludeScheduleOccasionalActivityOperation(int instructorId, String email)
			throws ApplicationException;
	
	
	public PaymentInfoDTO getPayInfo(int instructorID, String email)
			throws ApplicationException;
	
	
	public String getPayDayLimit(int instructorID, String email)
			throws ApplicationException;


	public int getNrSessions() throws ApplicationException;

}
