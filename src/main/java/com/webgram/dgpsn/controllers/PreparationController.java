package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.PreparationDTO;
import com.webgram.dgpsn.services.PreparationService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/preparations")
@Tag(name = "preparation-controller", description = "Preparation controller")
@RequiredArgsConstructor
public class PreparationController {
    private final PreparationService preparationService;

    @Operation(summary = "Create preparation", description = "this endpoint take input preparation and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the preparation was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PreparationDTO createPreparation(@RequestBody PreparationDTO preparationDTO) {
        return preparationService.create(preparationDTO);
    }

    @PutMapping("/{preparationId}")
    @ResponseStatus(HttpStatus.OK)
    public PreparationDTO updatePreparation(@Parameter(name = "preparationId", description = "the preparation type id updated") @PathVariable Long preparationId, @RequestBody PreparationDTO preparationDTO) {
        preparationDTO.setId(preparationId);
        return preparationService.update(preparationDTO);
    }

    @Operation(summary = "Read the preparation", description = "This endpoint is used to read preparation  it take input id preparation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{preparationId}")
    @ResponseStatus(HttpStatus.OK)
    public PreparationDTO readPreparation(@Parameter(name = "preparationId", description = "the preparation id to read") @PathVariable Long preparationId) {
        return preparationService.read(preparationId);
    }

    @Operation(summary = "delete the preparation", description = "Delete preparation, it take input id preparation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the preparation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{preparationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePreparation(@Parameter(name = "preparationId", description = "the preparation id deleted") @PathVariable Long preparationId) {
        preparationService.delete(preparationId);
    }

    @Operation(summary = "Read all preparation", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PreparationDTO> readAllPreparations(
            Pageable pageable,
            @Parameter(name = "deadline", description = "value of deadline used to filter list preparation") @RequestParam(value = "deadline", required = false) Date deadline,
            @Parameter(name = "libelle", description = "value of label used to filter list preparation") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "phaseId", description = "value of phase used to filter list preparation") @RequestParam(value = "phaseId", required = false) Long phaseId,
            @Parameter(name = "agentId", description = "value of agent used to filter list preparation") @RequestParam(value = "agentId", required = false) Long agentId,
            @Parameter(name = "projetId", description = "value of projet used to filter list preparation") @RequestParam(value = "projetId", required = false) Long projetId
    ) {
        return preparationService.readAll(pageable, libelle, deadline, phaseId, agentId, projetId);
    }

    @Operation(summary = "Import preparation", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importProject(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        preparationService.importPreparation(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Preparation_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        preparationService.exportPreparation(response.getWriter());
    }
}
