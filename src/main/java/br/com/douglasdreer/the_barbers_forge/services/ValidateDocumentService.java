package br.com.douglasdreer.the_barbers_forge.services;

import br.com.douglasdreer.the_barbers_forge.exceptions.ValidateDocumentServiceException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * <h1>ValidateDocumentService</h1>
 * <p>Service class for validating document formats, specifically CPF, in the Casa da Navalha application.</p>
 *
 * <p>This service ensures that CPF documents are correctly formatted and non-null before being processed.</p>
 *
 * <p>The CPF format validation follows the Brazilian standard of exactly 11 numeric digits.</p>
 *
 * <p>It throws exceptions when validation rules are not met to maintain data integrity.</p>
 *
 * @author Douglas Dreer
 * @since 0.0.1
 */
@Service
public class ValidateDocumentService {

    /**
     * <h2>validateDocumentCPF</h2>
     * <p>Validates the format of a given CPF document.</p>
     *
     * <p>This method checks if the CPF is not null or empty and contains exactly 11 numeric digits.</p>
     *
     * @param cpf the CPF to be validated, as a numeric string.
     *            Must not be null or empty.
     *
     * @throws ValidateDocumentServiceException if the CPF is null, empty, or does not
     *                                          contain exactly 11 digits.
     * @since 0.0.1
     */
    public void validateDocumentCPF(String cpf) throws ValidateDocumentServiceException {
        if (cpf == null || cpf.isEmpty()) {
            throw new ValidateDocumentServiceException("CPF must not be null or empty.");
        }

        if (!Pattern.matches("\\d{11}", cpf)) {
            throw new ValidateDocumentServiceException("Invalid CPF format. CPF must contain exactly 11 digits.");
        }
    }
}
