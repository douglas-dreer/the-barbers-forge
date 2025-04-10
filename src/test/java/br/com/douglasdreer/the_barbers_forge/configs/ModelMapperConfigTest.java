package br.com.douglasdreer.the_barbers_forge.configs;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * <h1>ModelMapperConfigTest</h1>
 * <p>Testes para a classe de configuração {@link ModelMapperConfig}.</p>
 * 
 * @author Douglas Dreer
 * @since 0.0.2
 */
@SpringBootTest
public class ModelMapperConfigTest {

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Testa se o bean ModelMapper é criado e injetado corretamente.
     */
    @Test
    void modelMapperShouldBeCreated() {
        assertNotNull(modelMapper, "ModelMapper deve ser criado e injetado");
    }
} 