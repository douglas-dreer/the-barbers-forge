package br.com.douglasdreer.the_barbers_forge.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * <h1>Create Customer Request</h1>
 * <p>
 * DTO para receber dados de requisição para criação ou atualização de clientes.
 * Esta classe contém todos os campos necessários para as operações de cadastro e atualização
 * de clientes no sistema.
 * </p>
 *
 * @author Douglas Dreer
 * @version 1.0
 * @since 2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para requisição de criação/atualização de cliente")
public class CreateCustomerRequest {

    /**
     * Primeiro nome do cliente. Campo obrigatório.
     */
    @NotBlank(message = "O primeiro nome é obrigatório")
    @Schema(description = "Primeiro nome do cliente", example = "João", required = true)
    private String firstName;

    /**
     * Sobrenome do cliente. Campo obrigatório.
     */
    @NotBlank(message = "O sobrenome é obrigatório")
    @Schema(description = "Sobrenome do cliente", example = "Silva", required = true)
    private String lastName;

    /**
     * Lista de documentos associados ao cliente.
     */
    @Schema(description = "Lista de IDs de documentos associados ao cliente")
    private List<Long> documentIds;

    /**
     * Endereço do cliente. Campo opcional.
     */
    @Schema(description = "Endereço completo do cliente", example = "Rua das Flores, 123")
    private String address;

    /**
     * Telefone do cliente. Campo opcional.
     */
    @Schema(description = "Número de telefone do cliente", example = "(11) 98765-4321")
    private String phone;

    /**
     * Data de nascimento do cliente. Campo opcional, mas deve ser no passado ou presente.
     */
    @PastOrPresent(message = "A data de nascimento deve ser no passado ou presente")
    @Schema(description = "Data de nascimento do cliente", example = "1990-01-15")
    private LocalDate birthDate;
} 