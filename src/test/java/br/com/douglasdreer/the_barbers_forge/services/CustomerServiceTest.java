package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.CustomerServiceException;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * <h1>CustomerServiceTest</h1>
 * <p>Unit test class for {@link CustomerService}.
 * This class tests the functionality of customer-related operations,
 * including retrieval, creation, updating, and deletion of customer records.</p>
 *
 * <p>Tests are executed using {@link SpringExtension} to integrate with the Spring testing framework,
 * ensuring that the {@link CustomerService} and its dependencies are correctly mocked and tested.</p>
 *
 * <p>JUnit 5 and Mockito are used for test lifecycle management and mocking dependencies.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

    private final Customer entity = new Customer();
    private final CustomerDTO dto = new CustomerDTO();

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private ConverterService converterService;

    @Mock
    private ValidateDocumentService validateDocumentService;

    @Mock
    private CustomerRepository customerRepository;

    /**
     * Initializes test data before each test case.
     */
    @BeforeEach
    void setUp() {
        entity.setId(1L);
        entity.setFirstName("John");
        entity.setLastName("Douglas");
        entity.setCpf("01234567891");
        entity.setPhone("11123456879");
        entity.setAddress("5th street, 1000");
        entity.setBirthDate(LocalDate.now());
        entity.setCreatedAt(LocalDateTime.now().minusDays(15));
        entity.setUpdatedAt(LocalDateTime.now());

        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastName("Douglas");
        dto.setCpf("01234567891");
        dto.setPhone("11123456879");
        dto.setAddress("5th street, 1000");
        dto.setBirthDate(LocalDate.now());
        dto.setCreatedAt(LocalDateTime.now().minusDays(15));
        dto.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Tests successful retrieval of all customers.
     */
    @Test
    void mustReturnSuccessWhenFindAll() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(entity));
        when(converterService.convertTo(any(), any())).thenReturn(dto);

        List<CustomerDTO> resultList = customerService.findAll();

        assertFalse(resultList.isEmpty());
        verify(customerRepository, times(1)).findAll();
        verify(converterService, times(1)).convertTo(any(), any());
    }

    /**
     * Tests if CustomerServiceException is thrown when findAll fails.
     */
    @Test
    void mustReturnCustomerServiceExceptionWhenFindAll() {
        when(customerRepository.findAll()).thenThrow(new CustomerServiceException("Internal Error"));
        assertThrows(CustomerServiceException.class, () -> customerService.findAll());
    }

    /**
     * Tests successful retrieval of a customer by ID.
     */
    @Test
    void mustReturnSuccessWhenFindById() {
        Optional<Customer> customerOptional = Optional.of(entity);
        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);
        when(converterService.convertTo(any(), any())).thenReturn(dto);

        CustomerDTO result = customerService.findById(1L);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        verify(customerRepository, times(1)).findById(anyLong());
        verify(converterService, times(1)).convertTo(any(), any());
    }

    /**
     * Tests if null is returned when a customer ID is not found.
     */
    @Test
    void mustReturnNullWhenFindById() {
        Optional<Customer> customerOptional = Optional.empty();
        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        CustomerDTO result = customerService.findById(1L);

        assertNull(result);
        verify(customerRepository, times(1)).findById(anyLong());
    }

    /**
     * Tests successful retrieval of a customer by full name.
     */
    @Test
    void mustReturnSuccessWhenFindByFullName() {
        final String firstName = "John";
        final String lastName = "Douglas";

        when(customerRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Collections.singletonList(entity));
        when(converterService.convertTo(any(), any())).thenReturn(dto);

        List<CustomerDTO> resultList = customerService.findByFullName(firstName, lastName);

        assertFalse(resultList.isEmpty());
        verify(customerRepository, times(1)).findByFirstNameAndLastName(anyString(), anyString());
        verify(converterService, times(1)).convertTo(any(), any());
    }

    /**
     * Tests successful deletion of a customer by ID.
     */
    @Test
    void mustReturnSuccessWhenDelete() {
        when(customerRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(customerRepository).delete(any());

        customerService.delete(dto.getId());
        verify(customerRepository, times(1)).existsById(anyLong());
    }

    /**
     * Tests if ResourceNotFoundException is thrown when trying to delete a non-existent customer.
     */
    @Test
    void mustReturnResourceNotFoundExceptionWhenDelete() {
        when(customerRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> customerService.delete(dto.getId()));
        verify(customerRepository, times(1)).existsById(anyLong());
    }
}
