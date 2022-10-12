package facade.exceptions;

public class InvalidDurationException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 4196443607438196274L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public InvalidDurationException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public InvalidDurationException(String message, Exception e) {
		super(message, e);
	}
}
