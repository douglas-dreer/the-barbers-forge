package br.com.douglasdreer.the_barbers_forge.controllers;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * <h1>Customer Controller</h1>
 * <p>Controlador responsável por gerenciar as requisições relacionadas aos clientes.
 * Fornece endpoints para operações CRUD (Criar, Ler, Atualizar, Deletar) de clientes.</p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@RestController
@RequestMapping("/customers")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class CustomerController {
    private final CustomerService service;

    /**
     * Construtor que recebe o serviço de cliente por injeção de dependência.
     * 
     * @param service serviço que contém a lógica de negócio para operações com clientes
     */
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    /**
     * Busca todos os clientes com paginação.
     * 
     * @param page número da página (começa em 0)
     * @param pageSize quantidade de itens por página
     * @return ResponseEntity contendo uma página de DTOs de cliente
     */
    @GetMapping(params = {"page", "pageSize"})
    @Operation(
        summary = "Listar clientes paginados",
        description = "Retorna uma lista paginada de clientes",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Clientes encontrados com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<Page<CustomerDTO>> findAllCustomersWithPagination(
            @Parameter(description = "Número da página (iniciando em 0)") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Quantidade de registros por página") @RequestParam(value = "pageSize", defaultValue = "50") int pageSize
    ) {
        return ResponseEntity.ok(service.findAllCustomersWithPagination(page, pageSize));
    }

    /**
     * Busca um cliente pelo seu ID.
     * 
     * @param id identificador único do cliente
     * @return ResponseEntity contendo o DTO do cliente encontrado
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar cliente por ID",
        description = "Retorna um cliente específico com base no ID fornecido",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Cliente encontrado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<CustomerDTO> findCustomerById(
            @Parameter(description = "ID do cliente") @PathVariable("id") long id
    ) {
        return ResponseEntity.ok(service.findCustomerById(id));
    }

    /**
     * Cria um novo cliente.
     * 
     * @param customer dados do cliente a ser criado
     * @return ResponseEntity contendo o DTO do cliente criado
     */
    @PostMapping
    @Operation(
        summary = "Criar cliente",
        description = "Cria um novo cliente com os dados fornecidos",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Cliente criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<CustomerDTO> createCustomer(
            @Parameter(description = "Dados do cliente") @RequestBody CreateCustomerRequest customer
    ) {
        CustomerDTO savedData = service.createCustomer(customer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedData.getId()).toUri();
        return ResponseEntity.created(location).body(savedData);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param id identificador único do cliente
     * @param customer dados atualizados do cliente
     * @return ResponseEntity contendo o DTO do cliente atualizado
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente com base no ID fornecido",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Cliente atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "ID do cliente") @PathVariable("id") long id, 
            @Parameter(description = "Dados atualizados do cliente") @RequestBody CreateCustomerRequest customer
    ) {
        return ResponseEntity.ok(service.updateCustomer(id, customer));
    }

    /**
     * Remove um cliente pelo seu ID.
     * 
     * @param id identificador único do cliente a ser removido
     * @return ResponseEntity com mensagem de sucesso
     */
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir cliente",
        description = "Remove um cliente específico com base no ID fornecido",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
    )
    public ResponseEntity<String> deleteCustomerById(
            @Parameter(description = "ID do cliente") @PathVariable("id") long id
    ) {
        service.deleteCustomerById(id);
        return ResponseEntity.ok("Cliente removido com sucesso");
    }
}
