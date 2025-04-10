package br.com.douglasdreer.the_barbers_forge.dtos.mappers;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper extends BaseMapper {
    /**
     * Constructor for initializing the service with the required dependencies.
     *
     * @param modelMapper the ModelMapper instance for object-to-object conversion
     */
    public DocumentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    public <T> DocumentDTO toDTO(T inputData) {
        return convertTo(inputData, DocumentDTO.class);
    }

    public <T> Document toEntity(T inputData) {
        return convertTo(inputData, Document.class);
    }
}
