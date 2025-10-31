package com.webgram.dgpsn.models.responses.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EngagementDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numBon;
    private LocalDate dateEngagement;
    private String numBE;
    private String numMandat;
    private String factureEtat;
    private Double montants;
    private String fournisseurBeneficiaire;
    private String servicesDGPSN;
    private String compte;
    private LocalDate date;
}
