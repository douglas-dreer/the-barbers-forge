package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Validate Document Service Exception Test</h1>
 * <p>Testes para a classe de exceção {@link ValidateDocumentServiceException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class ValidateDocumentServiceExceptionTest {

    private static final String ERROR_MESSAGE = "Erro na validação do documento";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowValidateDocumentServiceException() {
        ValidateDocumentServiceException exception = new ValidateDocumentServiceException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowValidateDocumentServiceExceptionWithCorrectMessage() {
        ValidateDocumentServiceException exception = assertThrows(ValidateDocumentServiceException.class, () -> {
            throw new ValidateDocumentServiceException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 