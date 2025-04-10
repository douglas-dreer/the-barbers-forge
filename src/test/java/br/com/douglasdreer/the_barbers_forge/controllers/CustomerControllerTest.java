package br.com.douglasdreer.the_barbers_forge.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.services.CustomerService;

/**
 * <h1>Customer Controller Test</h1>
 * <p>Testes unitários para a classe {@link CustomerController}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;
    
    private static final String BASE_URL = "/customers";
    private static final String FIRST_NAME = "João";
    private static final String LAST_NAME = "Silva";
    private static final String ADDRESS = "Rua das Flores, 123";
    private static final String PHONE = "(11) 98765-4321";
    private static final Long CUSTOMER_ID = 1L;

    private CustomerDTO dto;
    private CreateCustomerRequest createCustomerRequest;

    @BeforeEach
    void setUp() {
        createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setFirstName(FIRST_NAME);
        createCustomerRequest.setLastName(LAST_NAME);
        createCustomerRequest.setAddress(ADDRESS);
        createCustomerRequest.setPhone(PHONE);
        createCustomerRequest.setBirthDate(LocalDate.of(1990, 1, 15));
        createCustomerRequest.setDocumentIds(new ArrayList<>());

        dto = new CustomerDTO();
        dto.setId(CUSTOMER_ID);
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setAddress(ADDRESS);
        dto.setPhone(PHONE);
        dto.setBirthDate(LocalDate.of(1990, 1, 15));
        dto.setDocuments(new ArrayList<>());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void mustReturnSuccessWhenCreateCustomer() throws Exception {
        when(customerService.createCustomer(any())).thenReturn(dto);
        final String JSON_CONTENT = objectMapper.writeValueAsString(createCustomerRequest);

        MockHttpServletRequestBuilder postMethod = post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_CONTENT);

        mockMvc.perform(postMethod)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.address").value(dto.getAddress()))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()));
    }

    @Test
    void mustReturnSuccessWhenListCustomersWithPagination() throws Exception {
        Page<CustomerDTO> customerPagined = new PageImpl<>(List.of(dto));
        when(customerService.findAllCustomersWithPagination(anyInt(), anyInt())).thenReturn(customerPagined);

        MockHttpServletRequestBuilder getMethod = get(BASE_URL)
                .param("page", "0")
                .param("pageSize", "10");

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(customerPagined.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(customerPagined.getTotalPages()))
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.content[0].lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.content[0].address").value(dto.getAddress()))
                .andExpect(jsonPath("$.content[0].phone").value(dto.getPhone()));
    }

    @Test
    void mustReturnSuccessWhenFindCustomerById() throws Exception {
        when(customerService.findCustomerById(anyLong())).thenReturn(dto);

        MockHttpServletRequestBuilder getMethod = get(BASE_URL + "/{id}", CUSTOMER_ID);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.address").value(dto.getAddress()))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()));
    }

    @Test
    void mustReturnSuccessWhenUpdateCustomer() throws Exception {
        when(customerService.updateCustomer(anyLong(), any())).thenReturn(dto);
        final String JSON_CONTENT = objectMapper.writeValueAsString(createCustomerRequest);

        MockHttpServletRequestBuilder putMethod = put(BASE_URL + "/{id}", CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_CONTENT);

        mockMvc.perform(putMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.firstName").value(dto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(dto.getLastName()))
                .andExpect(jsonPath("$.address").value(dto.getAddress()))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()));
    }

    @Test
    void mustReturnSuccessWhenDeleteCustomerById() throws Exception {
        final String MSG_SUCCESS = "Cliente removido com sucesso";
        doNothing().when(customerService).deleteCustomerById(anyLong());

        mockMvc.perform(delete(BASE_URL + "/{id}", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(MSG_SUCCESS));
    }
} 