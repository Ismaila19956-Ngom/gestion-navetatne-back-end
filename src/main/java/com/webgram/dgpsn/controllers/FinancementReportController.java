package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.responses.report.FinancialReportDTO;
import com.webgram.dgpsn.services.FinancementReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports/financement")
@Tag(name = "Rapports Financement", description = "Endpoints pour les rapports de financement")
@RequiredArgsConstructor
public class FinancementReportController {

    private final FinancementReportService financementReportService;

    @Operation(summary = "Générer le rapport financier complet par année",
            description = "Génère un rapport financier complet incluant recettes, dépenses, investissements et engagements pour tous les budgets d'une année donnée.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rapport généré avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun budget trouvé pour l'année spécifiée"), // MODIFIÉ
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping
    public ResponseEntity<FinancialReportDTO> getFinancialReport(
            @Parameter(description = "Année du rapport", example = "2025")
            @RequestParam Integer annee,

            @Parameter(description = "Type de période: mensuel, trimestriel, annuel", example = "annuel")
            @RequestParam(defaultValue = "annuel") String periodType) { // MODIFIÉ: budgetId supprimé

        // MODIFIÉ: Appel de service sans budgetId
        FinancialReportDTO report = financementReportService.generateFinancialReport(annee, periodType);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
