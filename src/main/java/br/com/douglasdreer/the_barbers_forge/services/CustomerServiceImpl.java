package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.repositories.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * <h1>Customer Service Implementation</h1>
 * <p>
 * Implementação dos serviços para gerenciamento de clientes.
 * Esta classe implementa os métodos definidos na interface CustomerService,
 * oferecendo funcionalidades para busca, criação, atualização e remoção de clientes.
 * </p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final ModelMapper mapper;

    /**
     * Construtor que recebe o repositório de clientes e o ModelMapper por injeção de dependência.
     * 
     * @param repository repositório de clientes
     * @param mapper objeto para mapeamento entre entidades e DTOs
     */
    public CustomerServiceImpl(CustomerRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CustomerDTO> findAllCustomersWithPagination(int page, int pageSize) {
        Page<Customer> customers = repository.findAll(PageRequest.of(page, pageSize));
        return customers.map(customer -> mapper.map(customer, CustomerDTO.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO findCustomerById(long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
        return mapper.map(customer, CustomerDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest customerRequest) {
        Customer customer = mapper.map(customerRequest, Customer.class);
        Customer savedCustomer = repository.save(customer);
        return mapper.map(savedCustomer, CustomerDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO updateCustomer(long id, CreateCustomerRequest customerRequest) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + id));
        
        // Atualiza apenas os campos presentes no DTO
        mapper.map(customerRequest, existingCustomer);
        existingCustomer.setId(id); // Garante que o ID não seja alterado
        
        Customer updatedCustomer = repository.save(existingCustomer);
        return mapper.map(updatedCustomer, CustomerDTO.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomerById(long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com o ID: " + id);
        }
        repository.deleteById(id);
    }
}
