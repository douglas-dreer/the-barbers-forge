package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import org.springframework.data.domain.Page;

/**
 * <h1>Customer Service</h1>
 * <p>
 * Interface que define os serviços disponíveis para gerenciamento de clientes.
 * Esta interface define os contratos para busca, criação, atualização e remoção de clientes.
 * </p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
public interface CustomerService {
    
    /**
     * Busca todos os clientes com paginação.
     * 
     * @param page número da página (começa em 0)
     * @param pageSize quantidade de itens por página
     * @return página de DTOs de clientes
     */
    Page<CustomerDTO> findAllCustomersWithPagination(int page, int pageSize);
    
    /**
     * Busca um cliente pelo seu ID.
     * 
     * @param id identificador único do cliente
     * @return DTO do cliente encontrado
     */
    CustomerDTO findCustomerById(long id);
    
    /**
     * Cria um novo cliente.
     * 
     * @param customer dados do cliente a ser criado
     * @return DTO do cliente criado
     */
    CustomerDTO createCustomer(CreateCustomerRequest customer);
    
    /**
     * Atualiza os dados de um cliente existente.
     * 
     * @param id identificador único do cliente
     * @param customer dados atualizados do cliente
     * @return DTO do cliente atualizado
     */
    CustomerDTO updateCustomer(long id, CreateCustomerRequest customer);
    
    /**
     * Remove um cliente pelo seu ID.
     * 
     * @param id identificador único do cliente a ser removido
     */
    void deleteCustomerById(long id);
}
