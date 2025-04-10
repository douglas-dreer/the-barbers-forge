package br.com.douglasdreer.the_barbers_forge.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * <h1>Document Service Exception Test</h1>
 * <p>Testes para a classe de exceção {@link DocumentServiceException}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
public class DocumentServiceExceptionTest {

    private static final String ERROR_MESSAGE = "Erro no serviço de documentos";

    /**
     * Testa se a exceção é criada corretamente com a mensagem especificada.
     */
    @Test
    public void mustReturnCorrectExceptionMessageWhenThrowDocumentServiceException() {
        DocumentServiceException exception = new DocumentServiceException(ERROR_MESSAGE);
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    /**
     * Testa se a exceção é lançada corretamente.
     */
    @Test
    public void mustThrowDocumentServiceExceptionWithCorrectMessage() {
        DocumentServiceException exception = assertThrows(DocumentServiceException.class, () -> {
            throw new DocumentServiceException(ERROR_MESSAGE);
        });
        
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
} 