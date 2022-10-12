package facade.exceptions;

public class InvalidNumSessionsException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 2349360023123432613L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public InvalidNumSessionsException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public InvalidNumSessionsException(String message, Exception e) {
		super(message, e);
	}
}
