package application;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.handlers.CreateActivityHandler;
import facade.dtos.SpecialtyDTO;
import facade.exceptions.ApplicationException;
import facade.remote.ICreateActivityServiceRemote;

/**
 * A service that offers the required operations to create an activity, hiding
 * its implementation from the client.
 *
 */
@Stateless
public class CreateActivityService implements ICreateActivityServiceRemote {

	/**
	 * The business object facade that handles the use case "Create activity"
	 */
	@EJB
	private CreateActivityHandler createActivityHandler;


	/**
	 * Gets all the specialty designations
	 * 
	 * @return The list of the specialty designations
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	public Collection<SpecialtyDTO> getSpecialities() throws ApplicationException {
		return createActivityHandler.getSpecialities();
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
	 * @param numParticipants      The max number of participants per sessions for
	 *                             the activity
	 * @throws ApplicationException if an error occurs while attempting to create
	 *                              the activity
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price, int numParticipants) throws ApplicationException {

		createActivityHandler.addActivity(name, specialtyDesignation, isRegular, numSessions, sessionDuration, price,
				numParticipants);
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
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addActivity(String name, String specialtyDesignation, boolean isRegular, int numSessions,
			int sessionDuration, long price) throws ApplicationException {

		createActivityHandler.addActivity(name, specialtyDesignation, isRegular, numSessions, sessionDuration, price);
	}
}
