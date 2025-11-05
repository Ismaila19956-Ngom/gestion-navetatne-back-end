package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.services.ExcelExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.models.RealisationExportDTO;
import com.webgram.dgpsn.services.RealisationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/realisation")
@Tag(name = "Realisation :", description = "Endpoint pour gérer les réalisations associées à une ligne budgétaire")
@RequiredArgsConstructor
public class RealisationController {
    private final RealisationService realisationService;
    private final ExcelExportService excelExportService;

    @Operation(summary = "Create realisation", description = "This endpoint takes input realisation and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealisationDTO addRealisation(@RequestBody RealisationDTO realisationDTO) {
        return realisationService.create(realisationDTO);
    }

    @Operation(summary = "Create multiple realisations", description = "This endpoint takes a list of realisations and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<RealisationDTO> addMultipleRealisation(@RequestBody List<RealisationDTO> realisationDTOs) {
        return realisationService.createMultiple(realisationDTOs);
    }

    @Operation(summary = "Update realisation", description = "This endpoint updates an existing realisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{realisationId}")
    @ResponseStatus(HttpStatus.OK)
    public RealisationDTO updateRealisation(
            @Parameter(name = "realisationId", description = "The realisation id updated") @PathVariable Long realisationId,
            @RequestBody RealisationDTO realisationDTO) {
        realisationDTO.setId(realisationId);
        return realisationService.update(realisationDTO);
    }

    @Operation(summary = "Delete realisation", description = "Delete realisation, it takes input id realisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{realisationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRealisation(@Parameter(name = "realisationId", description = "The realisation id deleted") @PathVariable Long realisationId) {
        realisationService.delete(realisationId);
    }

