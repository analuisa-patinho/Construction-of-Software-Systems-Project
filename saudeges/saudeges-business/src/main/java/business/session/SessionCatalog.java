package business.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.instructor.Instructor;
import facade.dtos.Pair;

/**
 * A catalog of sessions
 *
 */
@Stateless
public class SessionCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/*
	 * Constructs an activity catalog given an entity manager
	 * 
	 * @param em The entity manager
	 *
	public SessionCatalog(EntityManager em) {
		this.em = em;
	}*/

	/**
	 * Get the sessions with the given instructor. Return empty list if nothing
	 * found
	 * 
	 * @requires instructor != null
	 * @return The list with sessions.
	 */
	public List<Session> getSessionByInstructor(Instructor instructor) {
		try {
			TypedQuery<Session> query = em.createNamedQuery(Session.FIND_BY_INSTRUCTOR, Session.class);
			query.setParameter(Session.INSTRUCTOR_OBJ, instructor);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	/**
	 * Checks if instructor is available to instruct all provided time slots
	 * 
	 * @requires instructor != null
	 * @return If instructor is available
	 */
	public boolean isInsctructorAvailable(Instructor instructor, List<Pair<Date, Date>> timeSlots)  {
		 for(Pair<Date, Date> dates : timeSlots) {
			TypedQuery<Session> query = em.createNamedQuery(Session.FIND_BY_INSTRUCTOR_AND_DATES, Session.class);
			query.setParameter(Session.INSTRUCTOR_OBJ, instructor);
			query.setParameter(Session.START_DATE, dates.getValue0());
			query.setParameter(Session.END_DATE, dates.getValue1());
			if( !query.getResultList().isEmpty())
				return false;
		 }
		 return true;
	}
}
