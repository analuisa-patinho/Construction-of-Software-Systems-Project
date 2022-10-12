package business.specialty;

import javax.persistence.*;

import business.instructor.Certification;

/**
 * Entity implementation class for Entity: Specialty Class that represents a
 * specialty.
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = Specialty.FIND_BY_DESIGNATION, query = "SELECT s FROM Specialty s WHERE s.designation = :"
				+ Specialty.SPECIALTY_DESIGNATION),
		@NamedQuery(name = Specialty.GET_ALL_DESIGNATIONS, query = "SELECT s.designation FROM Specialty s"), 
		@NamedQuery(name = Specialty.GET_ALL_SPECIALTIES, query = "SELECT s FROM Specialty s"), })

public class Specialty {

	// Named query name constants
	public static final String GET_ALL_SPECIALTIES = "Specialty.getAllSpecialties";
	public static final String FIND_BY_DESIGNATION = "Specialty.findByDesignation";
	public static final String SPECIALTY_DESIGNATION = "designation";
	public static final String GET_ALL_DESIGNATIONS = "Specialty.getAllDesignations";

	@Id
	private int id;

	@Column(nullable = false, unique = true)
	private String designation;

	@Column(nullable = false)
	private int minDuration;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Certification certification;

	/**
	 * Constructor needed by JPA.
	 */
	public Specialty() {
	}

	/**
	 * Creates a specialty given the required data
	 * 
	 * @param designation   The specialty's designation
	 * @param minDuration   The specialty's minimal duration
	 * @param certification The required certification
	 */
	public Specialty(String designation, int minDuration, Certification certification) {
		this.designation = designation;
		this.minDuration = minDuration;
		this.certification = certification;
	}

	/**
	 * Gets the specialty's id
	 * 
	 * @return specialty's id
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * Verifies if the given session's duration is valid to the specialty
	 * 
	 * @param sessionDuration The duration to be verified
	 * @return true is minDuration <= sessionDuration, false if otherwise
	 */
	public boolean isValid(int sessionDuration) {
		return this.minDuration <= sessionDuration;
	}

	/**
	 * Gets the specialty's certification
	 * 
	 * @return specialty's certification
	 */
	public Certification getCertification() {
		return certification;
	}

	public String getDesignation() {
		return this.designation;
	}

	public int getMinDuration() {
		return this.minDuration;
	}
}
