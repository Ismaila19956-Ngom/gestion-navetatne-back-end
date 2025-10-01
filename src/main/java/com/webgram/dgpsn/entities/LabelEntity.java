package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.ReferentielType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "label")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabelEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lab_id")
    private Long id;

    @Column(name = "lab_referentiel_type")
    @Enumerated(EnumType.STRING)
    private ReferentielType referentielType;

    @Column(name = "lab_code", unique = true, nullable = false)
    private String code;

    @Column(name = "lab_label")
    private String libelle;

    @ManyToOne()
    @JoinColumn(name = "lab_category")
    private LabelEntity category;

}
