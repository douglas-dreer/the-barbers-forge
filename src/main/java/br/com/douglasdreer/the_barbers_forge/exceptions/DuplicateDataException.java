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
public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
