package exception;

public class PixInUseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PixInUseException(String message) {
        super(message);
    }

}