package br.com.douglasdreer.the_barbers_forge.repositories;

import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>CustomerRepositoryTest</h1>
 * <p>Testes de integração para o repositório {@link CustomerRepository}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    /**
     * Testa a busca de um cliente por ID.
     */
    @Test
    void shouldFindCustomerById() {
        // Cria um cliente para teste
        Customer customer = new Customer();
        customer.setFirstName("João");
        customer.setLastName("Silva");
        customer.setAddress("Rua das Flores, 123");
        customer.setPhone("(11) 98765-4321");
        customer.setBirthDate(LocalDate.of(1990, 1, 15));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        // Persiste o cliente no banco de dados
        Customer savedCustomer = entityManager.persistAndFlush(customer);

        // Busca o cliente pelo ID
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Verifica se o cliente foi encontrado e se os dados estão corretos
        assertTrue(foundCustomer.isPresent(), "O cliente deve ser encontrado");
        assertEquals(savedCustomer.getId(), foundCustomer.get().getId(), "IDs devem ser iguais");
        assertEquals("João", foundCustomer.get().getFirstName(), "Primeiro nome deve ser igual");
        assertEquals("Silva", foundCustomer.get().getLastName(), "Sobrenome deve ser igual");
    }

    /**
     * Testa a listagem de clientes com paginação.
     */
    @Test
    void shouldListCustomersWithPagination() {
        // Cria alguns clientes para teste
        for (int i = 1; i <= 10; i++) {
            Customer customer = new Customer();
            customer.setFirstName("Nome" + i);
            customer.setLastName("Sobrenome" + i);
            customer.setAddress("Endereço " + i);
            customer.setPhone("Telefone " + i);
            customer.setBirthDate(LocalDate.of(1990, 1, i));
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());
            
            entityManager.persist(customer);
        }
        entityManager.flush();

        // Busca os clientes com paginação (página 0, tamanho 5)
        Page<Customer> customersPage = customerRepository.findAll(PageRequest.of(0, 5));

        // Verifica se a paginação está correta
        assertEquals(10, customersPage.getTotalElements(), "Total de elementos deve ser 10");
        assertEquals(2, customersPage.getTotalPages(), "Total de páginas deve ser 2");
        assertEquals(5, customersPage.getContent().size(), "Tamanho da página deve ser 5");
    }

    /**
     * Testa a criação de um cliente.
     */
    @Test
    void shouldCreateCustomer() {
        // Cria um cliente para teste
        Customer customer = new Customer();
        customer.setFirstName("Maria");
        customer.setLastName("Oliveira");
        customer.setAddress("Avenida Central, 456");
        customer.setPhone("(21) 98765-4321");
        customer.setBirthDate(LocalDate.of(1995, 5, 15));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        // Salva o cliente
        Customer savedCustomer = customerRepository.save(customer);

        // Verifica se o cliente foi salvo corretamente
        assertNotNull(savedCustomer.getId(), "ID deve ser gerado");
        
        // Busca o cliente pelo ID para confirmar que foi salvo
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
        assertTrue(foundCustomer.isPresent(), "O cliente deve ser encontrado");
        assertEquals("Maria", foundCustomer.get().getFirstName(), "Primeiro nome deve ser igual");
        assertEquals("Oliveira", foundCustomer.get().getLastName(), "Sobrenome deve ser igual");
    }

    /**
     * Testa a atualização de um cliente.
     */
    @Test
    void shouldUpdateCustomer() {
        // Cria um cliente para teste
        Customer customer = new Customer();
        customer.setFirstName("Carlos");
        customer.setLastName("Santos");
        customer.setAddress("Rua Principal, 789");
        customer.setPhone("(31) 98765-4321");
        customer.setBirthDate(LocalDate.of(1980, 10, 20));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        // Persiste o cliente
        Customer savedCustomer = entityManager.persistAndFlush(customer);

        // Atualiza os dados do cliente
        savedCustomer.setFirstName("Carlos Atualizado");
        savedCustomer.setLastName("Santos Atualizado");
        savedCustomer.setUpdatedAt(LocalDateTime.now());
        
        // Salva as atualizações
        customerRepository.save(savedCustomer);

        // Busca o cliente novamente para verificar as atualizações
        Customer updatedCustomer = entityManager.find(Customer.class, savedCustomer.getId());
        
        // Verifica se os dados foram atualizados
        assertEquals("Carlos Atualizado", updatedCustomer.getFirstName(), "Primeiro nome deve ser atualizado");
        assertEquals("Santos Atualizado", updatedCustomer.getLastName(), "Sobrenome deve ser atualizado");
    }

    /**
     * Testa a exclusão de um cliente.
     */
    @Test
    void shouldDeleteCustomer() {
        // Cria um cliente para teste
        Customer customer = new Customer();
        customer.setFirstName("Ana");
        customer.setLastName("Lima");
        customer.setAddress("Alameda das Árvores, 321");
        customer.setPhone("(41) 98765-4321");
        customer.setBirthDate(LocalDate.of(1985, 3, 10));
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        // Persiste o cliente
        Customer savedCustomer = entityManager.persistAndFlush(customer);
        
        // Verifica se o cliente existe
        assertTrue(customerRepository.existsById(savedCustomer.getId()), "Cliente deve existir antes da exclusão");
        
        // Exclui o cliente
        customerRepository.deleteById(savedCustomer.getId());
        
        // Verifica se o cliente foi excluído
        assertFalse(customerRepository.existsById(savedCustomer.getId()), "Cliente não deve existir após a exclusão");
    }
} 