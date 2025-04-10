package br.com.douglasdreer.the_barbers_forge.repositories;

import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByDocumentTypeAndNumber(DocumentType documentType, String number);

}
