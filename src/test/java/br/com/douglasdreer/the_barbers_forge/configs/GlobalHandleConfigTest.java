package br.com.douglasdreer.the_barbers_forge.configs;

import br.com.douglasdreer.the_barbers_forge.dtos.ErrorDTO;
import br.com.douglasdreer.the_barbers_forge.exceptions.ResourceNotFoundException;
import br.com.douglasdreer.the_barbers_forge.exceptions.UniqueConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>GlobalHandleConfigTest</h1>
 * <p>Testes para a classe de configuração {@link GlobalHandleConfig}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class GlobalHandleConfigTest {

    private GlobalHandleConfig globalHandleConfig;

    @BeforeEach
    void setUp() {
        globalHandleConfig = new GlobalHandleConfig();
    }

    /**
     * Testa o tratamento de exceção para DataIntegrityViolationException relacionada a unique constraint.
     */
    @Test
    void handleDataIntegrityViolationExceptionShouldReturnBadRequestForUniqueConstraint() {
        // Cria uma exceção com mensagem contendo "unique constraint"
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Violation of unique constraint") {
            @Override
            public Throwable getCause() {
                return new Exception("unique constraint");
            }
        };

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleDataIntegrityViolationException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Status code deve ser BAD_REQUEST");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(400L, response.getBody().getCode(), "Código de erro deve ser 400");
        assertEquals("Duplicate Entry", response.getBody().getTitle(), "Título deve indicar entrada duplicada");
        assertTrue(response.getBody().getMessage().contains("cpf"), "Mensagem deve mencionar o campo CPF");
    }

    /**
     * Testa o tratamento de exceção para DataIntegrityViolationException genérica.
     */
    @Test
    void handleDataIntegrityViolationExceptionShouldReturnInternalServerErrorForOtherCases() {
        // Cria uma exceção sem referência a unique constraint
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Database error");

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleDataIntegrityViolationException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "Status code deve ser INTERNAL_SERVER_ERROR");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(500L, response.getBody().getCode(), "Código de erro deve ser 500");
        assertEquals("Internal Server Error", response.getBody().getTitle(), "Título deve indicar erro interno");
    }

    /**
     * Testa o tratamento de exceção para UniqueConstraintViolationException.
     */
    @Test
    void handleUniqueConstraintViolationExceptionShouldReturnBadRequest() {
        // Mensagem personalizada para a exceção
        String errorMessage = "CPF já cadastrado no sistema";
        UniqueConstraintViolationException exception = new UniqueConstraintViolationException(errorMessage);

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleUniqueConstraintViolationException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Status code deve ser BAD_REQUEST");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(400L, response.getBody().getCode(), "Código de erro deve ser 400");
        assertEquals("Duplicate Entry", response.getBody().getTitle(), "Título deve indicar entrada duplicada");
        assertEquals(errorMessage, response.getBody().getMessage(), "Mensagem deve ser a mesma da exceção");
    }

    /**
     * Testa o tratamento de exceção para IllegalArgumentException.
     */
    @Test
    void handleIllegalArgumentExceptionShouldReturnBadRequest() {
        // Mensagem personalizada para a exceção
        String errorMessage = "Parâmetro inválido";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleIllegalArgumentException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Status code deve ser BAD_REQUEST");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(400L, response.getBody().getCode(), "Código de erro deve ser 400");
        assertEquals("Invalid Parameter", response.getBody().getTitle(), "Título deve indicar parâmetro inválido");
        assertEquals(errorMessage, response.getBody().getMessage(), "Mensagem deve ser a mesma da exceção");
    }

    /**
     * Testa o tratamento de exceção para ResourceNotFoundException.
     */
    @Test
    void handleResourceNotFoundExceptionShouldReturnNotFound() {
        // Mensagem personalizada para a exceção
        String errorMessage = "Cliente não encontrado com o ID: 1";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleResourceNotFoundException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Status code deve ser NOT_FOUND");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(404L, response.getBody().getCode(), "Código de erro deve ser 404");
        assertEquals("Resource Not Found", response.getBody().getTitle(), "Título deve indicar recurso não encontrado");
        assertEquals(errorMessage, response.getBody().getMessage(), "Mensagem deve ser a mesma da exceção");
    }

    /**
     * Testa o tratamento de exceção para UnsupportedOperationException.
     */
    @Test
    void handleUnsupportedOperationExceptionShouldReturnInternalServerError() {
        // Cria a exceção
        UnsupportedOperationException exception = new UnsupportedOperationException();

        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleUnsupportedOperationException(exception);

        // Verifica a resposta
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "Status code deve ser INTERNAL_SERVER_ERROR");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(500L, response.getBody().getCode(), "Código de erro deve ser 500");
        assertEquals("Internal Server Error", response.getBody().getTitle(), "Título deve indicar erro interno");
        assertEquals("Function not implement still.", response.getBody().getMessage(), "Mensagem deve indicar que a função não está implementada");
    }

    /**
     * Testa o tratamento de exceção genérica.
     */
    @Test
    void handleGeneralExceptionShouldReturnInternalServerError() {
        // Executa o handler
        ResponseEntity<ErrorDTO> response = globalHandleConfig.handleGeneralException();

        // Verifica a resposta
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode(), "Status code deve ser INTERNAL_SERVER_ERROR");
        assertNotNull(response.getBody(), "Corpo da resposta não deve ser nulo");
        assertEquals(500L, response.getBody().getCode(), "Código de erro deve ser 500");
        assertEquals("Internal Server Error", response.getBody().getTitle(), "Título deve indicar erro interno");
        assertEquals("An unexpected error occurred.", response.getBody().getMessage(), "Mensagem deve indicar erro inesperado");
    }
} 