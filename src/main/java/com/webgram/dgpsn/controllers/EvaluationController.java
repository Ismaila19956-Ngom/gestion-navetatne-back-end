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
import com.webgram.dgpsn.models.EvaluationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.EvaluationService;

import java.util.Map;

@RestController
@RequestMapping("/evaluation")
@Tag(name = "evaluation", description = "Evaluation Management")
@RequiredArgsConstructor
public class EvaluationController {
    private final EvaluationService evaluationService;

    @Operation(summary = "Create evaluation", description = "This endpoint takes an evaluation input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationDTO createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        return evaluationService.create(evaluationDTO);
    }

    @Operation(summary = "Update evaluation", description = "This endpoint updates an existing evaluation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{evaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO updateEvaluation(@Parameter(name = "evaluationId", description = "The evaluation ID to update") @PathVariable Long evaluationId, @RequestBody EvaluationDTO evaluationDTO) {
        evaluationDTO.setId(evaluationId);
        return evaluationService.update(evaluationDTO);
    }

    @Operation(summary = "Read evaluation", description = "This endpoint retrieves an evaluation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{evaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationDTO readEvaluation(@Parameter(name = "evaluationId", description = "The evaluation ID to read") @PathVariable Long evaluationId) {
        return evaluationService.read(evaluationId);
    }

    @Operation(summary = "Delete evaluation", description = "This endpoint deletes an evaluation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{evaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvaluation(@Parameter(name = "evaluationId", description = "The evaluation ID to delete") @PathVariable Long evaluationId) {
        evaluationService.delete(evaluationId);
    }

    @Operation(summary = "Read all evaluations", description = "This endpoint retrieves all evaluations with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allEvaluations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllEvaluations(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = evaluationService.readAllEvaluations(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}