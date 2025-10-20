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
import com.webgram.dgpsn.models.InspectionICPEDTO;
import com.webgram.dgpsn.services.InspectionICPEService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("/inspections-icpe")
@Tag(name = "inspections ICPE", description = "Inspections ICPE controller")
@RequiredArgsConstructor
public class InspectionICPEController {

    private final InspectionICPEService inspectionICPEService;

    @Operation(summary = "Créer une inspection ICPE", description = "Ce endpoint prend une inspection ICPE et des fichiers optionnels (photos, documents, rapports) en entrée et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public InspectionICPEDTO addInspectionICPE(
            @RequestPart("inspectionData") String inspectionDataJson,
            @RequestPart(value = "photos", required = false) MultipartFile[] photos,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents,
            @RequestPart(value = "analysisReports", required = false) MultipartFile[] analysisReports
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InspectionICPEDTO inspectionICPEDTO = objectMapper.readValue(inspectionDataJson, InspectionICPEDTO.class);
        return inspectionICPEService.create(inspectionICPEDTO, photos, documents, analysisReports);
    }

    @Operation(summary = "Mettre à jour une inspection ICPE", description = "Ce endpoint met à jour une inspection ICPE existante avec l’ID spécifié et des fichiers optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping(value = "/{inspectionICPEId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public InspectionICPEDTO updateInspectionICPE(
            @Parameter(name = "inspectionICPEId", description = "L’ID de l’inspection ICPE à mettre à jour")
            @PathVariable Long inspectionICPEId,
            @RequestPart("inspectionData") String inspectionDataJson,
            @RequestPart(value = "photos", required = false) MultipartFile[] photos,
            @RequestPart(value = "documents", required = false) MultipartFile[] documents,
            @RequestPart(value = "analysisReports", required = false) MultipartFile[] analysisReports
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InspectionICPEDTO inspectionICPEDTO = objectMapper.readValue(inspectionDataJson, InspectionICPEDTO.class);
        inspectionICPEDTO.setId(inspectionICPEId);
        return inspectionICPEService.update(inspectionICPEDTO, photos, documents, analysisReports);
    }

    @Operation(summary = "Supprimer une inspection ICPE", description = "Ce endpoint supprime une inspection ICPE avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{inspectionICPEId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInspectionICPE(
            @Parameter(name = "inspectionICPEId", description = "L’ID de l’inspection ICPE à supprimer")
            @PathVariable Long inspectionICPEId) {
        inspectionICPEService.delete(inspectionICPEId);
    }

    @Operation(summary = "Lire toutes les inspections ICPE", description = "Ce endpoint retourne une liste paginée d’inspections ICPE avec des filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<InspectionICPEDTO> readAllInspectionICPE(
            Pageable pageable,
            @Parameter(name = "code", description = "Valeur du code pour filtrer les inspections ICPE")
            @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "dateInspection", description = "Date de l’inspection pour filtrer les inspections ICPE")
            @RequestParam(value = "dateInspection", required = false) Date dateInspection,
            @Parameter(name = "typeInspectionId", description = "ID du type d’inspection pour filtrer les inspections ICPE")
            @RequestParam(value = "typeInspectionId", required = false) Long typeInspectionId,
            @Parameter(name = "ref", description = "Référence pour filtrer les inspections ICPE")
            @RequestParam(value = "ref", required = false) String ref,
            @Parameter(name = "etablissementId", description = "ID de l’établissement pour filtrer les inspections ICPE")
            @RequestParam(value = "etablissementId", required = false) Long etablissementId,
            @Parameter(name = "teamLeadId", description = "Responsable d’équipe pour filtrer les inspections ICPE")
            @RequestParam(value = "teamLeadId", required = false) Long teamLeadId,
            @Parameter(name = "complianceLevelId", description = "ID du niveau de conformité pour filtrer les inspections ICPE")
            @RequestParam(value = "complianceLevelId", required = false) Long complianceLevelId,
            @Parameter(name = "environmentalRiskId", description = "ID du risque environnemental pour filtrer les inspections ICPE")
            @RequestParam(value = "environmentalRiskId", required = false) Long environmentalRiskId,
            @Parameter(name = "preparedBy", description = "Préparé par pour filtrer les inspections ICPE")
            @RequestParam(value = "preparedBy", required = false) String preparedBy,
            @Parameter(name = "preparationDate", description = "Date de préparation pour filtrer les inspections ICPE")
            @RequestParam(value = "preparationDate", required = false) Date preparationDate,
            @Parameter(name = "sortBy", description = "Champ utilisé pour trier les résultats")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Ordre de tri (true pour ascendant, false pour descendant)")
            @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return inspectionICPEService.readAll(pageable, code, dateInspection, typeInspectionId, ref, etablissementId, teamLeadId, complianceLevelId, environmentalRiskId, preparedBy, preparationDate, sortBy, ascending);
    }

    @Operation(summary = "Télécharger un document d’inspection ICPE", description = "Ce endpoint permet de télécharger un fichier associé à une inspection ICPE (photo, document ou rapport d’analyse)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier téléchargé avec succès"),
            @ApiResponse(responseCode = "404", description = "Fichier ou inspection non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{inspectionICPEId}/{docType}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long inspectionICPEId,
            @PathVariable String docType
    ) throws IOException {
//        log.info("Downloading file for inspection ID: {}, docType: {}", inspectionICPEId, docType);
        Resource fileResource = inspectionICPEService.downloadFile(inspectionICPEId, docType);
        String fileName = fileResource.getFilename() != null ? fileResource.getFilename() : "file";
        String contentType = "application/octet-stream";
        try {
            contentType = Files.probeContentType(Path.of(fileName)) != null ? Files.probeContentType(Path.of(fileName)) : contentType;
        } catch (IOException e) {
//            log.warn("Could not determine content type for file: {}", fileName);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(fileResource);
    }
}