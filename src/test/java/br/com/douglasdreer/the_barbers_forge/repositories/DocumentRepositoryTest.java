package br.com.douglasdreer.the_barbers_forge.repositories;

import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>DocumentRepositoryTest</h1>
 * <p>Testes de integração para o repositório {@link DocumentRepository}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * Testa a busca de um documento por ID.
     */
    @Test
    void shouldFindDocumentById() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.CPF);
        document.setNumber("12345678900");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Persiste o documento no banco de dados
        Document savedDocument = entityManager.persistAndFlush(document);

        // Busca o documento pelo ID
        Optional<Document> foundDocument = documentRepository.findById(savedDocument.getId());

        // Verifica se o documento foi encontrado e se os dados estão corretos
        assertTrue(foundDocument.isPresent(), "O documento deve ser encontrado");
        assertEquals(savedDocument.getId(), foundDocument.get().getId(), "IDs devem ser iguais");
        assertEquals(DocumentType.CPF, foundDocument.get().getDocumentType(), "Tipo de documento deve ser igual");
        assertEquals("12345678900", foundDocument.get().getNumber(), "Número de documento deve ser igual");
    }

    /**
     * Testa a busca de um documento por tipo e número.
     */
    @Test
    void shouldFindDocumentByTypeAndNumber() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.RG);
        document.setNumber("987654321");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Persiste o documento no banco de dados
        entityManager.persistAndFlush(document);

        // Busca o documento pelo tipo e número
        Optional<Document> foundDocument = documentRepository.findByDocumentTypeAndNumber(DocumentType.RG, "987654321");

        // Verifica se o documento foi encontrado e se os dados estão corretos
        assertTrue(foundDocument.isPresent(), "O documento deve ser encontrado");
        assertEquals(DocumentType.RG, foundDocument.get().getDocumentType(), "Tipo de documento deve ser igual");
        assertEquals("987654321", foundDocument.get().getNumber(), "Número de documento deve ser igual");
    }

    /**
     * Testa a listagem de documentos com paginação.
     */
    @Test
    void shouldListDocumentsWithPagination() {
        // Cria alguns documentos para teste
        for (int i = 1; i <= 10; i++) {
            Document document = new Document();
            document.setDocumentType(i % 2 == 0 ? DocumentType.CPF : DocumentType.RG);
            document.setNumber("Documento" + i);
            document.setCreatedAt(LocalDateTime.now());
            document.setUpdatedAt(LocalDateTime.now());
            
            entityManager.persist(document);
        }
        entityManager.flush();

        // Busca os documentos com paginação (página 0, tamanho 5)
        Page<Document> documentsPage = documentRepository.findAll(PageRequest.of(0, 5));

        // Verifica se a paginação está correta
        assertEquals(10, documentsPage.getTotalElements(), "Total de elementos deve ser 10");
        assertEquals(2, documentsPage.getTotalPages(), "Total de páginas deve ser 2");
        assertEquals(5, documentsPage.getContent().size(), "Tamanho da página deve ser 5");
    }

    /**
     * Testa a criação de um documento.
     */
    @Test
    void shouldCreateDocument() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.CNH);
        document.setNumber("12345678901");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Salva o documento
        Document savedDocument = documentRepository.save(document);

        // Verifica se o documento foi salvo corretamente
        assertNotNull(savedDocument.getId(), "ID deve ser gerado");
        
        // Busca o documento pelo ID para confirmar que foi salvo
        Optional<Document> foundDocument = documentRepository.findById(savedDocument.getId());
        assertTrue(foundDocument.isPresent(), "O documento deve ser encontrado");
        assertEquals(DocumentType.CNH, foundDocument.get().getDocumentType(), "Tipo de documento deve ser igual");
        assertEquals("12345678901", foundDocument.get().getNumber(), "Número de documento deve ser igual");
    }

    /**
     * Testa a atualização de um documento.
     */
    @Test
    void shouldUpdateDocument() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.CPF);
        document.setNumber("AB123456");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Persiste o documento
        Document savedDocument = entityManager.persistAndFlush(document);

        // Atualiza os dados do documento
        savedDocument.setNumber("CD789012");
        savedDocument.setUpdatedAt(LocalDateTime.now());
        
        // Salva as atualizações
        documentRepository.save(savedDocument);

        // Busca o documento novamente para verificar as atualizações
        Document updatedDocument = entityManager.find(Document.class, savedDocument.getId());
        
        // Verifica se os dados foram atualizados
        assertEquals("CD789012", updatedDocument.getNumber(), "Número de documento deve ser atualizado");
    }

    /**
     * Testa a exclusão de um documento.
     */
    @Test
    void shouldDeleteDocument() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.CNPJ);
        document.setNumber("12345678901234");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Persiste o documento
        Document savedDocument = entityManager.persistAndFlush(document);
        
        // Verifica se o documento existe
        assertTrue(documentRepository.existsById(savedDocument.getId()), "Documento deve existir antes da exclusão");
        
        // Exclui o documento
        documentRepository.deleteById(savedDocument.getId());
        
        // Verifica se o documento foi excluído
        assertFalse(documentRepository.existsById(savedDocument.getId()), "Documento não deve existir após a exclusão");
    }

    /**
     * Testa que um documento não é encontrado quando tipo e número não correspondem.
     */
    @Test
    void shouldNotFindDocumentWhenTypeAndNumberDoNotMatch() {
        // Cria um documento para teste
        Document document = new Document();
        document.setDocumentType(DocumentType.CPF);
        document.setNumber("11122233344");
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());

        // Persiste o documento no banco de dados
        entityManager.persistAndFlush(document);

        // Busca um documento com tipo diferente
        Optional<Document> foundWithDifferentType = documentRepository.findByDocumentTypeAndNumber(DocumentType.RG, "11122233344");
        
        // Busca um documento com número diferente
        Optional<Document> foundWithDifferentNumber = documentRepository.findByDocumentTypeAndNumber(DocumentType.CPF, "99988877766");

        // Verifica que os documentos não foram encontrados
        assertFalse(foundWithDifferentType.isPresent(), "Documento não deve ser encontrado com tipo diferente");
        assertFalse(foundWithDifferentNumber.isPresent(), "Documento não deve ser encontrado com número diferente");
    }
} 