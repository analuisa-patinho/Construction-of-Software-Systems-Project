package business.specialty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * A catalog of specialties
 *
 */
@Stateless
public class SpecialtyCatalog {

	/**
	 * Entity manager for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/*
	 * Constructs a specialty catalog given an entity manager
	 * 
	 * @param em The entity manager
	 *
	public SpecialtyCatalog(EntityManager em) {
		this.em = em;
	}*/

	/**
	 * Finds a specialty given its designation. If does not exists return null,
	 * 
	 * @param specialtyDesignation The designation of the specialty
	 * @return The specialty. If did not found null.
	 */
	public Specialty getSpecialty(String specialtyDesignation) {

		TypedQuery<Specialty> query = em.createNamedQuery(Specialty.FIND_BY_DESIGNATION, Specialty.class);
		query.setParameter(Specialty.SPECIALTY_DESIGNATION, specialtyDesignation);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}

	/**
	 * Gets a list with all specialties. If error return empty list
	 * 
	 * @return The list with all specialties
	 */
	public List<String> getAllSpecialtyDesignations() {
		try {
			TypedQuery<String> query = em.createNamedQuery(Specialty.GET_ALL_DESIGNATIONS, String.class);
			List<String> sortedList = query.getResultList();
			Collections.sort(sortedList);
			return sortedList;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<Specialty> getAllSpecialties() {
		try {
			TypedQuery<Specialty> query = em.createNamedQuery(Specialty.GET_ALL_SPECIALTIES, Specialty.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
}
