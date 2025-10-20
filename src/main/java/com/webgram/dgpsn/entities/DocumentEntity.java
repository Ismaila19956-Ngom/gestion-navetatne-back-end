package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.webgram.dgpsn.entities.enums.CategoryDocument;

@Table(name = "document")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "doc_id")
    private Long id;

    @Column(name = "doc_libelle")
    private String libelle;

    @Column(name = "doc_author")
    private String author;

    @Column(name = "doc_src")
    private String path;

    @Column(name = "doc_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "doc_linked_document_type")
    private LabelEntity documentType;

    @ManyToOne
    @JoinColumn(name = "doc_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "doc_linked_entreprise")
    private EntrepriseEntity entreprise;

    private Long categoryId;
    @Column(name = "doc_category")
    @Enumerated(EnumType.STRING)
    private CategoryDocument category;
    private String categoryLibelle;

}
