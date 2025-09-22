package exception;

public class AccountWithInvestmentException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountWithInvestmentException(String message) {
        super(message);
    }

}