package br.com.douglasdreer.the_barbers_forge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <h1>ErrorDTO</h1>
 * <p>Data Transfer Object (DTO) class that represents an error response in the application.
 * It encapsulates information about the error, including a code, title, and message.</p>
 *
 * <p>This class is used to standardize error responses sent back to the client in case of issues or exceptions
 * occurring during the execution of requests.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDTO {

    /**
     * The error code that identifies the type of error.
     */
    private Long code;

    /**
     * A brief title or summary of the error.
     */
    private String title;

    /**
     * A detailed message explaining the error.
     */
    private String message;
    /**
     *
     */
    private LocalDateTime timestamp;
}
