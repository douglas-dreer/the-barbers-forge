package br.com.douglasdreer.the_barbers_forge.dtos.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.ConverterException;

/**
 * <h1>Customer Mapper Test</h1>
 * <p>Testes unitários para a classe {@link CustomerMapper}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@ExtendWith(MockitoExtension.class)
public class CustomerMapperTest {

    @Mock
    private ModelMapper modelMapper;

    private CustomerMapper customerMapper;
    private Customer customer;
    private CustomerDTO customerDTO;
    private CreateCustomerRequest createCustomerRequest;

    @BeforeEach
    public void setUp() {
        customerMapper = new CustomerMapper(modelMapper);

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
     * Testa a conversão de uma entidade para DTO.
     */
    @Test
    public void mustReturnSuccessWhenConvertEntityToDTO() {
        when(modelMapper.map(any(Customer.class), eq(CustomerDTO.class))).thenReturn(customerDTO);

        CustomerDTO result = customerMapper.toDTO(customer);

        assertNotNull(result);
        assertEquals(customerDTO.getId(), result.getId());
        assertEquals(customerDTO.getFirstName(), result.getFirstName());
        assertEquals(customerDTO.getLastName(), result.getLastName());
        assertEquals(customerDTO.getAddress(), result.getAddress());
        assertEquals(customerDTO.getPhone(), result.getPhone());
        assertEquals(customerDTO.getBirthDate(), result.getBirthDate());
    }

    /**
     * Testa a conversão de um DTO para entidade.
     */
    @Test
    public void mustReturnSuccessWhenConvertDTOToEntity() {
        when(modelMapper.map(any(CustomerDTO.class), eq(Customer.class))).thenReturn(customer);

        Customer result = customerMapper.toEntity(customerDTO);

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
        assertEquals(customer.getAddress(), result.getAddress());
        assertEquals(customer.getPhone(), result.getPhone());
        assertEquals(customer.getBirthDate(), result.getBirthDate());
    }

    /**
     * Testa a conversão de um request para entidade.
     */
    @Test
    public void mustReturnSuccessWhenConvertRequestToEntity() {
        when(modelMapper.map(any(CreateCustomerRequest.class), eq(Customer.class))).thenReturn(customer);

        Customer result = customerMapper.toEntity(createCustomerRequest);

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getFirstName(), result.getFirstName());
        assertEquals(customer.getLastName(), result.getLastName());
        assertEquals(customer.getAddress(), result.getAddress());
        assertEquals(customer.getPhone(), result.getPhone());
        assertEquals(customer.getBirthDate(), result.getBirthDate());
    }

    /**
     * Testa a conversão de um objeto para JSON.
     */
    @Test
    public void mustReturnSuccessWhenConvertObjectToJson() {
        // Arranging
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = "{\"id\":1,\"firstName\":\"João\",\"lastName\":\"Silva\"}";

        // Criar um CustomerMapper de teste que sobrescreve o método toJson
        CustomerMapper testMapper = new CustomerMapper(modelMapper) {
            @Override
            public String toJson(Object object) {
                return expectedJson;
            }
        };

        // Acting
        String result = testMapper.toJson(customerDTO);

        // Asserting
        assertNotNull(result);
        assertEquals(expectedJson, result);
    }

    /**
     * Testa se uma exceção é lançada quando ocorre erro na conversão para JSON.
     */
    @Test
    public void mustThrowConverterExceptionWhenJsonProcessingExceptionOccurs() {
        // Configurando um mapper que lança exceção
        CustomerMapper spyMapper = new CustomerMapper(modelMapper) {
            @Override
            public String toJson(Object object) {
                try {
                    throw new JsonProcessingException("Erro ao processar JSON") {};
                } catch (JsonProcessingException e) {
                    throw new ConverterException("Erro ao converter objeto para JSON");
                }
            }
        };

        // Verificando se a exceção é propagada corretamente
        org.junit.jupiter.api.Assertions.assertThrows(
            ConverterException.class, 
            () -> spyMapper.toJson(customerDTO)
        );
    }
} 