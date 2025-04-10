package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Unique Constraint Violation Exception Test</h1>
 * <p>Testes para a classe de exceção {@link UniqueConstraintViolationException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class UniqueConstraintViolationExceptionTest {

    private static final String ERROR_MESSAGE = "Registro duplicado encontrado";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowUniqueConstraintViolationException() {
        UniqueConstraintViolationException exception = new UniqueConstraintViolationException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowUniqueConstraintViolationExceptionWithCorrectMessage() {
        UniqueConstraintViolationException exception = assertThrows(UniqueConstraintViolationException.class, () -> {
            throw new UniqueConstraintViolationException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 