package br.com.douglasdreer.the_barbers_forge.services.integration;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.ConverterServiceException;
import br.com.douglasdreer.the_barbers_forge.services.ConverterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>ConverterServiceTest</h1>
 * <p>Unit test class for {@link ConverterService}.
 * This class tests the functionality of object conversion operations,
 * including object mapping and JSON serialization/deserialization.</p>
 *
 * <p>Tests are executed using {@link SpringBootTest} to load the application context,
 * ensuring that the {@link ConverterService} is correctly autowired.</p>
 *
 * <p>JUnit 5 and Spring Extension are used for test lifecycle management.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ConverterServiceTest {

    @Autowired
    private ConverterService converterService;

    private Customer customer;

    /**
     * Sets up the test environment by initializing a {@link Customer} object.
     * This method is executed before each test.
     */
    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Douglas");
        customer.setLastName("Dreer");
        customer.setPhone("123456789");
        customer.setAddress("Rua Principal, 123");
        customer.setBirthDate(LocalDate.now());
    }

    /**
     * Tests the successful conversion of a {@link Customer} object to a {@link CustomerDTO}.
     * Verifies that the resulting object is not null and that the fields match the original object.
     */
    @Test
    void mustReturnSuccessWhenConvertTo() {
        CustomerDTO result = converterService.convertTo(customer, CustomerDTO.class);

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getFirstName(), result.getFirstName());
    }

    /**
     * Tests whether the {@code convertTo} method throws a {@link ConverterServiceException}
     * when the conversion fails due to a null parameter.
     */
    @Test
    void mustReturnConverterExceptionWhenConvertTo() {
        assertThrows(ConverterServiceException.class, () -> converterService.convertTo(null, String.class));
    }

    /**
     * Tests the successful conversion of a {@link Customer} object to its JSON string representation.
     * Verifies that the resulting JSON is not null and contains the customer's first name.
     *
     * @throws JsonProcessingException if JSON processing fails
     */
    @Test
    void mustReturnSuccessWhenToJSON() throws JsonProcessingException {
        String json = converterService.toJSON(customer);
        assertNotNull(json);
        assertTrue(json.contains("Douglas"));
    }

    /**
     * Tests the successful conversion of a JSON string back to a {@link Customer} object.
     * Verifies that the resulting object is not null and that the fields match the original object.
     *
     * @throws IOException if JSON deserialization fails
     */
    @Test
    void mustReturnSuccessWhenToObject() throws IOException {
        String json = converterService.toJSON(customer);

        Customer result = converterService.toObject(json, Customer.class);

        assertNotNull(result);
        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
    }

    /**
     * Tests the successful mapping of a list of {@link Customer} objects to a list of {@link CustomerDTO} objects.
     * Verifies that the list is not empty and that the mapped elements have the same properties as the original ones.
     *
     */
    @Test
    void mustReturnSuccessWhenMapList() {
        final List<Customer> entityList = Collections.singletonList(customer);
        final List<CustomerDTO> resultList = converterService.mapList(entityList, CustomerDTO.class);
        assertFalse(resultList.isEmpty());
        assertEquals(1, resultList.size());
        assertEquals(customer.getFirstName(), resultList.getFirst().getFirstName());
    }
}
