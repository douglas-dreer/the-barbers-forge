package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.CustomerServiceException;
import br.com.douglasdreer.the_barbers_forge.exceptions.DuplicateDataException;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceIntegrationTest {
    private final Long id = 1L;
    private final String CPF = "02886612901";
    private final String FIRST_NAME = "Millie";
    private final String LAST_NAME = "Haag";

    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    @Rollback
    public void mustReturnSuccessWhenCreateCustomer() {
        CustomerDTO dto = createAnCustomer(null);
        CustomerDTO customerSaved = customerService.create(dto);
        assertNotNull(customerSaved);
        assertNotNull(customerSaved.getId());

        CustomerDTO customerFromService = customerService.findById(customerSaved.getId());
        assertNotNull(customerFromService);
        assertEquals(dto.getFirstName(), customerFromService.getFirstName());
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnExceptionWhenDuplicateData() {
        CustomerDTO duplicateDto = createAnCustomer(null);
        duplicateDto.setCPF(CPF);
        assertThrows(DuplicateDataException.class, () -> customerService.create(duplicateDto));
    }

    @Test
    @Transactional
    public void mustReturnSuccessWhenFindCustomerById() {
        CustomerDTO customerFound = customerService.findById(2L);
        assertNotNull(customerFound);
        assertNotNull(customerFound.getId());
    }

    @Test
    @Transactional
    public void mustReturnNullWhenFindCustomerById() {
        CustomerDTO customerFound = customerService.findById(99L);
        assertNull(customerFound);
    }

    @Test
    @Transactional
    public void mustReturnSuccessWhenFindCustomerByFullName() {
        List<CustomerDTO> customers = customerService.findByFullName(FIRST_NAME, LAST_NAME);
        assertFalse((customers.isEmpty()));
        assertEquals(1, customers.size());
        assertEquals(FIRST_NAME, customers.get(0).getFirstName());
        assertEquals(LAST_NAME, customers.get(0).getLastName());
    }

    @Test
    @Transactional
    public void mustReturnSuccessWhenFindCustomerByCPF() {
        CustomerDTO customerFound = customerService.findByCpf(CPF);
        assertNotNull(customerFound);
        assertEquals(CPF, customerFound.getCPF());
    }

    @Test
    @Transactional
    public void mustReturnResourceNotFoundExceptionWhenCustomerByCPF() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.findByCpf("1234567890"));
    }

    @Test
    @Transactional
    public void mustReturnSuccessWhenFindAllCustomers() {
        List<CustomerDTO> customers = customerService.findAll();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(2, customers.size());
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnSuccessWhenEditCustomer() {
       CustomerDTO customerEdited = createAnCustomer(id);
       customerEdited.setFirstName("Mario");
       customerEdited.setLastName("Souza");

       customerService.edit(customerEdited);

       CustomerDTO customerFromService = customerService.findById(customerEdited.getId());
       assertNotNull(customerFromService);
       assertEquals(customerEdited.getFirstName(), customerFromService.getFirstName());
       assertEquals(customerEdited.getLastName(), customerFromService.getLastName());
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnResourceNotFoundExceptionWhenEditCustomer() {
        CustomerDTO customerEdited = createAnCustomer(99L);

        assertThrows(ResourceNotFoundException.class, () -> customerService.edit(customerEdited));
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnCustomerServiceExceptionWhenFirstNameIsNull() {
        assertThrows(CustomerServiceException.class, () -> {
            customerService.edit(null);
        });
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnSuccessWhenDeleteCustomer() {
        customerService.delete(id);
        assertNull(customerService.findById(id));
    }

    @Test
    @Transactional
    @Rollback
    public void mustReturnResourceNotFoundExceptionWhenDeleteCustomer() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.delete(99L));
    }

    private CustomerDTO createAnCustomer(Long withId) {
        CustomerDTO dto = new CustomerDTO();
        if (withId != null) {
            dto.setId(withId);
        }
        dto.setFirstName("João");
        dto.setLastName("Da Silva");
        dto.setCPF(RandomStringUtils.randomAlphanumeric(10));
        dto.setAddress("Avenida Brasil, 1500");
        dto.setBirthDate(LocalDate.now());
        dto.setPhone(RandomStringUtils.randomNumeric(11));
        return dto;
    }
}
