package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Engagement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EngagementEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "engagement_id")
    private Long id;

    @Column(name = "engagement_mois", nullable = false)
    private String mois;

    @Column(name = "engagement_reference", nullable = false)
    private String reference;

    @Column(name = "engagement_objet", nullable = false)
    private String objet;

    @Column(name = "engagement_montant_engage", nullable = false)
    private Double montantEngage;

    @Column(name = "engagement_statut", nullable = false)
    private String statut;

    @Column(name = "engagement_fournisseur", nullable = false)
    private String fournisseur;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "type_engagement_id", nullable = false)
//    private LabelEntity typeEngagement;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "structure_beneficiaire_id", nullable = false)
//    private LabelEntity structureBeneficiaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_passation_id", nullable = false)
    private BudgetPassationEntity budgetPassation;

    @Column(name = "engagement_date_engagement", nullable = false)
    private LocalDate dateEngagement;

    @Column(name = "engagement_date_reception_facture")
    private LocalDate dateReceptionFacture;

    @Column(name = "engagement_justificatif")
    private String justificatif;

    @Column(name = "engagement_cree_par")
    private String creePar;

    @Column(name = "engagement_date_creation")
    private LocalDate dateCreation;
}