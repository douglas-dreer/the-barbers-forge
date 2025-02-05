package br.com.douglasdreer.the_barbers_forge.exceptions;

/**
 * <h1>ConverterServiceException</h1>
 * <p>Custom exception class for handling errors in the ConverterService.
 * This exception is thrown when there is an issue related to the conversion process.</p>
 * <p>It extends {@link RuntimeException}, providing a runtime exception mechanism
 * to handle errors during the conversion operations.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
public class ConverterServiceException extends RuntimeException {

    /**
     * Constructor for creating an instance of ConverterServiceException with a custom message.
     *
     * @param message the error message that explains the cause of the exception
     */
    public ConverterServiceException(String message) {
        super(message);
    }
}
