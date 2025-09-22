package exception;

public class NoFundsEnoughException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFundsEnoughException(String message) {
        super(message);
    }

}