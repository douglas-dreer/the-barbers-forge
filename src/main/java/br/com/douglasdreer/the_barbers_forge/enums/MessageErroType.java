package br.com.douglasdreer.the_barbers_forge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>Message Error Type</h1>
 * <p>Enumeração que representa os tipos de mensagens de erro que podem ocorrer no sistema.
 * Cada tipo de erro possui uma mensagem associada que é exibida ao usuário.</p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@AllArgsConstructor
@Getter
public enum MessageErroType {
    /**
     * Erro que ocorre quando um recurso solicitado não é encontrado.
     * Mensagem associada: "There is no result for this request"
     */
    NOT_FOUND("There is no result for this request");
    
    /**
     * A mensagem de erro associada ao tipo.
     */
    final String message;
}
