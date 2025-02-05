package br.com.douglasdreer.the_barbers_forge.controllers;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.services.ConverterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * <h1>CustomerController Integration Tests</h1>
 *
 * <p>Integration tests for the <code>CustomerController</code> class.</p>
 *
 * <p>These tests cover various endpoints of the <code>CustomerController</code> to ensure they work as expected.</p>
 *
 * <p><b>Author:</b> Douglas Dreer</p>
 * <p><b>Since:</b> 2025</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback
public class CustomerControllerIntegrationTest {

    private static final String BASE_URL = "/customers";
    private static final CustomerDTO data = new CustomerDTO();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConverterService converterService;

    /**
     * <h3>Setup Test Data</h3>
     *
     * <p>Initializes test data before any tests are run.</p>
     *
     * @since 2025
     */
    @BeforeAll
    public static void setup() {
        data.setFirstName("Joaquin");
        data.setLastName("Phoenix");
        data.setCpf("01234567890");
        data.setPhone("11911111111");
        data.setBirthDate(LocalDate.now());
        data.setAddress("Rua dos Bobos, 0");
    }

    /**
     * <h3>Test: Find All Customers</h3>
     *
     * <p>Ensures that retrieving all customers returns a successful response.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenFindAllCustomers() throws Exception {
        MockHttpServletRequestBuilder getMethod = get(BASE_URL);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    /**
     * <h3>Test: Find Customer by Full Name</h3>
     *
     * <p>Ensures that searching for a customer by full name returns the correct customer.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenFindByFullName() throws Exception {
        final String FIRST_NAME = "João";
        final String LAST_NAME = "Da Silva";

        MockHttpServletRequestBuilder getMethod = get(BASE_URL)
                .param("firstName", FIRST_NAME)
                .param("lastName", LAST_NAME);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", is(LAST_NAME)));
    }

    /**
     * <h3>Test: Find Customer by ID</h3>
     *
     * <p>Ensures that retrieving a customer by ID returns the expected result.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenFindById() throws Exception {
        final String URL = BASE_URL + "/1";
        MockHttpServletRequestBuilder getMethod = get(URL);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    /**
     * <h3>Test: Find Customer by cpf</h3>
     *
     * <p>Ensures that retrieving a customer by cpf returns the expected result.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenFindBycpf() throws Exception {
        final String cpf = "23456789012";
        MockHttpServletRequestBuilder getMethod = get(BASE_URL)
                .param("cpf", cpf);
        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", is(cpf)));
    }


    /**
     * <h3>Test: Create New Customer</h3>
     *
     * <p>Ensures that creating a new customer works correctly and returns a created status.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenCreateCustomer() throws Exception {
        final String DATA_JSON = converterService.toJSON(data);

        MockHttpServletRequestBuilder postMethod = post(BASE_URL)
                .content(DATA_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(postMethod)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    /**
     * <h3>Test: Update Customer</h3>
     *
     * <p>Ensures that updating an existing customer returns the updated customer data.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @see #mustReturnIllegalArgumentExceptionWhenUpdateCustomer()
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenUpdateCustomer() throws Exception {
        final String FIRST_NAME = "Alejandro";
        final String LAST_NAME = "García";

        data.setId(1L);
        data.setFirstName(FIRST_NAME);
        data.setLastName(LAST_NAME);

        final String DATA_JSON = converterService.toJSON(data);
        final String URL = BASE_URL + "/1";

        MockHttpServletRequestBuilder putMethod = put(URL)
                .content(DATA_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(putMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(LAST_NAME)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    /**
     * <h3>Test: Update Customer with Mismatched ID</h3>
     *
     * <p>Ensures that attempting to update a customer with a mismatched ID results in a Bad Request error.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @see #mustReturnSuccessWhenUpdateCustomer()
     * @since 2025
     */
    @Test
    void mustReturnIllegalArgumentExceptionWhenUpdateCustomer() throws Exception {
        final String FIRST_NAME = "Alejandro";
        final String LAST_NAME = "García";

        data.setId(2L);
        data.setFirstName(FIRST_NAME);
        data.setLastName(LAST_NAME);

        final String DATA_JSON = converterService.toJSON(data);
        final String URL = BASE_URL + "/1";

        MockHttpServletRequestBuilder putMethod = put(URL)
                .content(DATA_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(putMethod)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }

    /**
     * <h3>Test: Delete Customer</h3>
     *
     * <p>Ensures that deleting a customer returns a success message and status code.</p>
     *
     * @throws Exception if an error occurs during the request.
     * @since 2025
     */
    @Test
    void mustReturnSuccessWhenDeleteCustomer() throws Exception {
        final String URL = BASE_URL + "/1";
        final String MSG_SUCCESS = "Customer deleted with successfully.";
        MockHttpServletRequestBuilder deleteMethod = delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(deleteMethod)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(MSG_SUCCESS)));
    }
}
