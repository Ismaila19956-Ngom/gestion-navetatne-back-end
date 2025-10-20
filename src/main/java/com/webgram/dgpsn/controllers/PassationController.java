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
import com.webgram.dgpsn.models.PassationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.PassationService;

import java.util.Map;

@RestController
@RequestMapping("/passation")
@Tag(name = "passation", description = "Passation Management")
@RequiredArgsConstructor
public class PassationController {
    private final PassationService passationService;

    @Operation(summary = "Create passation", description = "This endpoint takes a passation input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassationDTO createPassations(@RequestBody PassationDTO passationDTO) {
        return passationService.create(passationDTO);
    }

    @Operation(summary = "Update passation", description = "This endpoint updates an existing passation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{passationId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationDTO updatePassations(@Parameter(name = "passationId", description = "The passation ID to update") @PathVariable Long passationId, @RequestBody PassationDTO passationDTO) {
        passationDTO.setId(passationId);
        return passationService.update(passationDTO);
    }

    @Operation(summary = "Read passation", description = "This endpoint retrieves a passation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{passationId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationDTO readPassationss(@Parameter(name = "passationId", description = "The passation ID to read") @PathVariable Long passationId) {
        return passationService.read(passationId);
    }

    @Operation(summary = "Delete passation", description = "This endpoint deletes a passation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{passationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassations(@Parameter(name = "passationId", description = "The passation ID to delete") @PathVariable Long passationId) {
        passationService.delete(passationId);
    }

    @Operation(summary = "Read all passations", description = "This endpoint retrieves all passations with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allPassations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllPassations(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = passationService.readAllPassations(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}