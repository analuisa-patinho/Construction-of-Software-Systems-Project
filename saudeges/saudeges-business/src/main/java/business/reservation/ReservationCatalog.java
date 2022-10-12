/**
 * 
 */
package business.reservation;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * A catalog of Activities
 *
 */
@Stateless
public class ReservationCatalog {
	
	/**
	 * Entity manager for accessing the persistence service 
	 */
	@PersistenceContext @SuppressWarnings("unused")
	private EntityManager em;
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Reservation createReservation(Date date, String email, String entity, String reference, double price) {
		Reservation reservation = new Reservation(date, email, entity, reference, price);
		em.persist(reservation);
		return reservation;
	}
}
