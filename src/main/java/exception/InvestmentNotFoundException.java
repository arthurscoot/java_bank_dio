package exception;

public class InvestmentNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvestmentNotFoundException(String message) {
        super(message);
    }

}