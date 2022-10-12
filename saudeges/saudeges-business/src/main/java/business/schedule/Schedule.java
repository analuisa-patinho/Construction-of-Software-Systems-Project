package business.schedule;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import business.instructor.Instructor;
import business.session.Session;
import business.utils.UtilDate;

/**
 * Entity implementation class for Entity: Schedule Class that represents an
 * schedule for the regular activity.
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = Schedule.GET_ALL_TIMETABLES, query = "SELECT s FROM Schedule s"),
		@NamedQuery(name = Schedule.FIND_BY_ID, query = "SELECT s FROM Schedule s where s.id = :"
				+ Schedule.REGULARACTIVITYSCHEDULE_ID) })
public class Schedule {

	// Named query name constants
	public static final String GET_ALL_TIMETABLES = "Schedule.getAllSchedules";
	public static final String FIND_BY_ID = "Schedule.findById";
	public static final String REGULARACTIVITYSCHEDULE_ID = "id";

	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@Column(nullable = false)
	private int durationInMonths;
	
	@ElementCollection
	private List<WeekDayTimeSlot> weekDayTimeSlots;
		
	@OneToMany(cascade = ALL)
	private List<Session> sessions = new ArrayList<>();
	
	@Version
	private int version;
	
	/**
	 * Constructor needed by JPA.
	 */
	public Schedule() {
	}

	/**
	 * Constructs an Schedule given the required data, it also constructs the
	 * sessions associated to it
	 * 
	 * @param monthlyDuration    The duration of the schedule in months
	 * @param beginDate		     The beginning date
	 * @param weekSessionsDates  The list with the day of the week and the starting time of
	 *                           the week sessions
	 * @param i                  The instructor
	 * @param instructorDuration The duration of the instructor instruction
	 * @param activity           The regular activity
	 */
	public Schedule(int monthlyDuration, Date beginDate, List<WeekDayTimeSlot> weekSessionsDates, 
			Instructor i, int instructorDuration, 
			int numMaxParticipants, int activityDuration) {
		
		this.durationInMonths = monthlyDuration;
		this.beginDate = beginDate;
		this.weekDayTimeSlots = weekSessionsDates;
			
		Date endDate = UtilDate.toDate(UtilDate.toLocalDate(beginDate).plusMonths(monthlyDuration));
		Date endInstructorDate = UtilDate.toDate(UtilDate.toLocalDate(beginDate).plusMonths(instructorDuration));

		Calendar calendar = Calendar.getInstance();
		int weekday;

		for ( WeekDayTimeSlot wdts : weekSessionsDates) {
			calendar.setTime(beginDate);
			weekday = calendar.get(Calendar.DAY_OF_WEEK);

			while (weekday != UtilDate.dayOfWeekToInt(wdts.getDay())) {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				weekday = calendar.get(Calendar.DAY_OF_WEEK);
			}

			LocalTime ldate = UtilDate.toLocalTime(wdts.getTime());
			calendar.set(Calendar.HOUR_OF_DAY, ldate.getHour());
			calendar.set(Calendar.MINUTE, ldate.getMinute());

			Date date = calendar.getTime();

			while (endDate.after(date)){
				if (endInstructorDate.after(date)) {
					sessions.add(new Session(date, i, activityDuration, numMaxParticipants));
				} else {
					sessions.add(new Session(date, activityDuration, numMaxParticipants));
				}
				calendar.add(Calendar.DATE, 7);
				date = calendar.getTime();
			}
		}
	}



	/**
	 * Returns the list of sessions with instructor
	 * 
	 * @return The list of sessions with instructor associated
	 */
	public List<Session> sessionsWithInstructor() {
		List<Session> sessionsWithInstructor = new ArrayList<>();
		for (Session session : sessions) {
			if (session.hasInstructor())
				sessionsWithInstructor.add(session);
		}
		return sessionsWithInstructor;
	}

	/**
	 * Given the date and duration gets the list of sessions that happen in between
	 * 
	 * @param date     The begin date
	 * @param duration The duration
	 * @return The list of sessions in between
	 */
	public List<Session> getSessionsBetween(Date date, int duration) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, duration);
		Date endDate = calendar.getTime();

		List<Session> returnSessions = new ArrayList<>();

		for (Session session : sessions) {
			if (session.getBegin().after(date) && endDate.after(session.getBegin())) {
				returnSessions.add(session);
			}
		}

		return returnSessions;
	}

	/**
	 * Gets the schedule id
	 * 
	 * @return the schedule id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the schedule begin date
	 * 
	 * @return the schedule begin date
	 */
	public Date getStartDate() {
		return this.beginDate;
	}


	/**
	 * Gets the schedule monthly duration
	 * 
	 * @return the schedule monthly duration
	 */
	public int getDurationInMonths() {
		return this.durationInMonths;
	}

	/**
	 * Gets the schedule sessions
	 * 
	 * @return the schedule sessions
	 */
	public List<Session> getAllSessions() {
		return this.sessions;
	}

	public List<WeekDayTimeSlot> getWeekDayTimeSlots() {
		return weekDayTimeSlots;
	}

}