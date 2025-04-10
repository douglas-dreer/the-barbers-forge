package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.mappers.DocumentMapper;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.MessageErroType;
import br.com.douglasdreer.the_barbers_forge.exceptions.DocumentServiceException;
import br.com.douglasdreer.the_barbers_forge.repositories.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository repository;
    private final DocumentMapper mapper;

    public DocumentServiceImpl(DocumentRepository repository, DocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DocumentDTO createDocument(CreateDocumentRequest document) {
        try {
            validateDocumentForCreate(document);
            Document documentSaved = repository.save(mapper.toEntity(document));
            return mapper.toDTO(documentSaved);
        } catch (Exception e) {
            throw new DocumentServiceException(e.getCause().getLocalizedMessage());
        }
    }

    @Override
    public List<DocumentDTO> listDocument() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public Page<DocumentDTO> findAllDocumentWithPagination(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Document> result = repository.findAll(pageRequest);
        return result.map(mapper::toDTO);
    }

    @Override
    public DocumentDTO findDocumentById(long id) {
        Document result = repository.findById(id).orElseThrow(() -> new DocumentServiceException(MessageErroType.NOT_FOUND.getMessage()));
        return mapper.toDTO(result);
    }

    @Override
    public DocumentDTO findByDocumentTypeAndNumber(ParamDocumentRequest paramsDocument) {
        Document result = repository
            .findByDocumentTypeAndNumber(paramsDocument.getDocumentType(), paramsDocument.getNumber())
            .orElseThrow(() -> new DocumentServiceException(MessageErroType.NOT_FOUND.getMessage()));
        return mapper.toDTO(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDocumentById(long id) {
        validateDocumentForDelete(id);
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    private void validateDocumentForCreate(CreateDocumentRequest document) {
        Document entity = mapper.toEntity(document);
        if (checkIfAlrightSave(entity)) {
            throw new DocumentServiceException("Document alright saved.");
        }
    }

    private void validateDocumentForDelete(long id) {
        repository.findById(id).orElseThrow(() -> new DocumentServiceException(MessageErroType.NOT_FOUND.getMessage()));
    }

    private boolean checkIfAlrightSave(Document document) {
        Optional<Document> documentOptional = repository.findByDocumentTypeAndNumber(document.getDocumentType(), document.getNumber());
        return documentOptional.isPresent();
    }
}
