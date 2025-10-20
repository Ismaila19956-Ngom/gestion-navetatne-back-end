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
import com.webgram.dgpsn.models.EngagementDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.EngagementService;

import java.util.Map;

@RestController
@RequestMapping("/engagement")
@Tag(name = "engagement", description = "Engagement Management")
@RequiredArgsConstructor
public class EngagementController {
    private final EngagementService engagementService;

    @Operation(summary = "Create engagement", description = "This endpoint takes an engagement input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EngagementDTO createEngagement(@RequestBody EngagementDTO engagementDTO) {
        return engagementService.create(engagementDTO);
    }

    @Operation(summary = "Update engagement", description = "This endpoint updates an existing engagement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{engagementId}")
    @ResponseStatus(HttpStatus.OK)
    public EngagementDTO updateEngagement(@Parameter(name = "engagementId", description = "The engagement ID to update") @PathVariable Long engagementId, @RequestBody EngagementDTO engagementDTO) {
        engagementDTO.setId(engagementId);
        return engagementService.update(engagementDTO);
    }

    @Operation(summary = "Read engagement", description = "This endpoint retrieves an engagement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{engagementId}")
    @ResponseStatus(HttpStatus.OK)
    public EngagementDTO readEngagement(@Parameter(name = "engagementId", description = "The engagement ID to read") @PathVariable Long engagementId) {
        return engagementService.read(engagementId);
    }

    @Operation(summary = "Delete engagement", description = "This endpoint deletes an engagement by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{engagementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEngagement(@Parameter(name = "engagementId", description = "The engagement ID to delete") @PathVariable Long engagementId) {
        engagementService.delete(engagementId);
    }

    @Operation(summary = "Read all engagements", description = "This endpoint retrieves all engagements with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allEngagements")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllEngagements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = engagementService.readAllEngagements(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}