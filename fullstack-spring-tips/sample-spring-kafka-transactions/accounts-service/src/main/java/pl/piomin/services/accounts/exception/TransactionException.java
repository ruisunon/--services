package pl.piomin.services.accounts.exception;

public class TransactionException extends Exception {
    /**
     * Constructor for TransactionException class which creates an exception with the given message.
     * @param message The message to be displayed for the exception.
     */
    public TransactionException(String message) {
        super(message);
    }

}
