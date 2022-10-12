package business.instructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.session.Session;
import business.specialty.Specialty;

/**
 * Entity implementation class for Entity: Instructor Class that represents an
 * instructor.
 *
 */

@Entity
@NamedQueries({
		@NamedQuery(name = Instructor.FIND_BY_ID, query = "SELECT i FROM Instructor i WHERE i.id = :"
				+ Instructor.INSTRUCTOR_ID),
		@NamedQuery(name = Instructor.GET_ALL_INSTRUCTORS, query = "SELECT i FROM Instructor i"),
		@NamedQuery(name = Instructor.GET_UNAVAILABLE_INSTRUCTORS, 
			query = "SELECT i FROM Instructor i, IN(i.sessions) AS s WHERE (s.beginTime BETWEEN :" + 
					Session.START_DATE + " AND :" + Session.END_DATE + ")"
					+ " OR (s.endTime BETWEEN :" + Session.START_DATE + " AND :" + Session.END_DATE + ")")
})
public class Instructor {

	// Named query name constants
	public static final String FIND_BY_ID = "Instructor.findById";
	public static final String INSTRUCTOR_ID = "id";
	public static final String GET_ALL_INSTRUCTORS = "Instructor.getAllImstructors";
	public static final String GET_UNAVAILABLE_INSTRUCTORS = "Instructor.getUnavInstructors";

	@Id
	private int id;

	@Column(nullable = false)
	private String name;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<Certification> certifications;
	
	@OneToMany(mappedBy = "instructor")
	private List<Session> sessions = new ArrayList<>();
	
	/**
	 * Constructor needed by JPA.
	 */
	public Instructor() {
	}

	/**
	 * Constructs an Instructor with the given name
	 * 
	 * @param name The name of the instructor
	 * @requires name != null
	 */
	public Instructor(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the instructor
	 * 
	 * @return The name of the instructor
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the list of certifications associated to the instructor
	 * 
	 * @return The list of certifications
	 */
	public List<Certification> getCertifications() {
		return certifications;
	}

	/**
	 * Gets the id of the instructor
	 * 
	 * @return The instructor's id
	 */
	public int getId() {
		return id;
	}

	
	/**
	 * @param specialty
	 * @return
	 */
	public boolean hasRightCertification(Specialty specialty) {
		for (Certification certification : getCertifications()) {
			if (certification == specialty.getCertification()) {
				return true;
			}
		}
		return false;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instructor other = (Instructor) obj;
		return id == other.id;
	}
	

}
