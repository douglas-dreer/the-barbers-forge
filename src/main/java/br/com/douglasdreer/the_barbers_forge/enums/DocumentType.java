package br.com.douglasdreer.the_barbers_forge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>Document Type</h1>
 * <p>Enumeração que representa os tipos de documentos aceitos pelo sistema.</p>
 * 
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@AllArgsConstructor
@Getter
public enum DocumentType {
    /**
     * Cadastro de Pessoa Física - Documento de identificação fiscal brasileiro.
     */
    CPF,
    
    /**
     * Registro Geral - Documento de identidade emitido pelas secretarias de segurança pública.
     */
    RG,
    
    /**
     * Carteira Nacional de Habilitação - Documento que habilita o cidadão a conduzir veículos.
     */
    CNH,
    
    /**
     * Cadastro Nacional de Pessoa Jurídica - Documento fiscal para empresas.
     */
    CNPJ
}
