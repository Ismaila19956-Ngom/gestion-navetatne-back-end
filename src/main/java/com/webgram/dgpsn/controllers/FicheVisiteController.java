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
import com.webgram.dgpsn.models.FicheVisiteDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.FicheVisiteService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ficheVisite")
@Tag(name = "ficheVisite", description = "Fiche Visite Management")
@RequiredArgsConstructor
public class FicheVisiteController {
    private final FicheVisiteService ficheVisiteService;

    @Operation(summary = "Create fiche visite", description = "This endpoint takes a fiche visite input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FicheVisiteDTO createFicheVisite(@RequestBody FicheVisiteDTO ficheVisiteDTO) {
        return ficheVisiteService.create(ficheVisiteDTO);
    }

    @Operation(summary = "Create multiple fiche visites", description = "This endpoint takes a list of fiche visites and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FicheVisiteDTO> createMultipleFicheVisites(@RequestBody List<FicheVisiteDTO> ficheVisiteDTOs) {
        return ficheVisiteService.createMultiple(ficheVisiteDTOs);
    }

    @Operation(summary = "Update fiche visite", description = "This endpoint updates an existing fiche visite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{ficheVisiteId}")
    @ResponseStatus(HttpStatus.OK)
    public FicheVisiteDTO updateFicheVisite(
            @Parameter(name = "ficheVisiteId", description = "The fiche visite ID to update") @PathVariable("ficheVisiteId") Long ficheVisiteId,
            @RequestBody FicheVisiteDTO ficheVisiteDTO) {
        ficheVisiteDTO.setId(ficheVisiteId);
        return ficheVisiteService.update(ficheVisiteDTO);
    }

    @Operation(summary = "Read fiche visite", description = "This endpoint retrieves a fiche visite by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{ficheVisiteId}")
    @ResponseStatus(HttpStatus.OK)
    public FicheVisiteDTO readFicheVisite(
            @Parameter(name = "ficheVisiteId", description = "The fiche visite ID to read") @PathVariable Long ficheVisiteId) {
        return ficheVisiteService.read(ficheVisiteId);
    }

    @Operation(summary = "Delete fiche visite", description = "This endpoint deletes a fiche visite by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{ficheVisiteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFicheVisite(
            @Parameter(name = "ficheVisiteId", description = "The fiche visite ID to delete") @PathVariable Long ficheVisiteId) {
        ficheVisiteService.delete(ficheVisiteId);
    }

    @Operation(summary = "Read all fiche visites", description = "This endpoint retrieves all fiche visites with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allFicheVisites")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllFicheVisites(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = ficheVisiteService.readAllFicheVisites(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}