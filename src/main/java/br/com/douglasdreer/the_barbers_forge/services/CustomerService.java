package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;

import java.util.List;

/**
 * <h1>CustomerService</h1>
 * <p>Interface defining the service methods for customer management within the Casa da Navalha application.
 * Includes methods for searching, creating, editing, and deleting customers.</p>
 * @since 0.0.1
 * @author Douglas Dreer
 */
public interface CustomerService {

   /**
    * Retrieves the list of all registered customers.
    *
    * @return list of {@link CustomerDTO} objects representing all customers in the system
    */
   List<CustomerDTO> findAll();

   /**
    * Searches for customers by their full name.
    *
    * @param firstName the first name of the customer
    * @param lastName  the last name of the customer
    * @return list of {@link CustomerDTO} objects representing the customers that match the full name
    */
   List<CustomerDTO> findByFullName(String firstName, String lastName);

   /**
    * Searches for a customer by CPF (Brazilian Individual Taxpayer Registry number).
    *
    * @param cpf the CPF of the customer to be searched
    * @return {@link CustomerDTO} object representing the customer, or {@code null} if not found
    */
   CustomerDTO findByCpf(String cpf);

   /**
    * Searches for a customer by their unique ID.
    *
    * @param id the unique identifier of the customer
    * @return {@link CustomerDTO} object representing the customer, or {@code null} if not found
    */
   CustomerDTO findById(Long id);

   /**
    * Creates a new customer in the system.
    *
    * @param dto the data for the customer to be registered
    * @return {@link CustomerDTO} object with the details of the created customer
    */
   CustomerDTO create(CustomerDTO dto);

   /**
    * Edits the information of an existing customer.
    *
    * @param dto the updated customer data
    * @return {@link CustomerDTO} object with the updated customer information
    */
   CustomerDTO edit(CustomerDTO dto);

   /**
    * Deletes a customer by their unique ID.
    *
    * @param id the unique identifier of the customer to be deleted
    */
   void delete(Long id);

   /**
    * Check if CPF exist
    * @param cpf Document CPF
    * @return {@link Boolean}
    */
   boolean existCPF(String cpf);
}
