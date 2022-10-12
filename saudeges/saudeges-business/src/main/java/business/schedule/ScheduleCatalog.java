package business.schedule;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.instructor.Instructor;

/**
 * A catalog of schedules
 *
 */
@Stateless
public class ScheduleCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;


	/**
	 * Finds an schedule given its id. Returns null if it is not found.
	 * 
	 * @param scheduleID The id of the schedule
	 * @return The schedule with given id
	 * @throwsInstructorNotFoundException
	 */
	public Schedule getRegularActivitySchedule(int scheduleID) {
		TypedQuery<Schedule> query = em.createNamedQuery(Schedule.FIND_BY_ID, Schedule.class);
		query.setParameter(Schedule.REGULARACTIVITYSCHEDULE_ID, scheduleID);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Schedule createSchedule(int currentMonthlyDuration, Date date,
			List<WeekDayTimeSlot> currentWeekSessionsDates, Instructor i, int instructorDuration,
			int numMaxParticipants, int sessionDuration) {
		
		Schedule skd = new Schedule(currentMonthlyDuration, date, currentWeekSessionsDates, i, 
				instructorDuration, numMaxParticipants, sessionDuration);
		em.persist(skd);
		return skd;
	}
}
