package br.com.douglasdreer.the_barbers_forge.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>OpenApiConfigTest</h1>
 * <p>Testes para a classe de configuração {@link OpenApiConfig}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@SpringBootTest
public class OpenApiConfigTest {

    @Autowired
    private OpenAPI openAPI;

    /**
     * Testa se o bean OpenAPI é criado e injetado corretamente com todas as propriedades.
     */
    @Test
    void openAPIShouldBeCreatedWithCorrectInfo() {
        // Verifica se o bean foi criado e injetado
        assertNotNull(openAPI, "OpenAPI deve ser criado e injetado");
        
        // Verifica as propriedades de Info
        Info info = openAPI.getInfo();
        assertNotNull(info, "Info deve ser configurado");
        assertEquals("The Barber's Forge API", info.getTitle(), "Título deve estar correto");
        assertEquals("v1.0", info.getVersion(), "Versão deve estar correta");
        assertTrue(info.getDescription().contains("API de gerenciamento para barbearias"), "Descrição deve estar correta");
        
        // Verifica as propriedades de Contact
        Contact contact = info.getContact();
        assertNotNull(contact, "Contact deve ser configurado");
        assertEquals("Douglas Dreer", contact.getName(), "Nome do contato deve estar correto");
        assertEquals("douglasdreer@gmail.com", contact.getEmail(), "Email do contato deve estar correto");
        assertEquals("https://github.com/douglas-dreer", contact.getUrl(), "URL do contato deve estar correta");
        
        // Verifica as propriedades de License
        License license = info.getLicense();
        assertNotNull(license, "License deve ser configurada");
        assertEquals("Apache 2.0", license.getName(), "Nome da licença deve estar correto");
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0", license.getUrl(), "URL da licença deve estar correta");
    }
} 