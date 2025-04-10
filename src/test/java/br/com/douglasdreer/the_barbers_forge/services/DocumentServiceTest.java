package br.com.douglasdreer.the_barbers_forge.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.mappers.DocumentMapper;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import br.com.douglasdreer.the_barbers_forge.exceptions.DocumentServiceException;
import br.com.douglasdreer.the_barbers_forge.repositories.DocumentRepository;


@ExtendWith(MockitoExtension.class)
public class DocumentServiceTest {
    @InjectMocks
    private DocumentServiceImpl documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    private Document entity = new Document();
    private DocumentDTO dto = new DocumentDTO();
    private CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest();
    private ParamDocumentRequest params = new ParamDocumentRequest();   

    
    @BeforeEach
    public void setUp() {
        createDocumentRequest = new CreateDocumentRequest(
            DocumentType.CPF,
            "1234567890"
        );

        params = new ParamDocumentRequest(
            "1234567890",
            DocumentType.CPF
        );        

        entity = new Document();
        entity.setId(1L);
        entity.setDocumentType(DocumentType.CPF);
        entity.setNumber("1234567890");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        dto = new DocumentDTO();
        dto.setId(1L);
        dto.setDocumentType(DocumentType.CPF);
        dto.setNumber("1234567890");
    }

    @Test
    public void mustReturnSuccessWhenCreateDocument() {
        Optional<Document> documentOptional = Optional.empty();

        when(documentMapper.toEntity(any())).thenReturn(entity);
        when(documentMapper.toDTO(any())).thenReturn(dto);
        when(documentRepository.findByDocumentTypeAndNumber(any(), anyString())).thenReturn(documentOptional);
        when(documentRepository.save(any())).thenReturn(entity);

        DocumentDTO result = documentService.createDocument(createDocumentRequest);

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDocumentType(), result.getDocumentType());
        assertEquals(entity.getNumber(), result.getNumber());

        verify(documentRepository, times(1)).save(any());
        verify(documentRepository, times(1)).findByDocumentTypeAndNumber(any(), anyString());
    }

    @Test
    public void mustReturnDocumentServiceExceptionWhenCreateDocumentWithDocumentAlreadyExists() {
        when(documentRepository.findByDocumentTypeAndNumber(any(), anyString())).thenReturn(Optional.of(entity));
        when(documentMapper.toEntity(any())).thenReturn(entity);

        assertThrows(DocumentServiceException.class, () -> documentService.createDocument(createDocumentRequest));
    }
    
    /**
     * Teste para verificar se o método listDocument retorna uma lista
     * de documentos com sucesso.
     */
    @Test
    public void mustReturnSuccessWhenListDocuments() {
        List<Document> documents = List.of(entity);
        when(documentRepository.findAll()).thenReturn(documents);
        when(documentMapper.toDTO(any())).thenReturn(new DocumentDTO());

        List<DocumentDTO> result = documentService.listDocument();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(documentRepository, times(1)).findAll();
    }

    @Test
    public void mustReturnSuccessWhenFindDocumentsWithPagination() {
        Page<Document> page = mock(Page.class);
        when(documentRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(page.map(any())).thenReturn(Page.empty());

        Page<DocumentDTO> result = documentService.findAllDocumentWithPagination(0, 10);

        assertNotNull(result);
        verify(documentRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void mustReturnSuccessWhenFindDocumentById() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(documentMapper.toDTO(any())).thenReturn(new DocumentDTO());

        DocumentDTO result = documentService.findDocumentById(1L);

        assertNotNull(result);
        verify(documentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void mustReturnSuccessWhenFindDocumentByTypeAndNumber() {        
        when(documentRepository.findByDocumentTypeAndNumber(any(), anyString())).thenReturn(Optional.of(entity));
        when(documentMapper.toDTO(any())).thenReturn(new DocumentDTO());

        DocumentDTO result = documentService.findByDocumentTypeAndNumber(params);

        assertNotNull(result);
        verify(documentRepository, times(1)).findByDocumentTypeAndNumber(any(), anyString());
    }

    @Test
    public void mustReturnDocumentServiceExceptionWhenFindByDocumentTypeAndNumberWithDocumentNotFound() {
        when(documentRepository.findByDocumentTypeAndNumber(any(), anyString())).thenReturn(Optional.empty());

        assertThrows(DocumentServiceException.class, () -> documentService.findByDocumentTypeAndNumber(params));
    }

    @Test
    public void mustReturnSuccessWhenDeleteDocumentById() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(documentRepository).deleteById(anyLong());

        documentService.deleteDocumentById(1L);

        verify(documentRepository, times(1)).deleteById(anyLong());
        verify(documentRepository, times(1)).findById(anyLong());
    }

    @Test
    public void mustReturnExceptionWhenDocumentNotFound() {
        when(documentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DocumentServiceException.class, () -> {
            documentService.findDocumentById(1L);
        });

        verify(documentRepository, times(1)).findById(anyLong());       
    }


}
