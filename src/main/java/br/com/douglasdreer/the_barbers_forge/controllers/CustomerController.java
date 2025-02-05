package br.com.douglasdreer.the_barbers_forge.controllers;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * <h1>CustomerController</h1>
 * <p>Controller class that handles HTTP requests for customer-related operations.
 * It provides endpoints for creating and retrieving customers.</p>
 *
 * <p>This controller delegates business logic to the {@link CustomerService} layer.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    /**
     * Constructor that initializes the controller with the required service dependency.
     *
     * @param customerService the service that handles customer-related business logic
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return a {@link ResponseEntity} containing a list of {@link CustomerDTO} objects
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    /**
     * Searches for customers by their first and last name.
     * <p>This method handles the HTTP GET request with two query parameters: <code>firstName</code> and <code>lastName</code>.
     * It calls the service to retrieve a list of customers that match the provided names.</p>
     *
     * @param firstName the first name of the customers to be searched for
     * @param lastName  the last name of the customers to be searched for
     * @return a {@link ResponseEntity} containing a list of {@link CustomerDTO} objects that match the provided first and last name
     *         or an empty list if no customers are found
     */
    @GetMapping(params = {"firstName", "lastName"})
    public ResponseEntity<List<CustomerDTO>> findByFirstName(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName
    ) {
        List<CustomerDTO> customerList = customerService.findByFullName(firstName, lastName);
        return ResponseEntity.ok(customerList);
    }


    /**
     * Searches for a customer by their CPF (Brazilian Individual Taxpayer Registry number).
     * <p>This method handles the HTTP GET request with a query parameter: <code>cpf</code>.
     * It calls the service to retrieve the customer that matches the provided CPF.</p>
     *
     * @param cpf the CPF of the customer to be searched for
     * @return a {@link ResponseEntity} containing a {@link CustomerDTO} object with the customer details
     *         that match the provided CPF, or a 404 error if no customer is found
     */
    @GetMapping(params = { "cpf" })
    public ResponseEntity<CustomerDTO> findByCPF(@RequestParam("cpf") String cpf) {
        return ResponseEntity.ok(customerService.findByCpf(cpf));
    }

    /**
     * Searches for a customer by ID.
     *
     * @param id the unique identifier of the customer
     * @return {@link ResponseEntity} containing a {@link CustomerDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    /**
     * Creates a new customer and returns the created customer data along with the URI of the newly created resource.
     *
     * @param datas the {@link CustomerDTO} object containing customer data
     * @param request the HTTP request containing additional request information
     * @return a {@link ResponseEntity} containing the created {@link CustomerDTO} and the location of the new resource
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerDTO datas, HttpServletRequest request) {
        CustomerDTO savedData = customerService.create(datas);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedData.getId()).toUri();
        return ResponseEntity.created(location).body(savedData);
    }

    /**
     * Updates an existing customer in the system.
     * <p>This endpoint allows you to update the details of a customer identified by their unique ID.</p>
     * <p>If the provided customer ID does not match the ID in the request URL, an {@link IllegalArgumentException} is thrown.</p>
     * <p>Upon successful update, the updated customer information is returned along with a 200 (OK) status and the location of the updated resource.</p>
     *
     * @param id the unique identifier of the customer to be updated
     * @param datas the updated customer data in the form of a {@link CustomerDTO}
     * @return a {@link ResponseEntity} containing the updated {@link CustomerDTO} and a 200 (OK) status
     * @throws IllegalArgumentException if the provided customer ID does not match the ID in the request URL
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> update(@PathVariable Long id, @RequestBody CustomerDTO datas) {
        if (!Objects.equals(id, datas.getId())) {
            throw new IllegalArgumentException("The id of the customer is not the same");
        }
        CustomerDTO editedData = customerService.edit(datas);
        return ResponseEntity.ok(editedData);
    }

    /**
     * Deletes a customer by their unique identifier (ID).
     * <p>This method attempts to delete the customer with the given ID from the system.</p>
     *
     * <p>If the customer is successfully deleted, a success message is returned. If the customer does not exist,
     * an exception will be thrown (such as {@link ResourceNotFoundException}).</p>
     *
     * @param id the unique identifier of the customer to be deleted
     * @return a {@link ResponseEntity} containing a success message and HTTP status 200 (OK)
     * @throws ResourceNotFoundException if no customer is found with the provided ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Customer deleted with successful.");
    }

}
