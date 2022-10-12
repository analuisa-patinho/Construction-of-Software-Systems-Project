package business.session;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import business.instructor.Instructor;
import business.reservation.Reservation;
import business.utils.UtilDate;


/**
 * Entity implementation class for Entity: Session Class that represents a session.
 */
@Entity
@NamedQueries({
	@NamedQuery(name = Session.FIND_BY_INSTRUCTOR, query = "SELECT s FROM Session s WHERE s.instructor = :"
		+ Session.INSTRUCTOR_OBJ + " ORDER BY s.beginTime"),
	@NamedQuery(name = Session.FIND_BY_INSTRUCTOR_AND_DATES, query = "SELECT s FROM Session s WHERE s.instructor = :"
		+ Session.INSTRUCTOR_OBJ + " AND (s.beginTime BETWEEN :" + Session.START_DATE + " AND :" + Session.END_DATE + ")"
		+ " OR (s.endTime BETWEEN :" + Session.START_DATE +" AND :" + Session.END_DATE + ")")
})
public class Session {

	// Named query name constants
	public static final String FIND_BY_INSTRUCTOR = "Session.findByInstructor";
	public static final String INSTRUCTOR_OBJ = "instructor";
	public static final String FIND_BY_INSTRUCTOR_AND_DATES = "Session.findByInstructorAndDates";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";

	@Id
	@GeneratedValue(strategy = AUTO)
	protected int id;

	int duration;
	
	@Temporal(TemporalType.TIMESTAMP)
	protected Date beginTime;

	@Temporal(TemporalType.TIMESTAMP)
	protected Date endTime;

	@Column(nullable = false)
	private int places;

	@ManyToOne
	protected Instructor instructor;

	@ManyToMany(cascade = ALL)
	private List<Reservation> reservations;
	
	@Version
	private int version;

	/**
	 * Constructor needed by JPA.
	 */
	public Session() {
	}

	/**
	 * Constructs a Session with the given data
	 * 
	 * @param beginTime    
	 * @param duration   The session's duration
	 * @param places 	The max number of places
	 */
	public Session(Date begin, int duration, int places) {
		this.beginTime = begin;
		this.endTime = UtilDate.addMinutes(begin, duration);
		this.duration = duration;
		this.places = places;
	}

	/**
	 * Constructs a session with the given data
	 * 
	 * @param begin      The begin date
	 * @param instructor The instructor associated
	 * @param duration   The session's duration
	 * @param numSlots   The max number of places
	 * @requires instructor != null
	 */
	public Session(Date begin, Instructor instructor, int duration, int places) {
		this(begin, duration, places);
		this.instructor = instructor;
	}
	
	public int getDuration() {
		return duration;
	}

	/**
	 * Gets the begin date
	 * 
	 * @return begin date
	 */
	public Date getBegin() {
		return beginTime;
	}

	/**
	 * Gets the session's end time
	 * 
	 * @return end time
	 */
	public Date getEnd() {
		return endTime;
	}

	/**
	 * Gets the max number of slots
	 * 
	 * @return max number of slots
	 */
	public int getNumPlaces() {
		return places;
	}


	/**
	 * Verifies if session has instructor
	 * 
	 * @return true if it has instructor, false if otherwise
	 */
	public boolean hasInstructor() {
		return instructor != null;
	}

	/**
	 * Adds a reservation to the session
	 * 
	 * @requires res != null
	 */
	public void addReservation(Reservation res) {
		reservations.add(res);
		places--;
	}

	/**
	 * Sets a session instructor
	 */
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	
}
