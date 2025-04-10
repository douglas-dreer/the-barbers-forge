package br.com.douglasdreer.the_barbers_forge.controllers;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.services.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * <h1>Document Controller</h1>
 * <p>Controlador responsável por gerenciar as requisições relacionadas aos documentos.
 * Fornece endpoints para operações CRUD (Criar, Ler, Atualizar, Deletar) de documentos.</p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@RestController
@RequestMapping("/documents")
@Tag(name = "Documentos", description = "API para gerenciamento de documentos")
@Log4j2
public class DocumentController {
    private final DocumentService service;

    /**
     * Construtor que recebe o serviço de documento por injeção de dependência.
     * 
     * @param service serviço que contém a lógica de negócio para operações com documentos
     */
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    /**
     * Busca todos os documentos com paginação.
     * 
     * @param page número da página (começa em 0)
     * @param pageSize quantidade de itens por página
     * @return ResponseEntity contendo uma página de DTOs de documento
     */
    @GetMapping(params = {"page", "pageSize"})
    @Operation(
        summary = "Listar documentos paginados",
        description = "Retorna uma lista paginada de documentos",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Documentos encontrados com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<Page<DocumentDTO>> findAllDocumentWithPagination(
            @Parameter(description = "Número da página (iniciando em 0)") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Quantidade de registros por página") @RequestParam(value = "pageSize", defaultValue = "50") int pageSize
    ) {
        return ResponseEntity.ok(service.findAllDocumentWithPagination(page, pageSize));
    }

    /**
     * Busca um documento pelo seu ID.
     * 
     * @param id identificador único do documento
     * @return ResponseEntity contendo o DTO do documento encontrado
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar documento por ID",
        description = "Retorna um documento específico com base no ID fornecido",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Documento encontrado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<DocumentDTO> findDocumentById(
            @Parameter(description = "ID do documento") @PathVariable("id") long id
    ) {
        return ResponseEntity.ok(service.findDocumentById(id));
    }

    /**
     * Busca um documento pelo tipo e número.
     * 
     * @param paramsDocumentRequest parâmetros de busca do documento
     * @return ResponseEntity contendo o DTO do documento encontrado
     */
    @GetMapping(value = "/search")
    @Operation(
        summary = "Buscar documento por tipo e número",
        description = "Retorna um documento específico com base no tipo e número fornecidos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Documento encontrado com sucesso", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<DocumentDTO> findDocumentByParameters(
            @Parameter(description ="Objeto para pesquisa") @RequestBody ParamDocumentRequest paramsDocumentRequest
    ) {
        log.info("Buscando documento por tipo e número: {}", paramsDocumentRequest);
        return ResponseEntity.ok(service.findByDocumentTypeAndNumber(paramsDocumentRequest));
    }

    /**
     * Cria um novo documento.
     * 
     * @param document dados do documento a ser criado
     * @return ResponseEntity contendo o DTO do documento criado
     */
    @PostMapping
    @Operation(
        summary = "Criar documento",
        description = "Cria um novo documento com os dados fornecidos",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Documento criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<DocumentDTO> createDocument(
            @Parameter(description = "Dados do documento") @RequestBody CreateDocumentRequest document
    ) {
        DocumentDTO savedData = service.createDocument(document);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedData.getId()).toUri();
        return ResponseEntity.created(location).body(savedData);
    }

    /**
     * Remove um documento pelo seu ID.
     * 
     * @param id identificador único do documento a ser removido
     * @return ResponseEntity com mensagem de sucesso
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir documento",
        description = "Remove um documento específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Documento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<String> deleteByIdDocument(
            @Parameter(description = "ID do documento") @PathVariable("id") long id
    ) {
        service.deleteDocumentById(id);
        return ResponseEntity.ok("Documento removido com sucesso");
    }
}
