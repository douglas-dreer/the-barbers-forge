package br.com.douglasdreer.the_barbers_forge.exceptions;

/**
 * <h1>CustomerServiceException</h1>
 * <p>Custom exception class for handling errors related to customer service operations.
 * This exception is thrown when there is an issue in the customer service layer.</p>
 * <p>It extends {@link RuntimeException}, allowing for runtime exception handling
 * when customer-related operations fail.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
public class CustomerServiceException extends RuntimeException {

    /**
     * Constructor for creating an instance of CustomerServiceException with a custom message.
     *
     * @param message the error message that explains the cause of the exception
     */
    public CustomerServiceException(String message) {
        super(message);
    }
}
