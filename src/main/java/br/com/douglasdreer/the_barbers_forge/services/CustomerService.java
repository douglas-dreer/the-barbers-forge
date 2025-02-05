package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.ValidateDocumentServiceException;

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
    * Searches for a customer by cpf (Brazilian Individual Taxpayer Registry number).
    *
    * @param cpf the cpf of the customer to be searched
    * @return {@link CustomerDTO} object representing the customer, or {@code null} if not found
    */
   CustomerDTO findBycpf(String cpf);

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
    * Checks whether the provided cpf exists in the system.    *
    * This method first validates the cpf using the {@link ValidateDocumentService}
    * to ensure that the cpf follows the correct format and is not null or empty.
    *
    * @param cpf the cpf number as a text string (digits only, without punctuation).
    *            Must not be null or empty.
    *
    * @return {@code true} if the cpf exists in the repository, {@code false} otherwise.
    *
    * @throws ValidateDocumentServiceException if the cpf is invalid or improperly formatted.
    *
    * @since 0.0.1
    */
   boolean existcpf(String cpf);
}
