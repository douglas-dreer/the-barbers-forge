package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import org.springframework.data.domain.Page;


import java.util.List;

/**
 * Interface que define os serviços relacionados a documentos.
 */
public interface DocumentService {
    /**
     * Cria um novo documento.
     *
     * @param document dados do documento a ser criado
     * @return DTO do documento criado
     */
    DocumentDTO createDocument(CreateDocumentRequest document);

    /**
     * Remove um documento pelo seu ID.
     *
     * @param id identificador único do documento a ser removido
     */
    void deleteDocumentById(long id);

    /**
     * Lista todos os documentos.
     *
     * @return lista de DTOs de documentos
     */
    List<DocumentDTO> listDocument();

    /**
     * Busca todos os documentos com paginação.
     *
     * @param page número da página (começa em 0)
     * @param pageSize quantidade de itens por página
     * @return página de DTOs de documentos
     */
    Page<DocumentDTO> findAllDocumentWithPagination(int page, int pageSize);

    /**
     * Busca um documento pelo seu ID.
     *
     * @param id identificador único do documento
     * @return DTO do documento encontrado
     */
    DocumentDTO findDocumentById(long id);

    /**
     * Busca um documento pelo tipo e número.
     *
     * @param paramsDocument parâmetros de busca do documento
     * @return DTO do documento encontrado
     */
    DocumentDTO findByDocumentTypeAndNumber(ParamDocumentRequest paramsDocument);
}
