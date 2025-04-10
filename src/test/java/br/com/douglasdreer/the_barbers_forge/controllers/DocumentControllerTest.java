package br.com.douglasdreer.the_barbers_forge.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.douglasdreer.the_barbers_forge.dtos.DocumentDTO;
import br.com.douglasdreer.the_barbers_forge.dtos.request.CreateDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.dtos.request.ParamDocumentRequest;
import br.com.douglasdreer.the_barbers_forge.entities.Document;
import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import br.com.douglasdreer.the_barbers_forge.services.DocumentService;

@WebMvcTest(DocumentController.class)
@ExtendWith(MockitoExtension.class)
public class DocumentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentService documentService;

    @Autowired
    private ObjectMapper objectMapper;
    
    private static final String BASE_URL = "/documents";
    private static final String DOCUMENT_NUMBER = "1234567890";
    private static final Long DOCUMENT_ID = 1L;

    private Document entity;
    private DocumentDTO dto;
    private CreateDocumentRequest createDocumentRequest;
    private ParamDocumentRequest params;

    @BeforeEach
    void setUp() {
        createDocumentRequest = new CreateDocumentRequest(
            DocumentType.CPF,
            DOCUMENT_NUMBER
        );

        params = new ParamDocumentRequest(
            DOCUMENT_NUMBER,
            DocumentType.CPF
        );

        entity = new Document();
        entity.setId(DOCUMENT_ID);
        entity.setDocumentType(DocumentType.CPF);
        entity.setNumber(DOCUMENT_NUMBER);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        dto = new DocumentDTO();
        dto.setId(DOCUMENT_ID);
        dto.setDocumentType(DocumentType.CPF);
        dto.setNumber(DOCUMENT_NUMBER);
    }

    @Test
    void mustReturnSuccessWhenCreateDocument() throws Exception {
        when(documentService.createDocument(any())).thenReturn(dto);
        final String JSON_CONTENT = objectMapper.writeValueAsString(createDocumentRequest);

        MockHttpServletRequestBuilder postMethod = post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_CONTENT);

        mockMvc.perform(postMethod)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.documentType").value(dto.getDocumentType().toString()))
                .andExpect(jsonPath("$.number").value(dto.getNumber()));        
    }

    @Test
    void mustReturnSuccessWhenListDocumentWithPagination() throws Exception {
        Page<DocumentDTO> documentPagined = new PageImpl<>(List.of(dto));
        when(documentService.findAllDocumentWithPagination(anyInt(), anyInt())).thenReturn(documentPagined);

        MockHttpServletRequestBuilder getMethod = get(BASE_URL)
                .param("page", "0")
                .param("pageSize", "10");

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(documentPagined.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(documentPagined.getTotalPages()))
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").value(dto.getId()))
                .andExpect(jsonPath("$.content[0].documentType").value(dto.getDocumentType().toString()))
                .andExpect(jsonPath("$.content[0].number").value(dto.getNumber()));
    }

    @Test
    void mustReturnSuccessWhenFindDocumentById() throws Exception {
        when(documentService.findDocumentById(anyLong())).thenReturn(dto);

        MockHttpServletRequestBuilder getMethod = get(BASE_URL + "/{id}", DOCUMENT_ID);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.documentType").value(dto.getDocumentType().toString()))
                .andExpect(jsonPath("$.number").value(dto.getNumber()));
    }

    @Test
    void mustReturnSuccessWhenFindDocumentByTypeAndNumber() throws Exception {
        when(documentService.findByDocumentTypeAndNumber(any())).thenReturn(dto);
        final String JSON_CONTENT = objectMapper.writeValueAsString(params);

        MockHttpServletRequestBuilder getMethod = get(BASE_URL + "/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_CONTENT);

        mockMvc.perform(getMethod)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.documentType").value(dto.getDocumentType().toString()))
                .andExpect(jsonPath("$.number").value(dto.getNumber()));
    }

    @Test
    void mustReturnSuccessWhenDeleteDocumentById() throws Exception {
        final String MSG_SUCCESS = "Documento removido com sucesso";
        doNothing().when(documentService).deleteDocumentById(anyLong());

        mockMvc.perform(delete(BASE_URL + "/{id}", DOCUMENT_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(MSG_SUCCESS));
    }
}
