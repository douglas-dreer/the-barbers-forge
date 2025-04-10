package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Resource Not Found Exception Test</h1>
 * <p>Testes para a classe de exceção {@link ResourceNotFoundException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class ResourceNotFoundExceptionTest {

    private static final String ERROR_MESSAGE = "Recurso não encontrado";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowResourceNotFoundExceptionWithCorrectMessage() {
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 