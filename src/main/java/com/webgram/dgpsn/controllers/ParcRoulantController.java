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
import com.webgram.dgpsn.models.ParcRoulantDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.ParcRoulantService;

import java.util.Map;

@RestController
@RequestMapping("/parcRoulant")
@Tag(name = "parcRoulant", description = "Parc Roulant Management")
@RequiredArgsConstructor
public class ParcRoulantController {
    private final ParcRoulantService parcRoulantService;

    @Operation(summary = "Create parc roulant", description = "This endpoint takes a parc roulant input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcRoulantDTO createParcRoulant(@RequestBody ParcRoulantDTO parcRoulantDTO) {
        return parcRoulantService.create(parcRoulantDTO);
    }


    @Operation(summary = "Update parc roulant", description = "This endpoint updates an existing parc roulant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{parcRoulantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParcRoulantDTO updateParcRoulant(@Parameter(name = "parcRoulantId", description = "The parc roulant ID to update") @PathVariable Long parcRoulantId, @RequestBody ParcRoulantDTO parcRoulantDTO) {
        parcRoulantDTO.setId(parcRoulantId);
        return parcRoulantService.update(parcRoulantDTO);
    }

    @Operation(summary = "Read parc roulant", description = "This endpoint retrieves a parc roulant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{parcRoulantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParcRoulantDTO readParcRoulant(@Parameter(name = "parcRoulantId", description = "The parc roulant ID to read") @PathVariable Long parcRoulantId) {
        return parcRoulantService.read(parcRoulantId);
    }

    @Operation(summary = "Delete parc roulant", description = "This endpoint deletes a parc roulant by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{parcRoulantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParcRoulant(@Parameter(name = "parcRoulantId", description = "The parc roulant ID to delete") @PathVariable Long parcRoulantId) {
        parcRoulantService.delete(parcRoulantId);
    }

    @Operation(summary = "Read all parc roulants", description = "This endpoint retrieves all parc roulants with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allParcRoulants")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllParcRoulants(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = parcRoulantService.readAllParcRoulants(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}