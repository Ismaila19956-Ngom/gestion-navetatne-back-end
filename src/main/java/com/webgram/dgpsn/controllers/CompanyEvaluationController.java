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
import com.webgram.dgpsn.models.CompanyEvaluationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CompanyEvaluationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/companyEvaluation")
@Tag(name = "companyEvaluation", description = "Company Evaluation Management")
@RequiredArgsConstructor
public class CompanyEvaluationController {
    private final CompanyEvaluationService companyEvaluationService;

    @Operation(summary = "Create company evaluation", description = "This endpoint takes a company evaluation input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyEvaluationDTO createCompanyEvaluation(@RequestBody CompanyEvaluationDTO companyEvaluationDTO) {
        return companyEvaluationService.create(companyEvaluationDTO);
    }

    @Operation(summary = "Create multiple company evaluations", description = "This endpoint takes a list of company evaluations and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CompanyEvaluationDTO> createMultipleCompanyEvaluations(@RequestBody List<CompanyEvaluationDTO> companyEvaluationDTOs) {
        return companyEvaluationService.createMultiple(companyEvaluationDTOs);
    }

    @Operation(summary = "Update company evaluation", description = "This endpoint updates an existing company evaluation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

@PutMapping("/{companyEvaluationId}")
@ResponseStatus(HttpStatus.OK)
public CompanyEvaluationDTO updateAgent(@Parameter(name = "companyEvaluationId", description = "the compagny id to updated") @PathVariable("companyEvaluationId") Long companyEvaluationId, @RequestBody CompanyEvaluationDTO companyEvaluationDTO) {
    companyEvaluationDTO.setId(companyEvaluationId);
    return companyEvaluationService.update(companyEvaluationDTO);
}

    @Operation(summary = "Read company evaluation", description = "This endpoint retrieves a company evaluation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{companyEvaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyEvaluationDTO readCompanyEvaluation(
            @Parameter(name = "companyEvaluationId", description = "The company evaluation ID to read") @PathVariable Long companyEvaluationId) {
        return companyEvaluationService.read(companyEvaluationId);
    }

    @Operation(summary = "Delete company evaluation", description = "This endpoint deletes a company evaluation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{companyEvaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyEvaluation(
            @Parameter(name = "companyEvaluationId", description = "The company evaluation ID to delete") @PathVariable Long companyEvaluationId) {
        companyEvaluationService.delete(companyEvaluationId);
    }

    @Operation(summary = "Read all company evaluations", description = "This endpoint retrieves all company evaluations with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allCompanyEvaluations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllCompanyEvaluations(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = companyEvaluationService.readAllCompanyEvaluations(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}