package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Duplicate Data Exception Test</h1>
 * <p>Testes para a classe de exceção {@link DuplicateDataException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class DuplicateDataExceptionTest {

    private static final String ERROR_MESSAGE = "Dado duplicado encontrado";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowDuplicateDataException() {
        DuplicateDataException exception = new DuplicateDataException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowDuplicateDataExceptionWithCorrectMessage() {
        DuplicateDataException exception = assertThrows(DuplicateDataException.class, () -> {
            throw new DuplicateDataException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 