package facade.exceptions;

public class ActivityNotFoundException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 6726573253696626593L;

	/**
	 * Creates an exception given an error message
	 * 
	 * @param message The error message
	 */
	public ActivityNotFoundException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception
	 * 
	 * @param message The error message
	 * @param e       The wrapped exception
	 */
	public ActivityNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
