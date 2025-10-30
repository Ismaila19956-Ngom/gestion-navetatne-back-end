package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.responses.ptba.PtbaResponseDTO;
import com.webgram.dgpsn.services.PtbaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ManagementUnitDTO;
import com.webgram.dgpsn.models.TreeNodeDTO;
import com.webgram.dgpsn.models.responses.StatisticProjectDTO;
import com.webgram.dgpsn.services.ManagementUnitService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/management-unit")
@Tag(name = "management-unit-controller", description = "controlleur pour gérer les programmes, projets et activités")
@RequiredArgsConstructor
public class ManagementUnitController {
    private final ManagementUnitService managementUnitService;
    private final PtbaService ptbaService;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create management unit", description = "this endpoint take input management unit and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type management unit was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ManagementUnitDTO createProjet(@RequestBody ManagementUnitDTO managementUnitDTO) {
        return managementUnitService.create(managementUnitDTO);
    }

    @PutMapping("/{managementUnitId}")
    @ResponseStatus(HttpStatus.OK)
    public ManagementUnitDTO updateProjet(@Parameter(name = "managementUnitId", description = "the management unit id updated") @PathVariable Long managementUnitId, @RequestBody ManagementUnitDTO managementUnitDTO) {
        managementUnitDTO.setId(managementUnitId);
        return managementUnitService.update(managementUnitDTO);
    }

    @Operation(summary = "Read the management unit", description = "This endpoint is used to read management unit it take input id management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{managementUnitId}")
    @ResponseStatus(HttpStatus.OK)
    public ManagementUnitDTO readPorjet(@Parameter(name = "managementUnitId", description = "the type management unit id to read") @PathVariable Long managementUnitId) {
        return managementUnitService.read(managementUnitId);
    }

    @Operation(summary = "delete the management unit", description = "Delete management unit, it take input id management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the management unit was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{managementUnitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePorjet(@Parameter(name = "managementUnitId", description = "the management unit id deleted") @PathVariable Long managementUnitId) {
        managementUnitService.delete(managementUnitId);
    }

    @Operation(summary = "Read all management unit", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ManagementUnitDTO> readAllProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list ManagementUnit") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "name", description = "value of name used to filter list ManagementUnit") @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "type", description = "value of type used to filter list type") @RequestParam(value = "type", required = false) TypeProjet type,
            @Parameter(name = "dateDebut", description = "value of dateDebut used to filter list ManagementUnit") @RequestParam(value = "dateDebut", required = false) String dateDebut,
            @Parameter(name = "dateFin", description = "value of dateFin used to filter list ManagementUnit") @RequestParam(value = "dateFin", required = false) String dateFin,
            @Parameter(name = "budget", description = "value of budget used to filter list ManagementUnit") @RequestParam(value = "budget", required = false) Double budget,
            @Parameter(name = "poids", description = "value of poids used to filter list ManagementUnit") @RequestParam(value = "poids", required = false) Long poids,
            @Parameter(name = "responsibleId", description = "value of responsibleId used to filter list ManagementUnit") @RequestParam(value = "responsibleId", required = false) Long responsibleId,
            @Parameter(name = "tag", description = "value of tags used to filter list ManagementUnit") @RequestParam(value = "tag", required = false) String tag,
            @Parameter(name = "parentId", description = "value of parentId used to filter list ManagementUnit") @RequestParam(value = "parentId", required = false) Long parentId,
            @Parameter(name = "axePse", description = "value of axePSE used to filter list ManagementUnit") @RequestParam(value = "axePSEId", required = false) Long axePseId,
            @Parameter(name = "publish", description = "value of publish used to filter list projet") @RequestParam(value = "publish", required = false) Boolean publish
    ) {
        return managementUnitService.readAll(
                pageable, code, name, type, dateDebut, dateFin,
                budget, poids, responsibleId, tag,parentId, axePseId,publish);
    }

    @Operation(summary = "Import management unit", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the management unit was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importProject(@RequestParam("file") MultipartFile file) {
        managementUnitService.importProject(file);
    }

    @GetMapping(value = "/export/excel", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Projet_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        managementUnitService.export(response.getWriter());
    }

    @GetMapping(value = "/exportProjets/excel", produces = "text/csv")
    public void exportProjets(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Projet_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        managementUnitService.exportProjets(response.getWriter());
    }

    @Operation(
            summary = "Export pdf file",
            description = "this endpoint is used to export a  pdf file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/export/pdf", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> exportPdf() {
        /* Getting downloadFile */
        DownloadFile downloadFile = managementUnitService.generateFilePdf();

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));

    }

    @Operation(summary = "Uploade an image", description = "this endpoint take input management unit id and file and then save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the management unit was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping(path = "/upload-image/{managementUnitId}", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImage(@PathVariable Long managementUnitId, @RequestPart(name = "file", required = false) MultipartFile file) throws IOException  {
        managementUnitService.uploadImage(managementUnitId, file);
    }

    @Operation(
            summary = "Publish or unpublish a management unit",
            description = "this endpoint is used to publish or unpublished a management unit .")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/publishUnpublish/{projetId}")
    @ResponseStatus(HttpStatus.OK)
    public void publishOrUnpublish(@Parameter(name = "projetId", description = "the projet id to updated") @PathVariable Long projetId) {
        managementUnitService.publishOrUnpublish(projetId);
    }

    @Operation(summary = "Read all published management unit", description = "It return all published management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/publish")
    public List<ManagementUnitDTO> readPublishedProjects() {
        return managementUnitService.readPublishedProjects();
    }

    @Operation(summary = "Read static management unit", description = "It return statistic management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/statistics")
    public StatisticProjectDTO readStatisticsProject() {
        return managementUnitService.readStatisticProject();
    }

    @Operation(summary = "Read all management unit tree", description = "It return a tree management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tree/{managementUnitId}")
    public TreeNodeDTO readTreeManagementUnit(@PathVariable Long managementUnitId) {
        return managementUnitService.readTreeManagmentUnit(managementUnitId);
    }

    @Operation(summary = "Add node to tree", description = "this endpoint take input TreeNodeDTO and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type management unit was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/tree/add/{parentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TreeNodeDTO addNodeToTreeManagmentUnit(
            @PathVariable Long parentId, TreeNodeDTO nodeDTO) {
        return managementUnitService.addNodeToTreeManagmentUnit(parentId, nodeDTO);
    }

    @Operation(summary = "Read all management unit tree", description = "It return a tree management unit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tree/all")
    public List<TreeNodeDTO> readAllProjectsWithTree() {
        return managementUnitService.readAllProjectsWithTree();
    }

    @Operation(
            summary = "Generate PTBA",
            description = "Génère le Plan de Travail et Budget Annuel (PTBA) pour un projet donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Projet non trouvé"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{managementUnitId}/ptba")
    @ResponseStatus(HttpStatus.OK)
    public PtbaResponseDTO generatePtba(
            @Parameter(name = "managementUnitId", description = "L'ID du projet/programme")
            @PathVariable Long managementUnitId,
            @Parameter(name = "annee", description = "L'année pour le PTBA (optionnel, utilise anneeDebut par défaut)")
            @RequestParam(value = "annee", required = false) Integer annee) {
        return ptbaService.generatePtba(managementUnitId, annee);
    }
}
