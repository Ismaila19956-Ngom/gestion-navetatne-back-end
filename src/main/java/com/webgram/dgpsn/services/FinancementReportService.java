package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.responses.report.EngagementDTO;
import com.webgram.dgpsn.models.responses.report.FinancialReportDTO;
import com.webgram.dgpsn.models.responses.report.BudgetReportRowDTO;

import java.util.List;

public interface FinancementReportService {
    
    /**
     * Génère le rapport financier complet
     */
    FinancialReportDTO generateFinancialReport(Integer annee, String periodType, Long budgetId);
    
    /**
     * Génère les recettes de fonctionnement (Classe 7)
     */
    List<BudgetReportRowDTO> generateRecettesFonctionnement(Long budgetId, Integer annee, String periodType);
    
    /**
     * Génère les dépenses de fonctionnement (Classe 6)
     */
    List<BudgetReportRowDTO> generateDepensesFonctionnement(Long budgetId, Integer annee, String periodType);
    
    /**
     * Génère les dépenses d'investissement (Classe 2)
     */
    List<BudgetReportRowDTO> generateInvestissements(Long budgetId, Integer annee, String periodType);
    
    /**
     * Génère le récapitulatif des investissements
     */
    List<BudgetReportRowDTO> generateRecapitulatifInvestissement(Long budgetId, Integer annee);
    
    /**
     * Récupère les engagements pour une année donnée
     */
    List<EngagementDTO> getEngagements(Long budgetId, Integer annee);
}
