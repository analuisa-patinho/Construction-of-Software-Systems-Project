package facade.exceptions;

public class ActivityAlreadyExistsException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 205386397252653757L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public ActivityAlreadyExistsException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public ActivityAlreadyExistsException(String message, Exception e) {
		super(message, e);
	}
}
