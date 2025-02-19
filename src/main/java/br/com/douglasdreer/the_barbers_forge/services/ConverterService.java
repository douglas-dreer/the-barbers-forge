package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.exceptions.ConverterServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * <h1>ConverterService</h1>
 * <p>Service class responsible for handling object conversion operations.
 * It includes functionalities for converting objects between different types,
 * mapping lists, and serializing/deserializing objects to/from JSON.</p>
 *
 * <p>This service uses {@link ModelMapper} for object-to-object conversion
 * and {@link ObjectMapper} for JSON serialization and deserialization.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Service
public class ConverterService {

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    /**
     * Constructor for initializing the service with the required dependencies.
     *
     * @param modelMapper the ModelMapper instance for object-to-object conversion
     */
    public ConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = initializeObjectMapper();
    }

    /**
     * Initializes and configures the {@link ObjectMapper} for JSON operations.
     * Enables pretty printing and registers the JavaTimeModule for handling Java 8 date/time types.
     *
     * @return a configured ObjectMapper instance
     */
    private ObjectMapper initializeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    /**
     * Converts an object of one type to another using {@link ModelMapper}.
     *
     * @param source the object to be converted
     * @param targetClass the class type to convert to
     * @param <D> the target type
     * @return the converted object of type {@link D}
     */
    public <D> D convertTo(Object source, Class<D> targetClass) {
        try {
            return modelMapper.map(source, targetClass);
        } catch (Exception e) {
            throw new ConverterServiceException(e.getLocalizedMessage());
        }
    }

    /**
     * Maps a list of objects from one type to another.
     *
     * @param sourceList the list of objects to be mapped
     * @param targetClass the class type to convert each element to
     * @param <S> the source type
     * @param <T> the target type
     * @return a list of objects of type {@link T}
     */
    public <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .toList();
    }

    /**
     * Converts an object to its JSON string representation.
     *
     * @param object the object to be converted to JSON
     * @return the JSON string representation of the object
     * @throws JsonProcessingException if the object cannot be converted to JSON
     */
    public String toJSON(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Converts a JSON string to an object of the specified type.
     *
     * @param jsonData the JSON string to be converted
     * @param targetClass the class type to convert the JSON string to
     * @param <D> the target type
     * @return the object converted from the JSON string
     * @throws IOException if there is an issue during the deserialization
     */
    public <D> D toObject(String jsonData, Class<D> targetClass) throws IOException {
        return objectMapper.readValue(jsonData, targetClass);
    }
}
