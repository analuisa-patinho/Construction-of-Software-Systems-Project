package business.instructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import business.session.Session;
import business.utils.UtilDate;

/**
 * A catalog of instructors
 *
 */
@Stateless
public class InstructorCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;


	/**
	 * Get all instructors. Return empty list if nothing found
	 * 
	 * @return The list with all instructors
	 */
	public List<Instructor> getAllInstructors() {
		try {
			TypedQuery<Instructor> query = em.createNamedQuery(Instructor.GET_ALL_INSTRUCTORS, Instructor.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	/**
	 * Finds an instructor given its ID. Returns null if it is not found.
	 * 
	 * @param instructorID The instructor's id
	 * @return The instructor. Null if not found
	 */
	public Instructor getInstructor(int instructorID) { //what about em.find ???
		TypedQuery<Instructor> query = em.createNamedQuery(Instructor.FIND_BY_ID, Instructor.class);
		query.setParameter(Instructor.INSTRUCTOR_ID, instructorID);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public List<Instructor> getAvailableInstructors(List<Date> dates, int duration) {
		 List<Instructor> instructors = getAllInstructors();
		 for(Date date : dates) {
			TypedQuery<Instructor> query = em.createNamedQuery(Instructor.GET_UNAVAILABLE_INSTRUCTORS, Instructor.class);
			query.setParameter(Session.START_DATE, date);
			query.setParameter(Session.END_DATE, UtilDate.addMinutes(date, duration));
			for(Instructor i: query.getResultList())
				instructors.remove(i);
		 }
		 return instructors;
	}
		
}