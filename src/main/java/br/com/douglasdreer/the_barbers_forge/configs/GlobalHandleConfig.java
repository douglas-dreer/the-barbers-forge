package br.com.douglasdreer.the_barbers_forge.configs;

import br.com.douglasdreer.the_barbers_forge.dtos.ErrorDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.exceptions.UniqueConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * <h1>GlobalHandleConfig</h1>
 * <p>Global exception handler that manages various exceptions in the application and
 * returns standardized error responses to the client.</p>
 * <p>This class intercepts specific exceptions such as data integrity violations and
 * general errors to return friendly and secure error messages.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@RestControllerAdvice
public class GlobalHandleConfig {
    private static final String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";

    /**
     * Handles exceptions related to data integrity violations, such as unique constraint violations.
     * <p>If a violation occurs (e.g., cpf already exists in the database), it returns a 400 (Bad Request) error
     * with a detailed message for the user, without exposing sensitive database information.</p>
     *
     * @param ex the exception thrown during the violation of a unique constraint
     * @return a ResponseEntity with an ErrorDTO and a 400 status code
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getCause() != null && ex.getCause().getMessage().contains("unique constraint")) {
            ErrorDTO errorDTO = new ErrorDTO(
                    400L,
                    "Duplicate Entry",
                    "This cpf is already registered.",
                    LocalDateTime.now()
            );
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }


        ErrorDTO errorDTO = new ErrorDTO(
                500L,
                MSG_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles custom exceptions related to unique constraint violations, such as a duplicate cpf.
     * <p>It provides a 400 (Bad Request) status and a relevant error message to the user.</p>
     *
     * @param ex the custom exception thrown when a unique constraint is violated
     * @return a ResponseEntity with an ErrorDTO and a 400 status code
     */
    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                400L, // Bad Request
                "Duplicate Entry", // Brief error title
                ex.getMessage(), // Friendly error message from the custom exception
                LocalDateTime.now() // Timestamp of when the error occurred
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                400L, // Bad Request
                "Invalid Parameter", // Brief error title
                ex.getMessage(), // Friendly error message from the custom exception
                LocalDateTime.now() // Timestamp of when the error occurred
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles cases when no records are found (404 Not Found).
     * <p>If a resource is not found, a 404 (Not Found) error is returned with a relevant message.</p>
     *
     * @param ex the exception that indicates no records were found
     * @return a ResponseEntity with an ErrorDTO and a 404 status code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                404L, // Not Found
                "Resource Not Found", // Brief error title
                ex.getMessage(), // Friendly error message from the custom exception
                LocalDateTime.now() // Timestamp of when the error occurred
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles not implement
     * <p>It returns a error 404</p>
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorDTO> handleUnsupportedOperationException(UnsupportedOperationException ex) {
        ErrorDTO errorDTO = new ErrorDTO(
                500L,
                MSG_INTERNAL_SERVER_ERROR,
                "Function not implement still.",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles general exceptions in the application.
     * <p>It returns a generic internal server error with a 500 (Internal Server Error) status code.</p>
     *
     * @param ex the exception that occurred
     * @return a ResponseEntity with an ErrorDTO and a 500 status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGeneralException() {
        ErrorDTO errorDTO = new ErrorDTO(
                500L,
                MSG_INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
