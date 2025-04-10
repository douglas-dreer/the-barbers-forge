package br.com.douglasdreer.the_barbers_forge.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>OpenAPI Configuration</h1>
 * <p>
 * Configuração do OpenAPI (Swagger) para documentação da API.
 * Esta classe configura a documentação da API com informações sobre o projeto,
 * contato do desenvolvedor e licença.
 * </p>
 *
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura a documentação OpenAPI com informações do projeto.
     *
     * @return objeto OpenAPI configurado
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("The Barber's Forge API")
                        .description("API de gerenciamento para barbearias - permite cadastro de clientes, agendamentos, serviços e controle financeiro")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Douglas Dreer")
                                .email("douglasdreer@gmail.com")
                                .url("https://github.com/douglas-dreer"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
} 