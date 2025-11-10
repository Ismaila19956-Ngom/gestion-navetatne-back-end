package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Realisation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RealisationEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "realisation_id")
    private Long id;

    @Column(name = "realisation_code")
    private String code;

    @Column(name = "realisation_montant")
    private Double montant;

    @Column(name = "realisation_date")
    private LocalDate date;

//    @Column(name = "realisation_fournisseur")
//    private String fournisseur;
    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private FournisseurEntity fournisseur;


    @Column(name = "realisation_numero_bon")
    private String numeroBon;

    @Column(name = "realisation_numero_be")
    private String numeroBE;

    @Column(name = "realisation_numero_mandat")
    private String numeroMandat;

    @Column(name = "realisation_Facture")
    private String facture;

    @Column(name = "realisation_description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ligne_budgetaire_id")
    private LigneBudgetaireEntity ligneBudgetaire;

    @ManyToOne
    @JoinColumn(name = "realisation_compte_id")
    private PlanComptableElementEntity realisations;
}