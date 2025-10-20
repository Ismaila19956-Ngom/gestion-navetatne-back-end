package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.QualiteAirDTO;
import com.webgram.dgpsn.services.QualiteAirService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("/qualite-air")
@Tag(name = "qualite-air", description = "Qualite Air controller")
@RequiredArgsConstructor
public class QualiteAirController {

    private final QualiteAirService qualiteAirService;

    @Operation(summary = "Créer une évaluation de qualité de l'air", description = "Ce endpoint prend une évaluation de qualité de l'air et des fichiers optionnels (bulletins, rapports) en entrée et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public QualiteAirDTO addQualiteAir(
            @RequestPart("qualiteAirData") String qualiteAirDataJson,
            @RequestPart(value = "bulletinsMonthly", required = false) MultipartFile[] bulletinsMonthly,
            @RequestPart(value = "bulletinsQuarterly", required = false) MultipartFile[] bulletinsQuarterly,
            @RequestPart(value = "bulletinsAnnual", required = false) MultipartFile[] bulletinsAnnual,
            @RequestPart(value = "analysisReports", required = false) MultipartFile[] analysisReports
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        QualiteAirDTO qualiteAirDTO = objectMapper.readValue(qualiteAirDataJson, QualiteAirDTO.class);
        return qualiteAirService.create(qualiteAirDTO, bulletinsMonthly, bulletinsQuarterly, bulletinsAnnual, analysisReports);
    }

    @Operation(summary = "Mettre à jour une évaluation de qualité de l'air", description = "Ce endpoint met à jour une évaluation existante avec l’ID spécifié et des fichiers optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping(value = "/{qualiteAirId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public QualiteAirDTO updateQualiteAir(
            @Parameter(name = "qualiteAirId", description = "L’ID de l’évaluation de qualité de l'air à mettre à jour")
            @PathVariable Long qualiteAirId,
            @RequestPart("qualiteAirData") String qualiteAirDataJson,
            @RequestPart(value = "bulletinsMonthly", required = false) MultipartFile[] bulletinsMonthly,
            @RequestPart(value = "bulletinsQuarterly", required = false) MultipartFile[] bulletinsQuarterly,
            @RequestPart(value = "bulletinsAnnual", required = false) MultipartFile[] bulletinsAnnual,
            @RequestPart(value = "analysisReports", required = false) MultipartFile[] analysisReports
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        QualiteAirDTO qualiteAirDTO = objectMapper.readValue(qualiteAirDataJson, QualiteAirDTO.class);
        qualiteAirDTO.setId(qualiteAirId);
        return qualiteAirService.update(qualiteAirDTO, bulletinsMonthly, bulletinsQuarterly, bulletinsAnnual, analysisReports);
    }

    @Operation(summary = "Lire une évaluation de qualité de l'air", description = "Ce endpoint retourne une évaluation avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{qualiteAirId}")
    @ResponseStatus(HttpStatus.OK)
    public QualiteAirDTO readQualiteAir(
            @Parameter(name = "qualiteAirId", description = "L’ID de l’évaluation à lire")
            @PathVariable Long qualiteAirId
    ) {
        return qualiteAirService.read(qualiteAirId);
    }

    @Operation(summary = "Supprimer une évaluation de qualité de l'air", description = "Ce endpoint supprime une évaluation avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{qualiteAirId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQualiteAir(
            @Parameter(name = "qualiteAirId", description = "L’ID de l’évaluation à supprimer")
            @PathVariable Long qualiteAirId
    ) {
        qualiteAirService.delete(qualiteAirId);
    }

    @Operation(summary = "Lire toutes les évaluations de qualité de l'air", description = "Ce endpoint retourne une liste paginée d’évaluations avec des filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<QualiteAirDTO> readAllQualiteAir(
            Pageable pageable,
            @Parameter(name = "stationId", description = "ID de la station pour filtrer")
            @RequestParam(value = "stationId", required = false) Long stationId,
            @Parameter(name = "measurementDate", description = "Date de mesure pour filtrer")
            @RequestParam(value = "measurementDate", required = false) Date measurementDate,
            @Parameter(name = "iqa", description = "Indice de qualité de l'air pour filtrer")
            @RequestParam(value = "iqa", required = false) String iqa,
            @Parameter(name = "mainPollutantId", description = "ID du polluant principal pour filtrer")
            @RequestParam(value = "mainPollutantId", required = false) Long mainPollutantId,
            @Parameter(name = "preparedBy", description = "Préparé par pour filtrer")
            @RequestParam(value = "preparedBy", required = false) String preparedBy,
            @Parameter(name = "preparationDate", description = "Date de préparation pour filtrer")
            @RequestParam(value = "preparationDate", required = false) Date preparationDate,
            @Parameter(name = "sortBy", description = "Champ utilisé pour trier les résultats")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Ordre de tri (true pour ascendant, false pour descendant)")
            @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return qualiteAirService.readAll(pageable, stationId, measurementDate, iqa, mainPollutantId, preparedBy, preparationDate, sortBy, ascending);
    }

    @Operation(summary = "Télécharger un document de qualité de l'air", description = "Ce endpoint permet de télécharger un fichier associé à une évaluation (bulletin mensuel, trimestriel, annuel ou rapport d’analyse)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier téléchargé avec succès"),
            @ApiResponse(responseCode = "404", description = "Fichier ou évaluation non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{qualiteAirId}/{docType}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long qualiteAirId,
            @PathVariable String docType
    ) throws IOException {
        Resource fileResource = qualiteAirService.downloadFile(qualiteAirId, docType);
        String fileName = fileResource.getFilename() != null ? fileResource.getFilename() : "file";
        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(Path.of(fileName)) != null ? Files.probeContentType(Path.of(fileName)) : contentType;
        } catch (IOException e) {
            // Log warning if needed
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileResource);
    }
}