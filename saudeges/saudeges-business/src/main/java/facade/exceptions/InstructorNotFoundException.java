package facade.exceptions;

public class InstructorNotFoundException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 3817108840819609507L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public InstructorNotFoundException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public InstructorNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
