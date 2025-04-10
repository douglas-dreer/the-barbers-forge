package br.com.douglasdreer.the_barbers_forge.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.repositories.CustomerRepository;

/**
 * <h1>Customer Service Test</h1>
 * <p>Testes unitários para a classe {@link CustomerServiceImpl}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    private Customer customer;
    private CustomerDTO customerDTO;
    private CreateCustomerRequest createCustomerRequest;

    @BeforeEach
    public void setUp() {
        // Configurar entidade de cliente
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("João");
        customer.setLastName("Silva");
        customer.setAddress("Rua das Flores, 123");
        customer.setPhone("(11) 98765-4321");
        customer.setBirthDate(LocalDate.of(1990, 1, 15));
        customer.setDocuments(new ArrayList<>());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        // Configurar DTO de cliente
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setFirstName("João");
        customerDTO.setLastName("Silva");
        customerDTO.setAddress("Rua das Flores, 123");
        customerDTO.setPhone("(11) 98765-4321");
        customerDTO.setBirthDate(LocalDate.of(1990, 1, 15));
        customerDTO.setDocuments(new ArrayList<>());
        customerDTO.setCreatedAt(LocalDateTime.now());
        customerDTO.setUpdatedAt(LocalDateTime.now());

        // Configurar request de criação de cliente
        createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setFirstName("João");
        createCustomerRequest.setLastName("Silva");
        createCustomerRequest.setAddress("Rua das Flores, 123");
        createCustomerRequest.setPhone("(11) 98765-4321");
        createCustomerRequest.setBirthDate(LocalDate.of(1990, 1, 15));
        createCustomerRequest.setDocumentIds(new ArrayList<>());
    }

    /**
     * Testa a criação de um cliente.
     */
    @Test
    public void mustReturnSuccessWhenCreateCustomer() {
        when(modelMapper.map(any(CreateCustomerRequest.class), eq(Customer.class))).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(createCustomerRequest);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    /**
     * Testa a busca de um cliente por ID.
     */
    @Test
    public void mustReturnSuccessWhenFindCustomerById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        CustomerDTO result = customerService.findCustomerById(1L);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        
        verify(customerRepository, times(1)).findById(anyLong());
    }

    /**
     * Testa a exceção quando um cliente não é encontrado por ID.
     */
    @Test
    public void mustReturnResourceNotFoundExceptionWhenFindCustomerByIdWithInvalidId() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.findCustomerById(999L));
        
        verify(customerRepository, times(1)).findById(anyLong());
    }

    /**
     * Testa a atualização de um cliente.
     */
    @Test
    public void mustReturnSuccessWhenUpdateCustomer() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        
        
        doAnswer(invocation -> null)
        .when(modelMapper)
        .map(any(), eq(customer));
        
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        CustomerDTO result = customerService.updateCustomer(1L, createCustomerRequest);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        
        verify(customerRepository, times(1)).findById(anyLong());
        verify(modelMapper, times(1)).map(any(CreateCustomerRequest.class), eq(customer));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    /**
     * Testa a exceção quando um cliente a ser atualizado não é encontrado.
     */
    @Test
    public void mustReturnResourceNotFoundExceptionWhenUpdateCustomerWithInvalidId() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            customerService.updateCustomer(999L, createCustomerRequest));
        
        verify(customerRepository, times(1)).findById(anyLong());
    }

    /**
     * Testa a exclusão de um cliente.
     */
    @Test
    public void mustReturnSuccessWhenDeleteCustomerById() {
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomerById(1L);

        verify(customerRepository, times(1)).existsById(anyLong());
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

    /**
     * Testa a exceção quando um cliente a ser excluído não é encontrado.
     */
    @Test
    public void mustReturnResourceNotFoundExceptionWhenDeleteCustomerByIdWithInvalidId() {
        when(customerRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomerById(999L));
        
        verify(customerRepository, times(1)).existsById(anyLong());
    }

    /**
     * Testa a busca de todos os clientes com paginação.
     */
    @Test
    public void mustReturnSuccessWhenFindAllCustomersWithPagination() {
        Page<Customer> customerPage = new PageImpl<>(java.util.List.of(customer));
        Page<CustomerDTO> dtoPage = new PageImpl<>(java.util.List.of(customerDTO));
        
        when(customerRepository.findAll(any(PageRequest.class))).thenReturn(customerPage);
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        Page<CustomerDTO> result = customerService.findAllCustomersWithPagination(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        verify(customerRepository, times(1)).findAll(any(PageRequest.class));
    }
} 