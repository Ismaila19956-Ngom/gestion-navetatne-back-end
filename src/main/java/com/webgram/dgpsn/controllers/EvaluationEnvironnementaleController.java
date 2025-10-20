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
import com.webgram.dgpsn.models.EvaluationEnvironnementaleDTO;
import com.webgram.dgpsn.services.EvaluationEnvironnementaleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/evaluations-environnementales")
@Tag(name = "Evaluations Environnementales", description = "Evaluations Environnementales controller")
@RequiredArgsConstructor
public class EvaluationEnvironnementaleController {

    private final EvaluationEnvironnementaleService evaluationEnvironnementaleService;

    @Operation(summary = "Créer une évaluation environnementale", description = "Ce endpoint prend une évaluation environnementale et des fichiers optionnels (carte géographique, plan de masse, etc.) en entrée et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationEnvironnementaleDTO addEvaluationEnvironnementale(
            @RequestPart("evaluationData") String evaluationDataJson,
            @RequestPart(value = "carteGeographique", required = false) MultipartFile carteGeographique,
            @RequestPart(value = "planMasse", required = false) MultipartFile planMasse,
            @RequestPart(value = "planSituation", required = false) MultipartFile planSituation,
            @RequestPart(value = "planInstallations", required = false) MultipartFile planInstallations,
            @RequestPart(value = "planReseaux", required = false) MultipartFile planReseaux,
            @RequestPart(value = "tdrEtude", required = false) MultipartFile tdrEtude,
            @RequestPart(value = "attestationDomaine", required = false) MultipartFile attestationDomaine,
            @RequestPart(value = "bilanEau", required = false) MultipartFile bilanEau,
            @RequestPart(value = "autresDocuments", required = false) MultipartFile[] autresDocuments
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO = objectMapper.readValue(evaluationDataJson, EvaluationEnvironnementaleDTO.class);
        return evaluationEnvironnementaleService.create(evaluationEnvironnementaleDTO, carteGeographique, planMasse, planSituation, planInstallations, planReseaux, tdrEtude, attestationDomaine, bilanEau, autresDocuments);
    }

    @Operation(summary = "Mettre à jour une évaluation environnementale", description = "Ce endpoint met à jour une évaluation environnementale existante avec l’ID spécifié et des fichiers optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping(value = "/{evaluationEnvironnementaleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EvaluationEnvironnementaleDTO updateEvaluationEnvironnementale(
            @Parameter(name = "evaluationEnvironnementaleId", description = "L’ID de l’évaluation environnementale à mettre à jour")
            @PathVariable Long evaluationEnvironnementaleId,
            @RequestPart("evaluationData") String evaluationDataJson,
            @RequestPart(value = "carteGeographique", required = false) MultipartFile carteGeographique,
            @RequestPart(value = "planMasse", required = false) MultipartFile planMasse,
            @RequestPart(value = "planSituation", required = false) MultipartFile planSituation,
            @RequestPart(value = "planInstallations", required = false) MultipartFile planInstallations,
            @RequestPart(value = "planReseaux", required = false) MultipartFile planReseaux,
            @RequestPart(value = "tdrEtude", required = false) MultipartFile tdrEtude,
            @RequestPart(value = "attestationDomaine", required = false) MultipartFile attestationDomaine,
            @RequestPart(value = "bilanEau", required = false) MultipartFile bilanEau,
            @RequestPart(value = "autresDocuments", required = false) MultipartFile[] autresDocuments
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EvaluationEnvironnementaleDTO evaluationEnvironnementaleDTO = objectMapper.readValue(evaluationDataJson, EvaluationEnvironnementaleDTO.class);
        evaluationEnvironnementaleDTO.setId(evaluationEnvironnementaleId);
        return evaluationEnvironnementaleService.update(evaluationEnvironnementaleDTO, carteGeographique, planMasse, planSituation, planInstallations, planReseaux, tdrEtude, attestationDomaine, bilanEau, autresDocuments);
    }

    @Operation(summary = "Supprimer une évaluation environnementale", description = "Ce endpoint supprime une évaluation environnementale avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{evaluationEnvironnementaleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvaluationEnvironnementale(
            @Parameter(name = "evaluationEnvironnementaleId", description = "L’ID de l’évaluation environnementale à supprimer")
            @PathVariable Long evaluationEnvironnementaleId) {
        evaluationEnvironnementaleService.delete(evaluationEnvironnementaleId);
    }

    @Operation(summary = "Lire toutes les évaluations environnementales", description = "Ce endpoint retourne une liste paginée d’évaluations environnementales avec des filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EvaluationEnvironnementaleDTO> readAllEvaluationEnvironnementale(
            Pageable pageable,
            @Parameter(name = "programmeId", description = "ID du programme pour filtrer les évaluations")
            @RequestParam(value = "programmeId", required = false) Long programmeId,
            @Parameter(name = "projetId", description = "ID du projet pour filtrer les évaluations")
            @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "activiteId", description = "ID de l’activité pour filtrer les évaluations")
            @RequestParam(value = "activiteId", required = false) Long activiteId,
            @Parameter(name = "directionId", description = "ID de la direction pour filtrer les évaluations")
            @RequestParam(value = "directionId", required = false) Long directionId,
            @Parameter(name = "promoteurId", description = "ID du promoteur pour filtrer les évaluations")
            @RequestParam(value = "promoteurId", required = false) Long promoteurId,
            @Parameter(name = "titreProjet", description = "Titre du projet pour filtrer les évaluations")
            @RequestParam(value = "titreProjet", required = false) String titreProjet,
            @Parameter(name = "sortBy", description = "Champ utilisé pour trier les résultats")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Ordre de tri (true pour ascendant, false pour descendant)")
            @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return evaluationEnvironnementaleService.readAll(pageable, programmeId, projetId, activiteId, directionId, promoteurId, titreProjet, sortBy, ascending);
    }

    @Operation(summary = "Télécharger un document d’évaluation environnementale", description = "Ce endpoint permet de télécharger un fichier associé à une évaluation environnementale (carte géographique, plan de masse, etc.)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fichier téléchargé avec succès"),
            @ApiResponse(responseCode = "404", description = "Fichier ou évaluation non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{evaluationEnvironnementaleId}/{docType}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable Long evaluationEnvironnementaleId,
            @PathVariable String docType
    ) throws IOException {
        Resource fileResource = evaluationEnvironnementaleService.downloadFile(evaluationEnvironnementaleId, docType);
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