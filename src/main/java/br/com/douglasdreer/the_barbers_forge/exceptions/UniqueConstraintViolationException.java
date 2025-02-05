package br.com.douglasdreer.the_barbers_forge.exceptions;

/**
 * <h1>UniqueConstraintViolationException</h1>
 * <p>Exception class that represents a violation of a unique constraint in the database.</p>
 * <p>Thrown when an attempt to insert or update a record with a duplicate value occurs,
 * violating a unique constraint, such as a cpf that already exists in the database.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
public class UniqueConstraintViolationException extends RuntimeException {

  /**
   * Constructs a new UniqueConstraintViolationException with the specified detail message.
   *
   * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
   */
  public UniqueConstraintViolationException(String message) {
    super(message);
  }
}
