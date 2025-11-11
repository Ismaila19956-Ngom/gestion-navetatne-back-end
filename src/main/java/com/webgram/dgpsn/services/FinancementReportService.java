package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.responses.report.EngagementDTO;
import com.webgram.dgpsn.models.responses.report.FinancialReportDTO;
import com.webgram.dgpsn.models.responses.report.BudgetReportRowDTO;

import java.util.List;

public interface FinancementReportService {
    
    /**
     * Génère le rapport financier complet
     */
    FinancialReportDTO generateFinancialReport(Integer annee, String periodType);

    /**
     *  Génère les recettes de fonctionnement (Classe 7)
     */

    List<BudgetReportRowDTO> generateRecettesFonctionnement(List<Long> budgetIds, Integer annee, String periodType);

    /**
     *  Génère les dépenses de fonctionnement (Classe 6)
     */
    List<BudgetReportRowDTO> generateDepensesFonctionnement(List<Long> budgetIds, Integer annee, String periodType);


    List<BudgetReportRowDTO> generateInvestissements(List<Long> budgetIds, Integer annee, String periodType);

    List<BudgetReportRowDTO> generateRecapitulatifInvestissement(List<Long> budgetIds, Integer annee);

    List<EngagementDTO> getEngagements(List<Long> budgetIds, Integer annee);


}
