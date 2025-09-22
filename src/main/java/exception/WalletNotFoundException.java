package exception;

public class WalletNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WalletNotFoundException(String message) {
        super(message);
    }

}