    @Operation(summary = "Read all realisations", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RealisationDTO> readAllRealisation(
            Pageable pageable,
            @Parameter(name = "code", description = "Value of code used to filter list realisation") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "montant", description = "Value of montant used to filter list realisation") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "date", description = "Value of date used to filter list realisation") @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(name = "fournisseur", description = "Value of fournisseur used to filter list realisation") @RequestParam(value = "fournisseur", required = false) String fournisseur,
            @Parameter(name = "numeroBon", description = "Value of numeroBon used to filter list realisation") @RequestParam(value = "numeroBon", required = false) String numeroBon,
            @Parameter(name = "numeroBE", description = "Value of numeroBE used to filter list realisation") @RequestParam(value = "numeroBE", required = false) String numeroBE,
            @Parameter(name = "numeroMandat", description = "Value of numeroMandat used to filter list realisation") @RequestParam(value = "numeroMandat", required = false) String numeroMandat,
            @Parameter(name = "description", description = "Value of description used to filter list realisation") @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "ligneBudgetaireId", description = "Value of ligneBudgetaireId used to filter list realisation") @RequestParam(value = "ligneBudgetaireId", required = false) Long ligneBudgetaireId,
            @Parameter(name = "realisationsId", description = "Value of realisationsId used to filter list realisation") @RequestParam(value = "realisationsId", required = false) Long realisationsId
    ) {
        return realisationService.readAll(pageable, code, realisationsId, montant, date, fournisseur, numeroBon, numeroBE, numeroMandat, description, ligneBudgetaireId);
    }

    // ==================== ENDPOINTS D'EXPORT EXCEL ====================

    @Operation(
            summary = "Export recettes to Excel",
            description = "Exports recettes data to Excel file with styling for a given year, trimestre and period type"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel file generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error during export")
    })
    @GetMapping("/export/recettes")
    public ResponseEntity<byte[]> exportRecettesToExcel(
            @Parameter(name = "annee", description = "Year for the export", required = true)
            @RequestParam(value = "annee") Integer annee,

            @Parameter(name = "trimestre", description = "Trimestre (T1, T2, T3, T4)")
            @RequestParam(value = "trimestre", required = false, defaultValue = "T1") String trimestre,

            @Parameter(name = "typePeriode", description = "Type de période (mensuel, trimestriel, annuel)")
            @RequestParam(value = "typePeriode", required = false, defaultValue = "trimestriel") String typePeriode
    ) {
        try {
            byte[] excelData = realisationService.exportRecettesToExcel(annee, trimestre, typePeriode);

            String fileName = String.format("Recettes_%d_%s_%s.xlsx",
                    annee,
                    trimestre,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Export dépenses to Excel",
            description = "Exports dépenses de fonctionnement data to Excel file with styling for a given year, trimestre and period type"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Excel file generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error during export")
    })
    @GetMapping("/export/depenses")


    public ResponseEntity<byte[]> exportDepensesToExcel(
            @Parameter(name = "annee", description = "Year for the export", required = true)
            @RequestParam(value = "annee") Integer annee,

            @Parameter(name = "trimestre", description = "Trimestre (T1, T2, T3, T4)")
            @RequestParam(value = "trimestre", required = false, defaultValue = "T1") String trimestre,

            @Parameter(name = "typePeriode", description = "Type de période (mensuel, trimestriel, annuel)")
            @RequestParam(value = "typePeriode", required = false, defaultValue = "trimestriel") String typePeriode
    ) {
        try {
            byte[] excelData = realisationService.exportDepensesToExcel(annee, trimestre, typePeriode);

            String fileName = String.format("Depenses_Fonctionnement_%d_%s_%s.xlsx",
                    annee,
                    trimestre,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Debug endpoint - Get all realisations count",
            description = "Returns diagnostic information about realisations in the database"
    )
    @GetMapping("/export/debug")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> debugRealisations(
            @Parameter(name = "annee", description = "Year to check")
            @RequestParam(value = "annee", required = false, defaultValue = "2024") Integer annee
    ) {
        StringBuilder debug = new StringBuilder();

        // Récupérer toutes les réalisations
        Page<RealisationDTO> allRealisations = realisationService.readAll(
                Pageable.unpaged(),
                null, null, null, null, null, null, null, null, null, null
        );

        debug.append("=== DEBUG REALISATIONS ===\n\n");
        debug.append("Total réalisations dans la base: ").append(allRealisations.getTotalElements()).append("\n\n");

        if (!allRealisations.isEmpty()) {
            // Compter par année
            long count2024 = allRealisations.getContent().stream()
                    .filter(r -> r.getDate() != null && r.getDate().getYear() == annee)
                    .count();
            debug.append("Réalisations pour l'année ").append(annee).append(": ").append(count2024).append("\n\n");

            // Compter par type
            long countClasse6 = allRealisations.getContent().stream()
                    .filter(r -> r.getLigneBudgetaire() != null &&
                            r.getLigneBudgetaire().getTypeLigneBugetaire() != null &&
                            r.getLigneBudgetaire().getTypeLigneBugetaire().name().equals("CLASSE_6"))
                    .count();

            long countClasse7 = allRealisations.getContent().stream()
                    .filter(r -> r.getLigneBudgetaire() != null &&
                            r.getLigneBudgetaire().getTypeLigneBugetaire() != null &&
                            r.getLigneBudgetaire().getTypeLigneBugetaire().name().equals("CLASSE_7"))
                    .count();

            long countWithNullType = allRealisations.getContent().stream()
                    .filter(r -> r.getLigneBudgetaire() == null ||
                            r.getLigneBudgetaire().getTypeLigneBugetaire() == null)
                    .count();

            debug.append("Réalisations CLASSE_6 (Dépenses): ").append(countClasse6).append("\n");
            debug.append("Réalisations CLASSE_7 (Recettes): ").append(countClasse7).append("\n");
            debug.append("Réalisations avec Type NULL: ").append(countWithNullType).append("\n\n");

            // Afficher tous les types uniques trouvés
            debug.append("=== TYPES DE LIGNE BUDGETAIRE TROUVÉS ===\n\n");
            allRealisations.getContent().stream()
                    .filter(r -> r.getLigneBudgetaire() != null &&
                            r.getLigneBudgetaire().getTypeLigneBugetaire() != null)
                    .map(r -> r.getLigneBudgetaire().getTypeLigneBugetaire().name())
                    .distinct()
                    .forEach(type -> debug.append("  - ").append(type).append("\n"));
            debug.append("\n");

            // Afficher les premières réalisations pour voir leur structure
            debug.append("=== EXEMPLE DE DONNÉES (10 premières pour année ").append(annee).append(") ===\n\n");
            allRealisations.getContent().stream()
                    .filter(r -> r.getDate() != null && r.getDate().getYear() == annee)
                    .limit(10)
                    .forEach(r -> {
                        debug.append("ID: ").append(r.getId()).append("\n");
                        debug.append("  Code: ").append(r.getCode()).append("\n");
                        debug.append("  Montant: ").append(r.getMontant()).append("\n");
                        debug.append("  Date: ").append(r.getDate()).append("\n");
                        if (r.getLigneBudgetaire() != null) {
                            debug.append("  Ligne budgétaire ID: ").append(r.getLigneBudgetaire().getId()).append("\n");
                            debug.append("  Type: ").append(r.getLigneBudgetaire().getTypeLigneBugetaire()).append("\n");
                            debug.append("  Montant budget: ").append(r.getLigneBudgetaire().getMontant()).append("\n");
                            if (r.getLigneBudgetaire().getRubrique() != null) {
                                debug.append("  Rubrique: ").append(r.getLigneBudgetaire().getRubrique().getLibelle()).append("\n");
                            }
                        } else {
                            debug.append("  Ligne budgétaire: NULL\n");
                        }
                        debug.append("\n");
                    });
        }

        return ResponseEntity.ok(debug.toString());
    }

    @Operation(
            summary = "Debug getAllRealisationsByYear method",
            description = "Tests the getAllRealisationsByYear filtering logic"
    )
    @GetMapping("/export/debug/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> debugFilterMethod(
            @Parameter(name = "annee", description = "Year to check")
            @RequestParam(value = "annee", required = false, defaultValue = "2024") Integer annee
    ) {
        StringBuilder result = new StringBuilder();
        result.append("=== DEBUG FILTER METHOD ===\n\n");

        // Test direct de getAllRealisationsByYear
        Page<RealisationDTO> all = realisationService.readAll(
                Pageable.unpaged(),
                null, null, null, null, null, null, null, null, null, null
        );

        result.append("Total realisations from readAll: ").append(all.getTotalElements()).append("\n\n");

        result.append("Details of all realisations:\n");
        for (RealisationDTO r : all.getContent()) {
            result.append("  ID: ").append(r.getId());
            result.append(", Date: ").append(r.getDate());
            result.append(", Year: ").append(r.getDate() != null ? r.getDate().getYear() : "NULL");
            result.append(", Type: ").append(r.getLigneBudgetaire() != null && r.getLigneBudgetaire().getTypeLigneBugetaire() != null
                    ? r.getLigneBudgetaire().getTypeLigneBugetaire().name()
                    : "NULL");
            result.append("\n");
        }

        // NOW test the filtering logic manually
        result.append("\n=== TESTING FILTER FOR YEAR ").append(annee).append(" AND TYPE CLASSE_7 ===\n\n");

        long countAfterYearFilter = all.getContent().stream()
                .filter(r -> r.getDate() != null && r.getDate().getYear() == annee)
                .count();
        result.append("After year filter: ").append(countAfterYearFilter).append(" realisations\n");

        long countAfterTypeFilter = all.getContent().stream()
                .filter(r -> r.getDate() != null && r.getDate().getYear() == annee)
                .filter(r -> r.getLigneBudgetaire() != null &&
                             r.getLigneBudgetaire().getTypeLigneBugetaire() != null &&
                             r.getLigneBudgetaire().getTypeLigneBugetaire().name().equals("CLASSE_7"))
                .count();
        result.append("After type filter (CLASSE_7): ").append(countAfterTypeFilter).append(" realisations\n\n");

        // Show which ones match
        result.append("Matching realisations:\n");
        all.getContent().stream()
                .filter(r -> r.getDate() != null && r.getDate().getYear() == annee)
                .filter(r -> r.getLigneBudgetaire() != null &&
                             r.getLigneBudgetaire().getTypeLigneBugetaire() != null &&
                             r.getLigneBudgetaire().getTypeLigneBugetaire().name().equals("CLASSE_7"))
                .forEach(r -> {
                    result.append("  ID: ").append(r.getId());
                    result.append(", Montant: ").append(r.getMontant());
                    result.append(", Date: ").append(r.getDate());
                    result.append("\n");
                });

        return ResponseEntity.ok(result.toString());
    }

    @Operation(
            summary = "Test recettes data with detailed logging",
            description = "Returns recettes data WITH detailed information for debugging"
    )
    @GetMapping("/export/recettes/test")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> testRecettesData(
            @Parameter(name = "annee", description = "Year for the data", required = true)
            @RequestParam(value = "annee") Integer annee,

            @Parameter(name = "trimestre", description = "Trimestre (T1, T2, T3, T4)")
            @RequestParam(value = "trimestre", required = false, defaultValue = "T1") String trimestre,

            @Parameter(name = "typePeriode", description = "Type de période")
            @RequestParam(value = "typePeriode", required = false, defaultValue = "trimestriel") String typePeriode
    ) {
        StringBuilder result = new StringBuilder();
        result.append("=== TEST EXPORT RECETTES ===\n\n");
        result.append("Paramètres reçus:\n");
        result.append("  Année: ").append(annee).append("\n");
        result.append("  Trimestre: ").append(trimestre).append("\n");
        result.append("  Type période: ").append(typePeriode).append("\n\n");

        List<RealisationExportDTO> data = realisationService.getRecettesData(annee, trimestre, typePeriode);

        result.append("Nombre de lignes retournées: ").append(data.size()).append("\n\n");

        for (int i = 0; i < data.size(); i++) {
            RealisationExportDTO dto = data.get(i);
            result.append("--- Ligne ").append(i + 1).append(" ---\n");
            result.append("  Libellé: ").append(dto.getLibelle()).append("\n");
            result.append("  Budget: ").append(dto.getBudget()).append("\n");
            result.append("  Services votées: ").append(dto.getServicesVotees()).append("\n");
            result.append("  Réalisation T1: ").append(dto.getRealisationT1()).append("\n");
            result.append("  Réalisation T2: ").append(dto.getRealisationT2()).append("\n");
            result.append("  Réalisation T3: ").append(dto.getRealisationT3()).append("\n");
            result.append("  Réalisation T4: ").append(dto.getRealisationT4()).append("\n");
            result.append("  Taux réalisation T1: ").append(dto.getTauxRealisationT1()).append("%\n");
            result.append("  Taux réalisation global: ").append(dto.getTauxRealisationGlobal()).append("%\n");
            result.append("  Ecart: ").append(dto.getEcart()).append("\n");
            result.append("  Est total: ").append(dto.getIsTotal()).append("\n");
            result.append("\n");
        }

        return ResponseEntity.ok(result.toString());
    }

    @Operation(
            summary = "Get recettes data for preview",
            description = "Returns the recettes data that will be exported (for preview purposes)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export/recettes/preview")
    @ResponseStatus(HttpStatus.OK)
    public List<RealisationExportDTO> getRecettesDataPreview(
            @Parameter(name = "annee", description = "Year for the data", required = true)
            @RequestParam(value = "annee") Integer annee,

            @Parameter(name = "trimestre", description = "Trimestre (T1, T2, T3, T4)")
            @RequestParam(value = "trimestre", required = false, defaultValue = "T1") String trimestre,

            @Parameter(name = "typePeriode", description = "Type de période (mensuel, trimestriel, annuel)")
            @RequestParam(value = "typePeriode", required = false, defaultValue = "trimestriel") String typePeriode
    ) {
        return realisationService.getRecettesData(annee, trimestre, typePeriode);
    }

    @Operation(
            summary = "Get dépenses data for preview",
            description = "Returns the dépenses data that will be exported (for preview purposes)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/export/depenses/preview")
    @ResponseStatus(HttpStatus.OK)
    public List<RealisationExportDTO> getDepensesDataPreview(
            @Parameter(name = "annee", description = "Year for the data", required = true)
            @RequestParam(value = "annee") Integer annee,

            @Parameter(name = "trimestre", description = "Trimestre (T1, T2, T3, T4)")
            @RequestParam(value = "trimestre", required = false, defaultValue = "T1") String trimestre,

            @Parameter(name = "typePeriode", description = "Type de période (mensuel, trimestriel, annuel)")
            @RequestParam(value = "typePeriode", required = false, defaultValue = "trimestriel") String typePeriode
    ) {
        return realisationService.getDepensesData(annee, trimestre, typePeriode);
    }
}
