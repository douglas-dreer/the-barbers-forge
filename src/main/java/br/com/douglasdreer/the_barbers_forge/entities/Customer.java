package br.com.douglasdreer.the_barbers_forge.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <h1>Customer</h1>
 * <p>Entity class that represents the customer in the database.
 * This class is mapped to the "TBL0001_CUSTOMERS" table and contains customer-related data such as
 * their name, CPF, address, phone number, date of birth, and timestamps for creation and updates.</p>
 *
 * <p>The {@link Customer} class is a JPA entity, and its attributes are mapped to columns in the database table.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Entity
@Table(name = "TBL0001_CUSTOMERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    /**
     * The unique identifier for the customer.
     * This field is automatically generated as a primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the customer.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * The last name of the customer.
     * This field cannot be null.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * The CPF (Cadastro de Pessoas Físicas) number of the customer.
     * This field must be unique and cannot be null.
     */
    @Column(unique = true, nullable = false, updatable = false)
    private String CPF;

    /**
     * The address of the customer.
     * This field is optional.
     */
    private String address;

    /**
     * The phone number of the customer.
     * This field is optional.
     */
    private String phone;

    /**
     * The birth date of the customer.
     * This field is optional.
     */
    private LocalDate birthDate;

    /**
     * The timestamp indicating when the customer was created in the system.
     * This field is automatically set when the entity is first created and is not updatable.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last time the customer data was updated.
     * This field is automatically updated on each modification of the entity.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
