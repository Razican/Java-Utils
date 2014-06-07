package razican.db;

/**
 * Exception telling that the database was not found
 * 
 * @author Razican (Iban Eguia)
 */
public class NoDatabaseException extends Exception {

    private static final long serialVersionUID = 1265142422211981500L;

    /**
     * Default constructor. The default message is: 'The database does not
     * exist'
     */
    public NoDatabaseException() {
	super("The database does not exist");
    }

    /**
     * Creates the exception with a personalized message
     * 
     * @param message
     *            - Message for the exception
     */
    public NoDatabaseException(final String message) {
	super(message);
    }
}