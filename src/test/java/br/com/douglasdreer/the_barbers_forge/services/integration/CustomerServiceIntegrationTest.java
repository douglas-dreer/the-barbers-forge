package br.com.douglasdreer.the_barbers_forge.services.integration;

import br.com.douglasdreer.the_barbers_forge.TheBarbersForgeApplication;
import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.CustomerServiceException;
import br.com.douglasdreer.the_barbers_forge.exceptions.DuplicateDataException;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.services.CustomerService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TheBarbersForgeApplication.class)
@ActiveProfiles("test")
@Transactional
@Rollback
public class CustomerServiceIntegrationTest {
    private static final Long ID = 1L;
    private static final String CPF = "12345678901";
    private static final String FIRST_NAME = "João";
    private static final String LAST_NAME = "Da Silva";

    @Autowired
    private CustomerService customerService;

    @Test
    void mustReturnSuccessWhenCreateCustomer() {
        CustomerDTO dto = createAnCustomer(null);
        CustomerDTO customerSaved = customerService.create(dto);
        assertNotNull(customerSaved);
        assertNotNull(customerSaved.getId());

        CustomerDTO customerFromService = customerService.findById(customerSaved.getId());
        assertNotNull(customerFromService);
        assertEquals(dto.getFirstName(), customerFromService.getFirstName());
    }

    @Test
    void mustReturnDuplicateDataExceptionWhenCreateCustomer() {
        CustomerDTO duplicateDto = createAnCustomer(null);
        duplicateDto.setCpf(CPF);
        assertThrows(DuplicateDataException.class, () -> customerService.create(duplicateDto));
    }

    @Test
    void mustReturnSuccessWhenFindCustomerById() {
        CustomerDTO customerFound = customerService.findById(2L);
        assertNotNull(customerFound);
        assertNotNull(customerFound.getId());
    }

    @Test
    void mustReturnNullWhenFindCustomerById() {
        CustomerDTO customerFound = customerService.findById(99L);
        assertNull(customerFound);
    }

    @Test
    void mustReturnSuccessWhenFindCustomerByFullName() {
        List<CustomerDTO> customers = customerService.findByFullName(FIRST_NAME, LAST_NAME);
        assertFalse((customers.isEmpty()));
        assertEquals(1, customers.size());
        assertEquals(FIRST_NAME, customers.getFirst().getFirstName());
        assertEquals(LAST_NAME, customers.getFirst().getLastName());
    }

    @Test
    void mustReturnSuccessWhenFindCustomerByCpf() {
        CustomerDTO customerFound = customerService.findbyCpf(CPF);
        assertNotNull(customerFound);
        assertEquals(CPF, customerFound.getCpf());
    }

    @Test
    void mustReturnResourceNotFoundExceptionWhenCustomerByCpf() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> customerService.findbyCpf("1234567890"));
    }

    @Test
     void mustReturnSuccessWhenFindAllCustomers() {
        List<CustomerDTO> customers = customerService.findAll();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(3, customers.size());
    }

    @Test
     void mustReturnSuccessWhenEditCustomer() {
       CustomerDTO customerEdited = createAnCustomer(ID);
       customerEdited.setFirstName("Mario");
       customerEdited.setLastName("Souza");

       customerService.edit(customerEdited);

       CustomerDTO customerFromService = customerService.findById(customerEdited.getId());
       assertNotNull(customerFromService);
       assertEquals(customerEdited.getFirstName(), customerFromService.getFirstName());
       assertEquals(customerEdited.getLastName(), customerFromService.getLastName());
    }

    @Test
     void mustReturnResourceNotFoundExceptionWhenEditCustomer() {
        CustomerDTO customerEdited = createAnCustomer(99L);

        assertThrows(ResourceNotFoundException.class, () -> customerService.edit(customerEdited));
    }

    @Test
     void mustReturnCustomerServiceExceptionWhenFirstNameIsNull() {
        assertThrows(CustomerServiceException.class, () -> {
            customerService.edit(null);
        });
    }

    @Test
     void mustReturnSuccessWhenDeleteCustomer() {
        customerService.delete(ID);
        assertNull(customerService.findById(ID));
    }

    @Test
     void mustReturnResourceNotFoundExceptionWhenDeleteCustomer() {
        assertThrows(ResourceNotFoundException.class, () -> customerService.delete(99L));
    }

    private CustomerDTO createAnCustomer(Long withId) {
        CustomerDTO dto = new CustomerDTO();
        if (withId != null) {
            dto.setId(withId);
        }
        dto.setFirstName("Lucas");
        dto.setLastName("Da Silva");
        dto.setCpf(RandomStringUtils.randomNumeric(11));
        dto.setAddress("Rua São Paulo, 1320");
        dto.setBirthDate(LocalDate.now());
        dto.setPhone(RandomStringUtils.randomNumeric(11));
        return dto;
    }
}
