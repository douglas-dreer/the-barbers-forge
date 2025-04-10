package br.com.douglasdreer.the_barbers_forge.dtos;

import java.time.LocalDateTime;
import java.util.List;

import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentDTO {
    private Long id;
    private DocumentType documentType;
    private String number;
    private List<CustomerDTO> customers;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
