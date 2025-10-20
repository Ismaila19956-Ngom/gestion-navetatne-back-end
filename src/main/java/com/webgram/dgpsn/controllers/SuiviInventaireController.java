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
import com.webgram.dgpsn.models.SuiviInventaireDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.SuiviInventaireService;

import java.util.Map;

@RestController
@RequestMapping("/suiviInventaire")
@Tag(name = "suiviInventaire", description = "Suivi Inventaire Management")
@RequiredArgsConstructor
public class SuiviInventaireController {
    private final SuiviInventaireService suiviInventaireService;

    @Operation(summary = "Create suivi inventaire", description = "This endpoint takes a suivi inventaire input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuiviInventaireDTO createSuiviInventaire(@RequestBody SuiviInventaireDTO suiviInventaireDTO) {
        return suiviInventaireService.create(suiviInventaireDTO);
    }

    @Operation(summary = "Update suivi inventaire", description = "This endpoint updates an existing suivi inventaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{suiviInventaireId}")
    @ResponseStatus(HttpStatus.OK)
    public SuiviInventaireDTO updateSuiviInventaire(@Parameter(name = "suiviInventaireId", description = "The suivi inventaire ID to update") @PathVariable Long suiviInventaireId, @RequestBody SuiviInventaireDTO suiviInventaireDTO) {
        suiviInventaireDTO.setId(suiviInventaireId);
        return suiviInventaireService.update(suiviInventaireDTO);
    }

    @Operation(summary = "Read suivi inventaire", description = "This endpoint retrieves a suivi inventaire by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{suiviInventaireId}")
    @ResponseStatus(HttpStatus.OK)
    public SuiviInventaireDTO readSuiviInventaire(@Parameter(name = "suiviInventaireId", description = "The suivi inventaire ID to read") @PathVariable Long suiviInventaireId) {
        return suiviInventaireService.read(suiviInventaireId);
    }

    @Operation(summary = "Delete suivi inventaire", description = "This endpoint deletes a suivi inventaire by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{suiviInventaireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSuiviInventaire(@Parameter(name = "suiviInventaireId", description = "The suivi inventaire ID to delete") @PathVariable Long suiviInventaireId) {
        suiviInventaireService.delete(suiviInventaireId);
    }

    @Operation(summary = "Read all suivi inventaires", description = "This endpoint retrieves all suivi inventaires with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allSuiviInventaires")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllSuiviInventaires(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = suiviInventaireService.readAllSuiviInventaires(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}