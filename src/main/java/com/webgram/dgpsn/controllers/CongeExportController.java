package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.services.Impl.CongeExportServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/conges/export")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Export Congés", description = "APIs pour l'exportation des congés et cessations des agents")
public class CongeExportController {

    private final CongeExportServiceImpl congeExportService;

    @GetMapping(value = "/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Operation(
            summary = "Exporter les congés au format Excel",
            description = "Génère un fichier Excel détaillé avec tous les congés et cessations des agents pour une année donnée, " +
                    "incluant les absences mensuelles, les jours autorisés de l'année précédente et les statistiques"
    )
    @ApiResponse(responseCode = "200", description = "Export réussi")
    @ApiResponse(responseCode = "500", description = "Erreur lors de l'export")
    public ResponseEntity<byte[]> exportCongesExcel(
            @Parameter(description = "Année pour l'export (par défaut: année en cours)")
            @RequestParam(required = false) Integer annee) {
        try {
            // Si l'année n'est pas spécifiée, utiliser l'année en cours
            if (annee == null) {
                annee = Calendar.getInstance().get(Calendar.YEAR);
            }

            log.info("Export request received for Excel format for year: {}", annee);

            byte[] excelData = congeExportService.exportCongesAgentsExcel(annee);

            String filename = String.format("conges_agents_%d_%s.xlsx",
                    annee,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            log.info("Excel export completed successfully: {}", filename);

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);

        } catch (IOException e) {
            log.error("Error during Excel export", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/csv", produces = "text/csv; charset=UTF-8")
    @Operation(
            summary = "Exporter les congés au format CSV",
            description = "Génère un fichier CSV avec tous les congés et cessations des agents pour une année donnée"
    )
    @ApiResponse(responseCode = "200", description = "Export réussi")
    @ApiResponse(responseCode = "500", description = "Erreur lors de l'export")
    public void exportCongesCSV(
            HttpServletResponse response,
            @Parameter(description = "Année pour l'export (par défaut: année en cours)")
            @RequestParam(required = false) Integer annee) {
        try {
            // Si l'année n'est pas spécifiée, utiliser l'année en cours
            if (annee == null) {
                annee = Calendar.getInstance().get(Calendar.YEAR);
            }

            log.info("Export request received for CSV format for year: {}", annee);

            String filename = String.format("conges_agents_%d_%s.csv",
                    annee,
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));

            response.setContentType("text/csv; charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + filename + "\"");
            response.setCharacterEncoding("UTF-8");

            // Add BOM for Excel compatibility with UTF-8
            response.getWriter().write('\ufeff');

            PrintWriter writer = response.getWriter();
            congeExportService.exportCongesAgentsCSV(writer, annee);

            writer.flush();
            log.info("CSV export completed successfully: {}", filename);

        } catch (IOException e) {
            log.error("Error during CSV export", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}