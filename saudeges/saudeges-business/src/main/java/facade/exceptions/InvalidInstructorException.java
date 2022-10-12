package facade.exceptions;

public class InvalidInstructorException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 6159624595546241566L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public InvalidInstructorException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public InvalidInstructorException(String message, Exception e) {
		super(message, e);
	}
}
