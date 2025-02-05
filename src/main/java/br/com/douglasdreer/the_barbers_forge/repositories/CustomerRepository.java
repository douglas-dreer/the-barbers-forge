package br.com.douglasdreer.the_barbers_forge.repositories;

import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>CustomerRepository</h1>
 * <p>Repository interface for managing {@link Customer} entities in the database.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations.
 * It also includes custom queries for specific customer searches.</p>
 *
 * <p>The repository provides methods for finding customers based on their first name and last name.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds customers by their first and last name.
     *
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @return a list of {@link Customer} entities matching the provided first and last name
     */
    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName")
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Check if exist CPF
     * @param cpf
     * @return {@link Integer}
     */
    @Query("SELECT COUNT(c.id) FROM Customer c WHERE c.CPF = :cpf")
    Integer findByCPFExists(String cpf);

    /**
     * Find customer by CPF
     * @param cpf
     * @return {@link Customer}
     */
    Customer findCustomerByCPF(String cpf);
}
