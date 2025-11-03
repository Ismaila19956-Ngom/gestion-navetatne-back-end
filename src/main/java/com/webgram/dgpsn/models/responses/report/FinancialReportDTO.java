package com.webgram.dgpsn.models.responses.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialReportDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer annee;
    private String periodType; // "mensuel", "trimestriel", "annuel"
    
    private List<BudgetReportRowDTO> recettesFonctionnement;
    private List<BudgetReportRowDTO> depensesFonctionnement;
    private List<BudgetReportRowDTO> investissements;
    private List<BudgetReportRowDTO> recapInvestissement;
    private List<EngagementDTO> engagements;
}
