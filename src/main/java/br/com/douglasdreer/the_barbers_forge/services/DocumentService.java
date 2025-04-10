package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import org.springframework.data.domain.Page;


import java.util.List;

public interface DocumentService {
    DocumentDTO createDocument(CreateDocumentRequest document);
    void deleteDocumentById(long id);
    List<DocumentDTO> listDocument();
    Page<DocumentDTO> findAllDocumentWithPagination(int page, int pageSize);
    DocumentDTO findDocumentById(long id);
    DocumentDTO findByDocumentTypeAndNumber(ParamDocumentRequest paramsDocument);
}
