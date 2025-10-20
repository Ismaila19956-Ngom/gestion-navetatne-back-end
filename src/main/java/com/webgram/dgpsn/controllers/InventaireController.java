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
import com.webgram.dgpsn.models.InventaireDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.InventaireService;

import java.util.Map;

@RestController
@RequestMapping("/inventaire")
@Tag(name = "inventaire", description = "Inventaire Management")
@RequiredArgsConstructor
public class InventaireController {
    private final InventaireService inventaireService;

    @Operation(summary = "Create inventaire", description = "This endpoint takes an inventaire input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventaireDTO createInventaire(@RequestBody InventaireDTO inventaireDTO) {
        return inventaireService.create(inventaireDTO);
    }

    @Operation(summary = "Update inventaire", description = "This endpoint updates an existing inventaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{inventaireId}")
    @ResponseStatus(HttpStatus.OK)
    public InventaireDTO updateInventaire(@Parameter(name = "inventaireId", description = "The inventaire ID to update") @PathVariable Long inventaireId, @RequestBody InventaireDTO inventaireDTO) {
        inventaireDTO.setId(inventaireId);
        return inventaireService.update(inventaireDTO);
    }

    @Operation(summary = "Read inventaire", description = "This endpoint retrieves an inventaire by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{inventaireId}")
    @ResponseStatus(HttpStatus.OK)
    public InventaireDTO readInventaire(@Parameter(name = "inventaireId", description = "The inventaire ID to read") @PathVariable Long inventaireId) {
        return inventaireService.read(inventaireId);
    }

    @Operation(summary = "Delete inventaire", description = "This endpoint deletes an inventaire by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{inventaireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventaire(@Parameter(name = "inventaireId", description = "The inventaire ID to delete") @PathVariable Long inventaireId) {
        inventaireService.delete(inventaireId);
    }

    @Operation(summary = "Read all inventaires", description = "This endpoint retrieves all inventaires with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allInventaires")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllInventaires(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = inventaireService.readAllInventaires(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}