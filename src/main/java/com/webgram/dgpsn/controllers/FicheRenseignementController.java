package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.FicheRenseignementDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.FicheRenseignementService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ficheRenseignement")
@Tag(name = "ficheRenseignement", description = "Fiche Renseignement Management")
@RequiredArgsConstructor
public class FicheRenseignementController {
    private final FicheRenseignementService ficheRenseignementService;

    @Operation(summary = "Create fiche renseignement", description = "This endpoint takes a fiche renseignement input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FicheRenseignementDTO createFicheRenseignement(@RequestBody FicheRenseignementDTO ficheRenseignementDTO) {
        return ficheRenseignementService.create(ficheRenseignementDTO);
    }

    @Operation(summary = "Create multiple fiche renseignement", description = "This endpoint takes a list of fiche renseignement and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FicheRenseignementDTO> createMultipleFicheRenseignements(@RequestBody List<FicheRenseignementDTO> ficheRenseignementDTOs) {
        return ficheRenseignementService.createMultiple(ficheRenseignementDTOs);
    }

    @Operation(summary = "Update fiche renseignement", description = "This endpoint updates an existing fiche renseignement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{ficheRenseignementId}")
    @ResponseStatus(HttpStatus.OK)
    public FicheRenseignementDTO updateFicheRenseignement(
            @Parameter(name = "ficheRenseignementId", description = "The fiche renseignement ID to update") @PathVariable("ficheRenseignementId") Long ficheRenseignementId,
            @RequestBody FicheRenseignementDTO ficheRenseignementDTO) {
        ficheRenseignementDTO.setId(ficheRenseignementId);
        return ficheRenseignementService.update(ficheRenseignementDTO);
    }

    @Operation(summary = "Read fiche renseignement", description = "This endpoint retrieves a fiche renseignement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{ficheRenseignementId}")
    @ResponseStatus(HttpStatus.OK)
    public FicheRenseignementDTO readFicheRenseignement(
            @Parameter(name = "ficheRenseignementId", description = "The fiche renseignement ID to read") @PathVariable Long ficheRenseignementId) {
        return ficheRenseignementService.read(ficheRenseignementId);
    }

    @Operation(summary = "Delete fiche renseignement", description = "This endpoint deletes a fiche renseignement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{ficheRenseignementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFicheRenseignement(
            @Parameter(name = "ficheRenseignementId", description = "The fiche renseignement ID to delete") @PathVariable Long ficheRenseignementId) {
        ficheRenseignementService.delete(ficheRenseignementId);
    }

    @Operation(summary = "Read all fiche renseignement", description = "This endpoint retrieves all fiche renseignement with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allFicheRenseignements")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllFicheRenseignements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = ficheRenseignementService.readAllFicheRenseignements(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}