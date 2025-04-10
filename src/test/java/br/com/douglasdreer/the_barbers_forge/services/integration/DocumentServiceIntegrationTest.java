package br.com.douglasdreer.the_barbers_forge.services.integration;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import br.com.douglasdreer.the_barbers_forge.exceptions.DocumentServiceException;
import br.com.douglasdreer.the_barbers_forge.repositories.DocumentRepository;
import br.com.douglasdreer.the_barbers_forge.services.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>Document Service Integration Test</h1>
 * <p>Classe de testes de integração para o serviço de documentos.
 * Utiliza um banco de dados H2 em memória para realizar testes de integração reais.</p>
 * 
 * <p>Estes testes verificam se o serviço de documentos está interagindo corretamente
 * com o banco de dados e realizando as operações CRUD conforme esperado.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DocumentServiceIntegrationTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    private Document testDocument;
    private CreateDocumentRequest createRequest;
    private ParamDocumentRequest searchParams;

    @BeforeEach
    public void setUp() {
        // Limpa o banco antes de cada teste
        documentRepository.deleteAll();

        // Prepara os dados de teste
        createRequest = new CreateDocumentRequest(
            DocumentType.CPF,
            "12345678900"
        );

        searchParams = new ParamDocumentRequest(
            "12345678900",
            DocumentType.CPF
        );
    }

    /**
     * Testa a criação de um documento no banco de dados.
     */
    @Test
    public void mustReturnSuccessWhenCreateDocument() {
        // Executa a criação do documento
        DocumentDTO result = documentService.createDocument(createRequest);

        // Verifica se o documento foi criado com sucesso
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(DocumentType.CPF, result.getDocumentType());
        assertEquals("12345678900", result.getNumber());

        // Verifica se o documento existe no banco de dados
        Optional<Document> savedDocument = documentRepository.findById(result.getId());
        assertTrue(savedDocument.isPresent());
        assertEquals(DocumentType.CPF, savedDocument.get().getDocumentType());
        assertEquals("12345678900", savedDocument.get().getNumber());
    }

    /**
     * Testa a listagem de todos os documentos.
     */
    @Test
    public void mustReturnSuccessWhenListDocuments() {
        // Cria documento para teste
        documentService.createDocument(createRequest);

        // Cria outro documento com tipo diferente
        documentService.createDocument(new CreateDocumentRequest(DocumentType.RG, "987654321"));

        // Lista todos os documentos
        List<DocumentDTO> documents = documentService.listDocument();

        // Verifica se a lista contém 2 documentos
        assertNotNull(documents);
        assertEquals(2, documents.size());
    }

    /**
     * Testa a busca de documentos com paginação.
     */
    @Test
    public void mustReturnSuccessWhenFindDocumentsWithPagination() {
        // Cria múltiplos documentos para teste
        for (int i = 0; i < 5; i++) {
            documentService.createDocument(new CreateDocumentRequest(
                    DocumentType.CPF, 
                    "1234567890" + i
            ));
        }

        // Busca a primeira página com 3 itens
        Page<DocumentDTO> result = documentService.findAllDocumentWithPagination(0, 3);

        // Verifica a paginação
        assertNotNull(result);
        assertEquals(3, result.getContent().size());
        assertEquals(5, result.getTotalElements());
        assertEquals(2, result.getTotalPages());
    }

    /**
     * Testa a busca de um documento pelo ID.
     */
    @Test
    public void mustReturnSuccessWhenFindDocumentById() {
        // Cria um documento para teste
        DocumentDTO created = documentService.createDocument(createRequest);

        // Busca o documento pelo ID
        DocumentDTO found = documentService.findDocumentById(created.getId());

        // Verifica se o documento foi encontrado corretamente
        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
        assertEquals(created.getDocumentType(), found.getDocumentType());
        assertEquals(created.getNumber(), found.getNumber());
    }

    /**
     * Testa a busca de um documento pelo tipo e número.
     */
    @Test
    public void mustReturnSuccessWhenFindDocumentByTypeAndNumber() {
        // Cria um documento para teste
        documentService.createDocument(createRequest);

        // Busca o documento pelo tipo e número
        DocumentDTO found = documentService.findByDocumentTypeAndNumber(searchParams);

        // Verifica se o documento foi encontrado corretamente
        assertNotNull(found);
        assertEquals(DocumentType.CPF, found.getDocumentType());
        assertEquals("12345678900", found.getNumber());
    }

    /**
     * Testa a exclusão de um documento pelo ID.
     */
    @Test
    public void mustReturnSuccessWhenDeleteDocumentById() {
        // Cria um documento para teste
        DocumentDTO created = documentService.createDocument(createRequest);

        // Verifica se o documento foi criado
        assertTrue(documentRepository.findById(created.getId()).isPresent());

        // Exclui o documento
        documentService.deleteDocumentById(created.getId());

        // Verifica se o documento foi excluído
        assertFalse(documentRepository.findById(created.getId()).isPresent());
    }

    /**
     * Testa a exceção lançada quando um documento não é encontrado.
     */
    @Test
    public void mustReturnDocumentServiceExceptionWhenFindDocumentWithInvalidId() {
        // Tenta buscar um documento que não existe
        assertThrows(DocumentServiceException.class, () -> {
            documentService.findDocumentById(9999L);
        });
    }
}
