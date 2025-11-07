package com.webgram.dgpsn.models;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RealisationExportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // Informations de la ligne budgétaire
    private String libelle;
    private String codeRubrique;
    private String typeDepense; // FONCTIONNEMENT ou RECETTE

    // Budget et réalisations
    private Double budget;
    private Double servicesVotees;

    // Réalisations par période
    private Double realisationT1;
    private Double realisationT2;
    private Double realisationT3;
    private Double realisationT4;

    // Réalisations mensuelles (pour export mensuel)
    private Double realisationJanvier;
    private Double realisationFevrier;
    private Double realisationMars;
    private Double realisationAvril;
    private Double realisationMai;
    private Double realisationJuin;
    private Double realisationJuillet;
    private Double realisationAout;
    private Double realisationSeptembre;
    private Double realisationOctobre;
    private Double realisationNovembre;
    private Double realisationDecembre;

    // Taux de réalisation
    private Double tauxRealisationT1;
    private Double tauxRealisationT2;
    private Double tauxRealisationT3;
    private Double tauxRealisationT4;
    private Double tauxRealisationGlobal;

    // Écarts
    private Double ecart;

    // Observations
    private String observations;

    // Indicateur de total
    private Boolean isTotal;

    // Période de référence
    private Integer annee;
    private String trimestre;
    private Integer mois;

    // Informations supplémentaires
    private String fournisseur;
    private String numeroBon;
    private String numeroBE;
    private String numeroMandat;
    private LocalDate dateRealisation;
}
