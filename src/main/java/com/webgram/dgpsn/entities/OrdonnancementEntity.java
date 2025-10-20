package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Ordonnancement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrdonnancementEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ordonnancement_id")
    private Long id;

    @Column(name = "ordonnancement_reference", nullable = false)
    private String reference;

    @Column(name = "ordonnancement_date_ordonnancement", nullable = false)
    private LocalDate dateOrdonnancement;

    @Column(name = "ordonnancement_montant_ordonne", nullable = false)
    private Double montantOrdonne;

    @Column(name = "ordonnancement_mois", nullable = false)
    private String mois;

    @Column(name = "ordonnancement_objet", nullable = false)
    private String objet;

    @Column(name = "ordonnancement_statut", nullable = false)
    private String statut;

    @Column(name = "ordonnancement_mode_paiement", nullable = false)
    private String modePaiement;

//    @ManyToOne()
//    @JoinColumn(name = "beneficiaire_id")
//    private AgentEntity beneficiaire;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "engagement_id", nullable = false)
//    private EngagementEntity engagement;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_passation_id", nullable = false)
    private BudgetPassationEntity budgetPassation;

    @Column(name = "ordonnancement_justificatif")
    private String justificatif;
}