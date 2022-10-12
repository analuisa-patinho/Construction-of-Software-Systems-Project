package business.activity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.specialty.Specialty;

/**
 * A catalog of activities
 *
 */
@Stateless
public class ActivityCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Finds an activity given its name. Returns null if it is not found.
	 * 
	 * @param name The designation of the activity
	 * @return The activity with given name
	 */
	public Activity getActivity(String name) {
		TypedQuery<Activity> query = em.createNamedQuery(Activity.FIND_BY_NAME, Activity.class);
		query.setParameter(Activity.ACTIVITY_NAME, name);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Get all activities. Return empty list if nothing found
	 * 
	 * @return The list with all activities
	 */
	public List<Activity> getAllActivities() {
		try {
			TypedQuery<Activity> query = em.createNamedQuery(Activity.GET_ALL_ACTIVITIES, Activity.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * Get all activities. Return empty list if nothing found
	 * 
	 * @return The list with all activities
	 */
	public List<RegularActivity> getRegularActivities() {
		try {
			TypedQuery<RegularActivity> query = em.createNamedQuery(Activity.GET_REGULAR_ACTIVITIES, RegularActivity.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}


	/**
	 * Gets a list of all occasional activities
	 * 
	 * @return The list of all occasional activities
	 */
	public List<OccasionalActivity> getOccasionalActivities() {
		try {
			TypedQuery<OccasionalActivity> query = em.createNamedQuery(Activity.GET_OCCASIONAL_ACTIVITIES,
					OccasionalActivity.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	public List<OccasionalActivity> getOccasionalBySpecialty(int specID) {
		try {
			TypedQuery<OccasionalActivity> query = em.createNamedQuery(Activity.GET_OCCASIONAL_BY_SPECIALTY,
					OccasionalActivity.class);
			query.setParameter(Activity.SPECIALTY_OBJ, specID);
			
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Gets a list of the activities with a given specialty
	 * 
	 * @param specialty The given specialty
	 * @return The list of the activities with the given specialty
	 */
	public List<Activity> getActivityWithSpecialty(Specialty specialty) {
		try {
			TypedQuery<Activity> query = em.createNamedQuery(Activity.FIND_BY_SPECIALTY, Activity.class);
			query.setParameter(Activity.SPECIALTY_OBJ, specialty);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void createRegularActivity(int numParticipants, String name, Specialty specialty, long price, int numSessions, int sessionDuration) {
		RegularActivity newActivity = new RegularActivity(numParticipants, name, specialty, price, numSessions,
				sessionDuration);
		em.persist(newActivity);
	}

	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void createOcasionaActivity(String name, Specialty specialty, long price, int numSessions,
			int sessionDuration) {
		OccasionalActivity newActivity = new OccasionalActivity(name, specialty, price, numSessions, sessionDuration);
		em.persist(newActivity);
	}
}
