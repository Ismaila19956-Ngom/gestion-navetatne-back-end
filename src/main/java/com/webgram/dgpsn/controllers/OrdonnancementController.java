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
import com.webgram.dgpsn.models.OrdonnancementDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.OrdonnancementService;

import java.util.Map;

@RestController
@RequestMapping("/ordonnancement")
@Tag(name = "ordonnancement", description = "Ordonnancement Management")
@RequiredArgsConstructor
public class OrdonnancementController {
    private final OrdonnancementService ordonnancementService;

    @Operation(summary = "Create ordonnancement", description = "This endpoint takes an ordonnancement input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdonnancementDTO createOrdonnancement(@RequestBody OrdonnancementDTO ordonnancementDTO) {
        return ordonnancementService.create(ordonnancementDTO);
    }

    @Operation(summary = "Update ordonnancement", description = "This endpoint updates an existing ordonnancement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{ordonnancementId}")
    @ResponseStatus(HttpStatus.OK)
    public OrdonnancementDTO updateOrdonnancement(@Parameter(name = "ordonnancementId", description = "The ordonnancement ID to update") @PathVariable Long ordonnancementId, @RequestBody OrdonnancementDTO ordonnancementDTO) {
        ordonnancementDTO.setId(ordonnancementId);
        return ordonnancementService.update(ordonnancementDTO);
    }

    @Operation(summary = "Read ordonnancement", description = "This endpoint retrieves an ordonnancement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{ordonnancementId}")
    @ResponseStatus(HttpStatus.OK)
    public OrdonnancementDTO readOrdonnancement(@Parameter(name = "ordonnancementId", description = "The ordonnancement ID to read") @PathVariable Long ordonnancementId) {
        return ordonnancementService.read(ordonnancementId);
    }

    @Operation(summary = "Delete ordonnancement", description = "This endpoint deletes an ordonnancement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{ordonnancementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdonnancement(@Parameter(name = "ordonnancementId", description = "The ordonnancement ID to delete") @PathVariable Long ordonnancementId) {
        ordonnancementService.delete(ordonnancementId);
    }

    @Operation(summary = "Read all ordonnancements", description = "This endpoint retrieves all ordonnancements with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allOrdonnancements")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllOrdonnancements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = ordonnancementService.readAllOrdonnancements(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}