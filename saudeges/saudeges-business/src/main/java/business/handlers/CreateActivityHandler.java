package business.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import business.activity.ActivityCatalog;
import business.specialty.Specialty;
import business.specialty.SpecialtyCatalog;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ActivityAlreadyExistsException;
import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidNumParticipantsException;
import facade.exceptions.InvalidNumSessionsException;
import facade.exceptions.SpecialtyNotFoundException;

/**
 * Handles the activity creation use case: 1) createActivity 2) addActivity
 */
@Stateless
public class CreateActivityHandler {

	
	@EJB
	private SpecialtyCatalog sc;
	
	@EJB
	private ActivityCatalog ac;


	/**
	 * Gets all the specialty designations
	 * 
	 * @return The list of the specialty designations
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException {
		
		try {
			List<Specialty> entityList = sc.getAllSpecialties();
			return showSpecialties(entityList);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching sport modalities", e);
		}
	}
	

	/**
	 * Given the required activity data, creates a new activity
	 * 
	 * @param name                 The activity name
	 * @param specialtyDesignation The specialty designation
	 * @param isRegular            True if regular, false if otherwise
	 * @param numSessions          The max number of sessions for the activity
	 * @param sessionDuration      The session duration for the activity
	 * @param price                The price of the activity
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price) throws ApplicationException {
		
		if (isRegular) {
			InvalidNumParticipantsException e = new InvalidNumParticipantsException(
					"Regular Activities require a number of maximum participants");
			throw new ApplicationException("Error adding activity with name \"" + name + "\"", e);
		}
		addActivity(name, specialtyDesignation, isRegular, numSessions, sessionDuration, price, 1);
	}

	
	/**
	 * Given the required activity data, creates a new activity
	 * 
	 * @param name                 The activity name
	 * @param specialtyDesignation The specialty designation
	 * @param isRegular            True if regular, false if otherwise
	 * @param numSessions          The max number of sessions for the activity
	 * @param sessionDuration      The session duration for the activity
	 * @param price                The price of the activity
	 * @param numMaxParticipants   The max number of participants per sessions for
	 *                             the activity
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price, int numParticipants) throws ApplicationException {

		try {
			if (ac.getActivity(name) != null)
				throw new ActivityAlreadyExistsException(
						"There already exists an activity with the name \"" + name + "\"");
			if (!isValidNumSessions(isRegular, numSessions, price))
				throw new InvalidNumSessionsException("The param numSessions is invalid for the type of activity");

			Specialty specialty = sc.getSpecialty(specialtyDesignation);
			if (specialty == null) 
				throw new SpecialtyNotFoundException("Specialty requested does not exist");
			if (!specialty.isValid(sessionDuration))
				throw new InvalidDurationException("Given duration is not valid for the specialty requested");
			if (isRegular && numParticipants <= 0)
				throw new InvalidNumParticipantsException("Maximum number of participants is not valid");

			if (isRegular) 
				ac.createRegularActivity(numParticipants, name, specialty, price, numSessions,sessionDuration);
			else 
				ac.createOcasionaActivity(name, specialty, price, numSessions, sessionDuration);
			
		} catch (Exception e) {
			throw new ApplicationException("Error adding activity with name \"" + name + "\"", e);
		}
	}

	
	/**
	 * With the given data, checks if number of sessions is valid for the type of
	 * the activity
	 * 
	 * @param isRegular True if regular, false if otherwise
	 * @param price     The price of the activity
	 * @return true if it is all right, false if otherwise
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public boolean isValidNumSessions(boolean isRegular, int numSessions, long price) {
		if (isRegular) {
			return (numSessions > 0 && numSessions <= 5 && price > 0);
		} else {
			return (numSessions > 0 && numSessions <= 20 && price > 0);
		}
	}
	
/////////////////// TO DTO ///////////////////
	
	private List<SpecialtyDTO> showSpecialties(List<Specialty> entityList) {
		List<SpecialtyDTO> result = new ArrayList<>();
		for (Specialty s : entityList)
			result.add(new SpecialtyDTO(s.getID(), s.getDesignation(), s.getMinDuration(), s.getCertification().toString()));
		return result;
	}

}
