package br.com.douglasdreer.the_barbers_forge.entities;

import br.com.douglasdreer.the_barbers_forge.enums.DocumentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a document entity in the system, which can be associated with customers
 * and contains information about document type, number, and timestamps.
 */
@Entity
@Table(name = "TBL0002_DOCUMENTS")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Document {

    /**
     * The unique identifier for the document.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the document (e.g., ID, passport, etc.).
     * This field uses an enumerated value to ensure valid document types.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    /**
     * The number associated with the document (e.g., passport number, ID number).
     */
    @Column(nullable = false)
    private String number;

    /**
     * A list of customers associated with this document.
     * This relationship is managed through a join table.
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "TBL0003_CUSTOMERS_CONTACTS",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<Customer> customers;

    /**
     * The timestamp indicating when the document was created in the system.
     * This field is automatically set when the entity is first created and is not updatable.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last time the document data was updated.
     * This field is automatically updated on each modification of the entity.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
