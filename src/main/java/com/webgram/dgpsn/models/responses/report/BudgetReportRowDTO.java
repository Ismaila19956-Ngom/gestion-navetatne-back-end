package com.webgram.dgpsn.models.responses.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BudgetReportRowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isTitle = false;

    // Identifiants hiérarchiques
    private String classe;
    private String compte;
    private String sousCompte;
    private String rubrique;

    // Indicateurs de type de ligne
    private Boolean isCategory;
    private Boolean isSubCategory;
    private Boolean isTotal;

    // Budgets
    private Double budgetInitial;
    private Double primitif;
    private Double modification;
    private Double budgetModifie;

    // Réalisations par période (mois ou trimestres)
    private Map<String, Double> realisationsPeriodiques; // janvier, fevrier, t1, t2, etc.

    // Réalisations au 31/03/2024 (pour récapitulatif)
    private Double realisationsAu31032024;

    // Synthèse
    private Double realisationsCumulees;
    private Double tauxExecution; // en pourcentage
    private Double niveauExecution; // en pourcentage (pour récapitulatif)
    private Double resteARealiser;

    // Cumuls trimestriels (pour récapitulatif)
    private Double t1;
    private Double t2;
    private Double t3;
    private Double t4;
}
