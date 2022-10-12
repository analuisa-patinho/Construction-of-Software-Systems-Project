package facade.remote;

import java.util.Collection;

import javax.ejb.Remote;

import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface ICreateActivityServiceRemote {
	
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException;
	
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price, int numParticipants) throws ApplicationException;
	
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price) throws ApplicationException;

}
