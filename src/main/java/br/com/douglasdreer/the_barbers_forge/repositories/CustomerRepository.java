package br.com.douglasdreer.the_barbers_forge.repositories;

import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


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

}
