package business.reservation;

import static javax.persistence.GenerationType.AUTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity implementation class for Entity: Reservation Class that represents a
 * reservation of sessions.
 *
 */
@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(nullable = false)
	private String email;

	@Embedded
	private PaymentDetails paymentDetails;
	
	@Version
	private int version;

	/**
	 * Constructor needed by JPA.
	 */
	public Reservation() {
	}

	/**
	 * Constructs a Reservation given its data
	 * 
	 * @param date      The reservation's begin date
	 * @param email     The email associated
	 * @param entity    The entity
	 * @param reference The reference
	 * @param price     The price
	 */
	public Reservation(Date date, String email, String entity, String reference, double price) {
		this.date = date;
		this.email = email;
		this.paymentDetails = new PaymentDetails(entity, reference, price);
	}

	/**
	 * Gets the payment details of the reservation
	 * 
	 * @return the payment details
	 */
	public PaymentDetails getPaymentDetails() {
		return this.paymentDetails;
	}
	
	
}
