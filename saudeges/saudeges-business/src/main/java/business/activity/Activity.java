package business.activity;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import business.specialty.Specialty;

/**
 * Entity implementation class for Entity: Activity Class that represents an
 * activity. It can be an OccasionalActivity or a Regular Activity
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
		@NamedQuery(name = Activity.FIND_BY_NAME, query = "SELECT a FROM Activity a WHERE a.name = :"
				+ Activity.ACTIVITY_NAME),
		@NamedQuery(name = Activity.GET_ALL_ACTIVITIES, query = "SELECT a FROM Activity a"),
		@NamedQuery(name = Activity.GET_OCCASIONAL_ACTIVITIES, query = "SELECT a FROM Activity a WHERE a.activityType = 'Occasional'"),
		@NamedQuery(name = Activity.GET_REGULAR_ACTIVITIES, query = "SELECT a FROM Activity a WHERE a.activityType = 'Regular'"),
		@NamedQuery(name = Activity.FIND_BY_SPECIALTY, query = "SELECT a FROM Activity a WHERE a.specialty = :"
				+ Activity.SPECIALTY_OBJ),
		@NamedQuery(name=Activity.GET_OCCASIONAL_BY_SPECIALTY, query="SELECT a FROM Activity a WHERE (a.activityType = 'Occasional' AND a.specialty.id = :" 
				+ Activity.SPECIALTY_OBJ + ") ORDER BY a.price ASC") })
@DiscriminatorColumn(name = "ACTIVITY_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Activity {

	// Named query name constants
	public static final String FIND_BY_NAME = "Activity.findByName";
	public static final String ACTIVITY_NAME = "name";
	public static final String GET_ALL_ACTIVITIES = "Activity.getAllActivities";
	public static final String GET_OCCASIONAL_BY_SPECIALTY = "Activity.getOccasionalBySpecialty";
	public static final String GET_OCCASIONAL_ACTIVITIES = "Activity.getOccasionalActivities";
	public static final String GET_REGULAR_ACTIVITIES = "Activity.getRegularActivities";
	public static final String FIND_BY_SPECIALTY = "Activity.findBySpecialty";
	public static final String SPECIALTY_OBJ = "specialty";

	@Column(name = "ACTIVITY_TYPE", insertable = false, updatable = false)
	private String activityType;

	@Id
	@GeneratedValue(strategy = AUTO)
	protected int id;

	@Column(nullable = false, unique = true)
	protected String name;

	@ManyToOne(optional = false)
	protected Specialty specialty;

	@Column(nullable = false)
	protected double price;

	@Column(nullable = false)
	protected int numSessions;

	@Column(nullable = false)
	protected int sessionDuration;
	
	@Version
	private int version;

	/**
	 * Constructor needed by JPA.
	 */
	protected Activity() {
	}

	/**
	 * Constructs an Activity given its data
	 * 
	 * @param name            The name of the activity
	 * @param specialty       The specialty of the activity
	 * @param price           The price of the activity
	 * @param weekSessions    The number of sessions of the activity
	 * @param sessionDuration The duration of each session in the activity
	 * @requires price != null && price > 0 && specialty != null;
	 */
	protected Activity(String name, Specialty specialty, double price, int numSessions, int sessionDuration) {
		this.name = name;
		this.specialty = specialty;
		this.price = price;
		this.numSessions = numSessions;
		this.sessionDuration = sessionDuration;
	}

	/**
	 * Returns the id of any type of activity.
	 * @return the id of any type of the activity.
	 */
	public abstract int getId ();
	
	/**
	 * Gets activity name
	 * 
	 * @return The activity name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets activity session number
	 * 
	 * @return The activity session number
	 */
	public int getNumSessions() {
		return numSessions;
	}

	/**
	 * Gets activity specialty
	 * 
	 * @return The activity specialty
	 */
	public Specialty getSpecialty() {
		return specialty;
	}

	/**
	 * Gets activity price
	 * 
	 * @return The activity price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets activity session duration
	 * 
	 * @return The activity session duration
	 */
	public int getDuration() {
		return sessionDuration;
	}

}
