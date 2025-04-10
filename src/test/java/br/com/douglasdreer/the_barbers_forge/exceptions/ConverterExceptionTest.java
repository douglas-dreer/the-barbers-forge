package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Converter Exception Test</h1>
 * <p>Testes para a classe de exceção {@link ConverterException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class ConverterExceptionTest {

    private static final String ERROR_MESSAGE = "Erro na conversão de dados";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowConverterException() {
        ConverterException exception = new ConverterException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowConverterExceptionWithCorrectMessage() {
        ConverterException exception = assertThrows(ConverterException.class, () -> {
            throw new ConverterException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 