package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "sous_categorie")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subcat_id")
    private Long id;

    @Column(name = "subcat_code")
    private String code;

    @Column(name = "subcat_libelle")
    private String libelle;

    @ManyToOne()
    @JoinColumn(name = "subcat_linked_category")
    private LabelEntity category;
}