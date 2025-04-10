package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Customer Service Exception Test</h1>
 * <p>Testes para a classe de exceção {@link CustomerServiceException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class CustomerServiceExceptionTest {

    private static final String ERROR_MESSAGE = "Erro no serviço de clientes";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowCustomerServiceException() {
        CustomerServiceException exception = new CustomerServiceException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowCustomerServiceExceptionWithCorrectMessage() {
        CustomerServiceException exception = assertThrows(CustomerServiceException.class, () -> {
            throw new CustomerServiceException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 