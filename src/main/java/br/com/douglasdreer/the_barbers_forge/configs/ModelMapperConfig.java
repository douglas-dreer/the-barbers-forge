package br.com.douglasdreer.the_barbers_forge.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h1>ModelMapperConfig</h1>
 * <p>Configuration class to define the {@link ModelMapper} bean for the application context.
 * This bean will be used for object mapping between entities and DTOs (Data Transfer Objects).</p>
 *
 * <p>ModelMapper is a library that simplifies the process of copying values from one object to another,
 * typically between entity objects and DTOs in a Spring-based application.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Creates and returns a new {@link ModelMapper} instance.
     * This method is annotated with {@code @Bean}, allowing it to be used as a Spring bean throughout the application.
     *
     * @return a new instance of {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
