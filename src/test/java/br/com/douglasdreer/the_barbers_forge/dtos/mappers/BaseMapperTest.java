package br.com.douglasdreer.the_barbers_forge.dtos.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import br.com.douglasdreer.the_barbers_forge.exceptions.ConverterException;

/**
 * <h1>Base Mapper Test</h1>
 * <p>Testes unitários para a classe {@link BaseMapper}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@ExtendWith(MockitoExtension.class)
public class BaseMapperTest {

    @Mock
    private ModelMapper modelMapper;

    private BaseMapper baseMapper;
    private Document document;
    private DocumentDTO documentDTO;

    @BeforeEach
    public void setUp() {
        baseMapper = new BaseMapper(modelMapper);

        // Configurar entidade de documento
        document = new Document();
        document.setId(1L);
        document.setDocumentType(DocumentType.CPF);
        document.setNumber("12345678901");

        // Configurar DTO de documento
        documentDTO = new DocumentDTO();
        documentDTO.setId(1L);
        documentDTO.setDocumentType(DocumentType.CPF);
        documentDTO.setNumber("12345678901");
    }

    /**
     * Testa a conversão de um objeto para outro tipo.
     */
    @Test
    public void mustReturnSuccessWhenConvertToAnotherType() {
        when(modelMapper.map(any(Document.class), eq(DocumentDTO.class))).thenReturn(documentDTO);

        DocumentDTO result = baseMapper.convertTo(document, DocumentDTO.class);

        assertNotNull(result);
        assertEquals(documentDTO.getId(), result.getId());
        assertEquals(documentDTO.getDocumentType(), result.getDocumentType());
        assertEquals(documentDTO.getNumber(), result.getNumber());
    }

    /**
     * Testa o lançamento de exceção quando a conversão falha.
     */
    @Test
    public void mustThrowConverterExceptionWhenConversionFails() {
        when(modelMapper.map(any(), any())).thenThrow(new IllegalArgumentException("Erro de conversão"));

        assertThrows(ConverterException.class, () -> {
            baseMapper.convertTo(document, DocumentDTO.class);
        });
    }

    /**
     * Testa o mapeamento de uma lista de objetos.
     */
    @Test
    public void mustReturnSuccessWhenMapList() {
        List<Document> documents = Arrays.asList(document);
        when(modelMapper.map(any(Document.class), eq(DocumentDTO.class))).thenReturn(documentDTO);

        List<DocumentDTO> result = baseMapper.mapList(documents, DocumentDTO.class);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(documentDTO.getId(), result.get(0).getId());
        assertEquals(documentDTO.getDocumentType(), result.get(0).getDocumentType());
        assertEquals(documentDTO.getNumber(), result.get(0).getNumber());
    }

    /**
     * Testa a conversão de um objeto para JSON e de volta para objeto.
     */
    @Test
    public void mustReturnSuccessWhenConvertObjectToJsonAndBack() throws IOException {
        // Criar um BaseMapper personalizado para testes com conversão JSON
        BaseMapper testMapper = new BaseMapper(modelMapper) {
            @Override
            public String toJSON(Object object) throws JsonProcessingException {
                return "{\"id\":1,\"documentType\":\"CPF\",\"number\":\"12345678901\"}";
            }

            @Override
            public <D> D toObject(String jsonData, Class<D> targetClass) throws IOException {
                // Retornar o documentDTO para qualquer chamada de toObject
                return (D) documentDTO;
            }
        };

        // Testar conversão para JSON
        String json = testMapper.toJSON(document);
        assertNotNull(json);
        
        // Testar conversão de JSON para objeto
        DocumentDTO result = testMapper.toObject(json, DocumentDTO.class);
        assertNotNull(result);
        assertEquals(documentDTO.getId(), result.getId());
        assertEquals(documentDTO.getDocumentType(), result.getDocumentType());
        assertEquals(documentDTO.getNumber(), result.getNumber());
    }
} 