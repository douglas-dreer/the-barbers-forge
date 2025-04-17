package br.com.douglasdreer.the_barbers_forge.dtos.mappers;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.douglasdreer.the_barbers_forge.dtos.CustomerDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateCustomerRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Customer;
import br.com.douglasdreer.the_barbers_forge.exceptions.ConverterException;

public class CustomerMapper extends BaseMapper {

    /**
     * Construtor que recebe o ModelMapper necessário para as conversões.
     *
     * @param modelMapper instância do ModelMapper para conversão entre objetos
     */
    public CustomerMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    /**
     * Converte uma entidade Customer para CustomerDTO.
     *
     * @param customer a entidade a ser convertida
     * @return o DTO convertido
     */
    public CustomerDTO toDTO(Customer customer) {
        return convertTo(customer, CustomerDTO.class);
    }

    /**
     * Converte um CustomerDTO para entidade Customer.
     *
     * @param customerDTO o DTO a ser convertido
     * @return a entidade convertida
     */
    public Customer toEntity(CustomerDTO customerDTO) {
        return convertTo(customerDTO, Customer.class);
    }

    /**
     * Converte um CreateCustomerRequest para entidade Customer.
     *
     * @param request o request a ser convertido
     * @return a entidade convertida
     */
    public Customer toEntity(CreateCustomerRequest request) {
        return convertTo(request, Customer.class);
    }

    /**
     * Converte um objeto para sua representação em JSON.
     *
     * @param object o objeto a ser convertido
     * @return a string JSON representando o objeto
     * @throws ConverterException se houver erro na conversão
     */
    public String toJson(Object object) {
        try {
            return super.toJSON(object);
        } catch (JsonProcessingException e) {
            throw new ConverterException("Erro ao converter objeto para JSON", e);
        }
    }
}
