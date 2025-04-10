package br.com.douglasdreer.the_barbers_forge.dtos.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;

/**
 * <h1>Document Mapper Test</h1>
 * <p>Testes unitários para a classe {@link DocumentMapper}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@ExtendWith(MockitoExtension.class)
public class DocumentMapperTest {

    @Mock
    private ModelMapper modelMapper;

    private DocumentMapper documentMapper;
    private Document document;
    private DocumentDTO documentDTO;
    private CreateDocumentRequest createDocumentRequest;

    @BeforeEach
    public void setUp() {
        documentMapper = new DocumentMapper(modelMapper);

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

        // Configurar request de criação de documento
        createDocumentRequest = new CreateDocumentRequest(DocumentType.CPF, "12345678901");
    }

    /**
     * Testa a conversão de uma entidade para DTO.
     */
    @Test
    public void mustReturnSuccessWhenConvertEntityToDTO() {
        when(modelMapper.map(any(Document.class), eq(DocumentDTO.class))).thenReturn(documentDTO);

        DocumentDTO result = documentMapper.toDTO(document);

        assertNotNull(result);
        assertEquals(documentDTO.getId(), result.getId());
        assertEquals(documentDTO.getDocumentType(), result.getDocumentType());
        assertEquals(documentDTO.getNumber(), result.getNumber());
    }

    /**
     * Testa a conversão de um DTO para entidade.
     */
    @Test
    public void mustReturnSuccessWhenConvertDTOToEntity() {
        when(modelMapper.map(any(DocumentDTO.class), eq(Document.class))).thenReturn(document);

        Document result = documentMapper.toEntity(documentDTO);

        assertNotNull(result);
        assertEquals(document.getId(), result.getId());
        assertEquals(document.getDocumentType(), result.getDocumentType());
        assertEquals(document.getNumber(), result.getNumber());
    }

    /**
     * Testa a conversão de um request para entidade.
     */
    @Test
    public void mustReturnSuccessWhenConvertRequestToEntity() {
        when(modelMapper.map(any(CreateDocumentRequest.class), eq(Document.class))).thenReturn(document);

        Document result = documentMapper.toEntity(createDocumentRequest);

        assertNotNull(result);
        assertEquals(document.getId(), result.getId());
        assertEquals(document.getDocumentType(), result.getDocumentType());
        assertEquals(document.getNumber(), result.getNumber());
    }

    /**
     * Testa a conversão de um objeto para JSON.
     */
    @Test
    public void mustReturnSuccessWhenConvertObjectToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Criar o JSON esperado usando o mesmo objeto DTO
        String expectedJson = objectMapper.writeValueAsString(documentDTO);
        
        String result = documentMapper.toJson(documentDTO);

        assertNotNull(result);
        
        // Comparar os JSONs após deserializar para eliminar diferenças de formatação
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode resultNode = objectMapper.readTree(result);
        assertEquals(expectedNode, resultNode);
    }
} 