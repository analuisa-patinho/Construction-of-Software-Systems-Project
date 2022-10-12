package business.reservation;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Entity implementation class for Entity: PaymentDetails A class representing
 * payment details of a single reservation.
 *
 */
@Embeddable
public class PaymentDetails {
	@Column(nullable = false)
	private String entity;

	@Column(nullable = false)
	private String reference;

	@Column(nullable = false)
	private double price;

	/**
	 * Constructor needed by JPA.
	 */
	public PaymentDetails() {
	}

	/**
	 * Creates a new PaymentDetails object given an entity, reference and price
	 * 
	 * @param entity    The entity
	 * @param reference The reference
	 * @param price     The price
	 */
	public PaymentDetails(String entity, String reference, double price) {
		this.entity = entity;
		this.reference = reference;
		this.price = price;
	}

	/**
	 * Gets the entity
	 * 
	 * @return The entity
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * Gets the reference
	 * 
	 * @return The reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Gets the price
	 * 
	 * @return The price
	 */
	public double getPrice() {
		return this.price;
	}
}
