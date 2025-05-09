package br.com.douglasdreer.the_barbers_forge.dtos.request;

import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamDocumentRequest {
    private String number;
    private DocumentType documentType;
}
