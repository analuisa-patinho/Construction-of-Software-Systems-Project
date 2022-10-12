package business.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import business.session.Session;
import business.specialty.Specialty;
import static javax.persistence.CascadeType.ALL;

/**
 * Entity implementation class for Entity: OccasionalActivity Subclass of
 * Activity
 *
 */
@Entity
@DiscriminatorValue(value = "Occasional")
public class OccasionalActivity extends Activity {

	@OneToMany(cascade = ALL)
	private List<Session> sessions = new ArrayList<>();

	/**
	 * Constructor needed by JPA.
	 */
	public OccasionalActivity() {

	}

	/**
	 * Constructs an OccasionalActivity given its data
	 * 
	 * @param name            The name of the occasional activity
	 * @param specialty       The specialty of the occasional activity
	 * @param price           The price of the occasional activity
	 * @param weekSessions    The number of sessions of the occasional activity
	 * @param sessionDuration The duration of each session in the occasional
	 *                        activity
	 * @requires price != null && price > 0 && specialty != null;
	 */
	public OccasionalActivity(String name, Specialty specialty, long price, int weekSessions, int sessionDuration) {
		super(name, specialty, price, weekSessions, sessionDuration);
	}

	/**
	 * Gets all occasional activity sessions
	 * 
	 * @return The activity name
	 */
	public List<Session> getAllSessions() {
		return sessions;
	}

	/**
	 * Add to occasional activity the sessions
	 * 
	 * @param new ArrayList<>() The sessions to add to the occasional activity
	 * @requires sessions != null
	 */
	public void addSessions(List<Date> listDates) {
		for (Date d: listDates)
			this.sessions.add(new Session(d, sessionDuration, 1));
	}

	public int getId() {
		return this.id;
	}
}
