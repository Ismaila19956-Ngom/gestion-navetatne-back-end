package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.TypeConge;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import com.webgram.dgpsn.entities.audits.Auditable;

import java.io.Serializable;
import java.sql.Date;


@Table(name = "cessationfonction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class CessationFonctionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cessa_id")
    private Long id;
    @Column(name = "cessa_soldeAnnuel", length = 200)
    private Integer soldeAnnuel;
    @Column(name = "cessa_libelle", length = 200)
    private String libelle;
    @Column(name = "cessa_annee", length = 200)
    private Integer annee;
    @Column(name = "cessa_numberJour", length = 200)
    private Integer nombreDeJoursdemande;
    @Column(name = "cessa_dateCessation")
    @Temporal(TemporalType.DATE)
    private Date dateCessation;
    @Column(name = "cessa_description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "cessa_numeroFiche", columnDefinition = "TEXT")
    private String numFicheCessation;
    @Column(name = "cessa_type_conge")
    @Enumerated(EnumType.STRING)
    private TypeConge typeConge;
    @JoinColumn(name = "cessa_linked_conge")
    @ManyToOne
    private CongeEntity conge;


}
