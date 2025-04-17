package br.com.douglasdreer.the_barbers_forge.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ConverterException extends RuntimeException {
    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(String message, JsonProcessingException e) {
    }
}
