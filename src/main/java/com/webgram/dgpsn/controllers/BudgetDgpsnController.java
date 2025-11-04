package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.services.LigneBudgetaireService;
import com.webgram.dgpsn.services.RealisationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.BudgetDgpsnDTO;
import com.webgram.dgpsn.services.BudgetDgpsnService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/budgetGlobal")
@Tag(name = "BudgetDgpsn :", description = "Endpoint pour gérer les budgets DGPSN dans programme, projet et activité")
@RequiredArgsConstructor
public class BudgetDgpsnController {
    private final BudgetDgpsnService budgetDgpsnService;
    private final LigneBudgetaireService ligneBudgetaireService;
    private final RealisationService realisationService;

    @Operation(summary = "Create budget DGPSN", description = "This endpoint takes input budget DGPSN and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDgpsnDTO addBudget(@RequestBody BudgetDgpsnDTO budgetDgpsnDTO) {
        return budgetDgpsnService.create(budgetDgpsnDTO);
    }

    @Operation(summary = "Update budget DGPSN", description = "This endpoint updates an existing budget DGPSN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetDgpsnDTO updateBudget(
            @Parameter(name = "budgetId", description = "The budget DGPSN id updated") @PathVariable Long budgetId,
            @RequestBody BudgetDgpsnDTO budgetDgpsnDTO) {
        budgetDgpsnDTO.setId(budgetId);
        return budgetDgpsnService.update(budgetDgpsnDTO);
    }

    @Operation(summary = "Delete budget DGPSN", description = "Delete budget DGPSN, it takes input id budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@Parameter(name = "budgetId", description = "The budget DGPSN id deleted") @PathVariable Long budgetId) {
        budgetDgpsnService.delete(budgetId);
    }

    @Operation(summary = "Read all budget DGPSN", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<BudgetDgpsnDTO> readAllBudgetDgpsn(
            Pageable pageable,
            @Parameter(name = "code", description = "Value of code used to filter list budget") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "Value of libelle used to filter list budget") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "montant", description = "Value of montant used to filter list budget") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "annee", description = "Value of annee used to filter list budget") @RequestParam(value = "annee", required = false) Integer annee
    ) {
        return budgetDgpsnService.readAll(pageable, code, libelle, montant, annee);
    }

//    @GetMapping("/{id}/synthese")
//    public Map<String, Object> getSynthese(
//            @Parameter(description = "ID du budget DGPSN à synthétiser", required = true, example = "1")
//            @PathVariable Long id) {
//        BudgetDgpsnDTO budget = budgetDgpsnService.read(id);
//        List<LigneBudgetaireDTO> lignes = ligneBudgetaireService.findByBudgetId(id);
//        List<RealisationDTO> realisations = realisationService.findByBudgetId(id);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("budget", budget);
//        response.put("lignes", lignes);
//        response.put("realisations", realisations);
//        return response;
//    }

    @GetMapping("/{id}/synthese")
    @Operation(
            summary = "Obtenir la synthèse budgétaire",
            description = "Récupère la synthèse complète d'un budget avec filtrage optionnel par période (annuelle, trimestrielle ou mensuelle)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Synthèse récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Budget non trouvé"),
            @ApiResponse(responseCode = "400", description = "Paramètres de période invalides")
    })
    public ResponseEntity<Map<String, Object>> getSynthese(
            @Parameter(description = "ID du budget DGPSN à synthétiser", required = true, example = "1") @PathVariable Long id,
            @Parameter(description = "Type de période : annee, trimestre, mois", example = "annee")
            @RequestParam(required = false, defaultValue = "annee") String periode,
            @Parameter(description = "Numéro du trimestre (1-4), requis si periode=trimestre", example = "1")
            @RequestParam(required = false) Integer trimestre,
            @Parameter(description = "Numéro du mois (1-12), requis si periode=mois", example = "1")
            @RequestParam(required = false) Integer mois) {

        // Validation des paramètres
        if ("trimestre".equals(periode) && (trimestre == null || trimestre < 1 || trimestre > 4)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Pour la période 'trimestre', le paramètre 'trimestre' doit être entre 1 et 4"));
        }

        if ("mois".equals(periode) && (mois == null || mois < 1 || mois > 12)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Pour la période 'mois', le paramètre 'mois' doit être entre 1 et 12"));
        }

        try {
            Map<String, Object> synthese = budgetDgpsnService.getSyntheseBudgetaire(id, periode, trimestre, mois);
            return ResponseEntity.ok(synthese);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Erreur lors de la récupération de la synthèse : " + e.getMessage()));
        }
    }
}