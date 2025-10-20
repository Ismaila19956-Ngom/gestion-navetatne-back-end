package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.BudgetPassationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.BudgetPassationService;
import com.webgram.dgpsn.services.EngagementService;
import com.webgram.dgpsn.services.OrdonnancementService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/budgetPassation")
@Tag(name = "budgetPassation", description = "Budget Passation Management")
@RequiredArgsConstructor
public class BudgetPassationController {
    private final BudgetPassationService budgetPassationService;
    private final EngagementService engagementService;
    private final OrdonnancementService ordonnancementService;

    @Operation(summary = "Create budget passation", description = "This endpoint takes a budget passation input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetPassationDTO createBudgetPassation(@RequestBody BudgetPassationDTO budgetPassationDTO) {
        return budgetPassationService.create(budgetPassationDTO);
    }

    @Operation(summary = "Update budget passation", description = "This endpoint updates an existing budget passation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{budgetPassationId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPassationDTO updateBudgetPassation(@Parameter(name = "budgetPassationId", description = "The budget passation ID to update") @PathVariable Long budgetPassationId, @RequestBody BudgetPassationDTO budgetPassationDTO) {
        budgetPassationDTO.setId(budgetPassationId);
        return budgetPassationService.update(budgetPassationDTO);
    }

    @Operation(summary = "Read budget passation", description = "This endpoint retrieves a budget passation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{budgetPassationId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetPassationDTO readBudgetPassation(@Parameter(name = "budgetPassationId", description = "The budget passation ID to read") @PathVariable Long budgetPassationId) {
        return budgetPassationService.read(budgetPassationId);
    }

    @Operation(summary = "Delete budget passation", description = "This endpoint deletes a budget passation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{budgetPassationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetPassation(@Parameter(name = "budgetPassationId", description = "The budget passation ID to delete") @PathVariable Long budgetPassationId) {
        budgetPassationService.delete(budgetPassationId);
    }

    @Operation(summary = "Read all budget passations", description = "This endpoint retrieves all budget passations with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allBudgetPassations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllBudgetPassations(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = budgetPassationService.readAllBudgetPassations(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

//    @Operation(summary = "Get budget passation with engagements and ordonnancements and export to Excel",
//            description = "This endpoint retrieves a budget passation by ID along with its engagements and ordonnancements and returns an Excel file with two sheets")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "404", description = "Resource not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("/{budgetPassationId}/export")
//    public ResponseEntity<ByteArrayResource> getBudgetPassationWithEngagementsAndOrdonnancementsExcel(@Parameter(name = "budgetPassationId", description = "The budget passation ID to export") @PathVariable Long budgetPassationId) {
//        ByteArrayResource resource = budgetPassationService.exportBudgetPassationWithEngagementsAndOrdonnancements(budgetPassationId, engagementService, ordonnancementService);
//        HttpHeaders headers = new HttpHeaders();
//        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
//        headers.add("Content-Disposition", "attachment; filename=budget_passation_all_" + budgetPassationId + "_" + currentDate + ".xlsx");
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(resource.contentLength())
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(resource);
//    }

    @Operation(summary = "Export all budget passations with engagements and ordonnancements for a given year",
            description = "This endpoint retrieves all budget passations for a specific year along with their engagements and ordonnancements and returns an Excel file with two sheets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/export/year/{year}")
    public ResponseEntity<ByteArrayResource> getBudgetPassationsForYearWithEngagementsAndOrdonnancementsExcel(
            @Parameter(name = "year", description = "The year to export budget passations for") @PathVariable String year) {

        ByteArrayResource resource = budgetPassationService.exportAllBudgetPassationsForYearWithEngagementsAndOrdonnancements(year, engagementService, ordonnancementService);

        HttpHeaders headers = new HttpHeaders();
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        headers.add("Content-Disposition", "attachment; filename=budget_passations_" + year + "_" + currentDate + ".xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }
}