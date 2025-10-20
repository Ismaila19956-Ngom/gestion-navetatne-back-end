package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.CategoryDocument;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "categorie_document")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorieDocumentEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cat_doc_id")
    private Long id;

    @Column(name = "doc_categorie_type")
    @Enumerated(EnumType.STRING)
    private CategoryDocument categoryDocument;
    @ManyToOne()
    @JoinColumn(name = "type_document")
    private LabelEntity typeDocument;

}
