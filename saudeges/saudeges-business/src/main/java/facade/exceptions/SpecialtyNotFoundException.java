package facade.exceptions;

public class SpecialtyNotFoundException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -1543497536647898431L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public SpecialtyNotFoundException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public SpecialtyNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
