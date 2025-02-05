package br.com.douglasdreer.the_barbers_forge.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <h1>CustomerDTO</h1>
 * <p>Data Transfer Object (DTO) for transferring customer data between layers of the application.
 * This class represents the customer details and is used to send data in the API responses.</p>
 *
 * <p>This class contains basic customer information such as name, cpf, address, phone number,
 * date of birth, and timestamps for creation and update.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDTO {

    /**
     * The unique identifier for the customer.
     */
    private Long id;

    /**
     * The first name of the customer.
     */
    private String firstName;

    /**
     * The last name of the customer.
     */
    private String lastName;

    /**
     * The cpf (Cadastro de Pessoas Físicas) number of the customer.
     */
    private String cpf;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The phone number of the customer.
     */
    private String phone;

    /**
     * The birth date of the customer.
     */
    private LocalDate birthDate;

    /**
     * The timestamp of when the customer was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp of when the customer was last updated.
     */
    private LocalDateTime updatedAt;
}
