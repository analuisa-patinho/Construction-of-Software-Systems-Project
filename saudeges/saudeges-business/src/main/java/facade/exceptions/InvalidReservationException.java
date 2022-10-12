package facade.exceptions;

public class InvalidReservationException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -5375594493964475122L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public InvalidReservationException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public InvalidReservationException(String message, Exception e) {
		super(message, e);
	}
}
