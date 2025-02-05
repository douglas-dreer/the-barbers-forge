package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.CustomerServiceException;
import br.com.douglasdreer.the_barbers_forge.exceptions.DuplicateDataException;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h1>CustomerServiceImpl</h1>
 * <p>Implementation class for the customer service in the Casa da Navalha application.
 * It contains the business logic for managing customers, including operations
 * for searching, creating, updating, and deleting customers.</p>
 *
 * <p>This service is responsible for interacting with the repository and handling customer-related data.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ConverterService converterService;

    /**
     * Constructor with dependency injection.
     *
     * @param customerRepository repository responsible for CRUD operations on customers
     * @param converterService   service to convert between entities and DTOs
     */
    public CustomerServiceImpl(CustomerRepository customerRepository, ConverterService converterService) {
        this.customerRepository = customerRepository;
        this.converterService = converterService;
    }

    /**
     * Returns the list of all registered customers.
     *
     * @return list of {@link CustomerDTO} objects or an empty list if no customers are found
     */
    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(item -> converterService.convertTo(item, CustomerDTO.class))
                .toList();
    }

    /**
     * Searches for customers by full name.
     *
     * @param firstName the first name of the customer to be searched
     * @param lastName  the last name of the customer to be searched
     * @return list of {@link CustomerDTO} objects that match the provided name
     */
    @Override
    public List<CustomerDTO> findByFullName(String firstName, String lastName) {
        return customerRepository
                .findByFirstNameAndLastName(firstName, lastName)
                .stream()
                .map(item -> converterService.convertTo(item, CustomerDTO.class))
                .toList();
    }

    /**
     * Searches for a customer by their CPF (Brazilian Individual Taxpayer Registry number).
     * <p>This method queries the database to find a customer matching the provided CPF.</p>
     *
     * <p>If no customer is found with the given CPF, a {@link ResourceNotFoundException} is thrown.</p>
     *
     * @param cpf the CPF of the customer to be searched for
     * @return a {@link CustomerDTO} object containing the customer's details if found
     * @throws ResourceNotFoundException if no customer is found with the provided CPF
     */
    @Override
    public CustomerDTO findByCpf(String cpf) {
        Customer foundCustomer =  customerRepository.findCustomerByCPF(cpf);
        if (foundCustomer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return converterService.convertTo(foundCustomer, CustomerDTO.class);
    }

    /**
     * Searches for a customer by ID.
     *
     * @param id the unique identifier of the customer
     * @return {@link CustomerDTO} object corresponding to the provided ID, or {@code null} if not found
     */
    @Override
    public CustomerDTO findById(Long id) {
        return customerRepository
                .findById(id)
                .map(item -> converterService.convertTo(item, CustomerDTO.class))
                .orElse(null);
    }

    /**
     * Creates a new customer in the system.
     *
     * <p>If a duplicate CPF is detected, a {@link DuplicateDataException} is thrown.</p>
     *
     * @param dto the data of the customer to be registered
     * @return {@link CustomerDTO} object with the information of the created customer
     * @throws DuplicateDataException if the CPF already exists in the system
     */
    @Override
    public CustomerDTO create(CustomerDTO dto) {
        if (existCPF(dto.getCPF())) {
                throw new DuplicateDataException("A customer with the same CPF already exists.");
        }

        Customer savedEntity = customerRepository.save(converterService.convertTo(dto, Customer.class));
        return converterService.convertTo(savedEntity, CustomerDTO.class);
    }

    /**
     * Edits the details of an existing customer in the system.
     * <p>This method updates the customer information based on the provided {@link CustomerDTO}.
     * If the customer exists, their details are updated. If the customer does not exist, a {@link ResourceNotFoundException}
     * is thrown with a relevant error message.</p>
     *
     * @param dto the updated data of the customer, containing the information to be modified
     * @return {@link CustomerDTO} object containing the updated customer information
     * @throws ResourceNotFoundException if no customer is found with the provided {@link CustomerDTO#getId()}
     * @throws CustomerServiceException if an unexpected error occurs while processing the update
     */
    @Override
    public CustomerDTO edit(CustomerDTO dto) {
        try {
            if (!customerRepository.existsById(dto.getId())) {
                throw new ResourceNotFoundException("Customer not found with id: " + dto.getId());
            }

            Customer customerEdited = customerRepository.save(converterService.convertTo(dto, Customer.class));
            return converterService.convertTo(customerEdited, CustomerDTO.class);
        } catch (ResourceNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new CustomerServiceException("An unexpected error occurred while processing your request.");
        }
    }



    /**
     * Deletes a customer by ID.
     * <p>Future implementation.</p>
     *
     * @param id the identifier of the customer to be deleted
     */
    @Override
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    /**
     * Check if exist CPF
     * @param cpf Document CPF
     * @return boolean
     */
    @Override
    public boolean existCPF(String cpf) {
       return customerRepository.findByCPFExists(cpf) >= 1;
    }
}
