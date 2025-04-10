package br.com.douglasdreer.the_barbers_forge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * <h1>The Barbers Forge Application</h1>
 * <p>
 * Aplicação principal que inicia o sistema de gerenciamento para barbearia.
 * Este é o ponto de entrada da aplicação Spring Boot.
 * </p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "The Barber's Forge API",
		version = "1.0",
		description = "API para gerenciamento de barbearias"
	)
)
public class TheBarbersForgeApplication {

	/**
	 * Método principal que inicia a aplicação Spring Boot.
	 * 
	 * @param args argumentos de linha de comando
	 */
	public static void main(String[] args) {
		SpringApplication.run(TheBarbersForgeApplication.class, args);
	}

}
