package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.exceptions.ValidateDocumentServiceException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * <h1>ValidateDocumentService</h1>
 * <p>Service class for validating document formats, specifically cpf, in the Casa da Navalha application.</p>
 *
 * <p>This service ensures that cpf documents are correctly formatted and non-null before being processed.</p>
 *
 * <p>The cpf format validation follows the Brazilian standard of exactly 11 numeric digits.</p>
 *
 * <p>It throws exceptions when validation rules are not met to maintain data integrity.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Service
public class ValidateDocumentService {

    /**
     * <h2>validateDocumentcpf</h2>
     * <p>Validates the format of a given cpf document.</p>
     *
     * <p>This method checks if the cpf is not null or empty and contains exactly 11 numeric digits.</p>
     *
     * @param cpf the cpf to be validated, as a numeric string.
     *            Must not be null or empty.
     *
     * @throws ValidateDocumentServiceException if the cpf is null, empty, or does not
     *                                          contain exactly 11 digits.
     * @since 0.0.1
     */
    public void validateDocumentcpf(String cpf) throws ValidateDocumentServiceException {
        if (cpf == null || cpf.isEmpty()) {
            throw new ValidateDocumentServiceException("cpf must not be null or empty.");
        }

        if (!Pattern.matches("\\d{11}", cpf)) {
            throw new ValidateDocumentServiceException("Invalid cpf format. cpf must contain exactly 11 digits.");
        }
    }
}
