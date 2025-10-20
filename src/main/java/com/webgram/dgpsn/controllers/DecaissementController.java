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
import com.webgram.dgpsn.models.DecaissementDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.DecaissementService;

import java.util.Map;

@RestController
@RequestMapping("/decaissement")
@Tag(name = "decaissement", description = "Decaissement Management")
@RequiredArgsConstructor
public class DecaissementController {
    private final DecaissementService decaissementService;

    @Operation(summary = "Create decaissement", description = "This endpoint takes a decaissement input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DecaissementDTO createDecaissement(@RequestBody DecaissementDTO decaissementDTO) {
        return decaissementService.create(decaissementDTO);
    }

    @Operation(summary = "Update decaissement", description = "This endpoint updates an existing decaissement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{decaissementId}")
    @ResponseStatus(HttpStatus.OK)
    public DecaissementDTO updateDecaissement(@Parameter(name = "decaissementId", description = "The decaissement ID to update") @PathVariable Long decaissementId, @RequestBody DecaissementDTO decaissementDTO) {
        decaissementDTO.setId(decaissementId);
        return decaissementService.update(decaissementDTO);
    }

    @Operation(summary = "Read decaissement", description = "This endpoint retrieves a decaissement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{decaissementId}")
    @ResponseStatus(HttpStatus.OK)
    public DecaissementDTO readDecaissement(@Parameter(name = "decaissementId", description = "The decaissement ID to read") @PathVariable Long decaissementId) {
        return decaissementService.read(decaissementId);
    }

    @Operation(summary = "Delete decaissement", description = "This endpoint deletes a decaissement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{decaissementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDecaissement(@Parameter(name = "decaissementId", description = "The decaissement ID to delete") @PathVariable Long decaissementId) {
        decaissementService.delete(decaissementId);
    }

    @Operation(summary = "Read all decaissements", description = "This endpoint retrieves all decaissements with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allDecaissements")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllDecaissements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = decaissementService.readAllDecaissements(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}