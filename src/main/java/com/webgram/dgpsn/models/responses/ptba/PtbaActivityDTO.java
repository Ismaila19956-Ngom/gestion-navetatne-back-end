package com.webgram.dgpsn.models.responses.ptba;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PtbaActivityDTO {
    private Long id;
    private String objectif;
    private String objectifCode;
    private String action;
    private String actionCode;
    private String activite;
    private String activiteCode;
    private String indicateurs;
    private String taches;

    // Planification par trimestre
    private PtbaTrimesterDTO trimestre1;
    private PtbaTrimesterDTO trimestre2;
    private PtbaTrimesterDTO trimestre3;
    private PtbaTrimesterDTO trimestre4;

    // Acteurs
    private String acteurResponsable;
    private String acteurImplique;

    // Coûts
    private Double coutCFA;

    // Sources de financement
    private PtbaFundingSourcesDTO sourcesFinancement;

    // Vérification et observations
    private String sourcesVerification;
    private String observations;
}
