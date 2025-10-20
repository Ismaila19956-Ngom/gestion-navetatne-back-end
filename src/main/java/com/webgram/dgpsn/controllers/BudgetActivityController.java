package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.BudgetActivityDTO;
import com.webgram.dgpsn.services.BudgetActivityService;

import java.text.ParseException;

@RestController
@RequestMapping("/budgetActivity")
@Tag(name = "budget-Activity-controller", description = "budget Activity")
@RequiredArgsConstructor
public class BudgetActivityController {
    private final BudgetActivityService budgetActivityService;

    @Operation(summary = "Create budgetActivity", description = "this endpoint take input budgetActivity project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the milestone was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetActivityDTO budgetActivityAddToProject(@RequestBody BudgetActivityDTO budgetActivityDTO) {
        return budgetActivityService.create(budgetActivityDTO);
    }

    @PutMapping("/{budgetActivityId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetActivityDTO updateBudgetActivity(@Parameter(name = "budgetActivityId", description = "the budgetActivity id updated") @PathVariable Long budgetActivityId, @RequestBody BudgetActivityDTO budgetActivityDTO) {
        budgetActivityDTO.setId(budgetActivityId);
        return budgetActivityService.update(budgetActivityDTO);
    }

    @Operation(summary = "delete the budgetActivityId", description = "Delete budgetActivity, it take input id budgetActivity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{budgetActivityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetActivity(@Parameter(name = "budgetActivityId", description = "the budgetActivity id deleted") @PathVariable Long budgetActivityId) {
        budgetActivityService.delete(budgetActivityId);
    }

    @Operation(summary = "Read all milestone", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<BudgetActivityDTO> readAllBudgetActivityByProject(
            Pageable pageable,
//            @Parameter(name = "allocation", description = "value of allocation used to filter list budget activity") @RequestParam(value = "allocation", required = false)  String allocation,
            @Parameter(name = "year", description = "value of year used to filter list budget activity") @RequestParam(value = "year", required = false)  Integer year,
            @Parameter(name = "amount", description = "value of amount used to filter list budget activity") @RequestParam(value = "amount", required = false) Double amount,
            @Parameter(name = "sortBy", description = "value of allocation used to filter list sortBy activity") @RequestParam(value = "sortBy", required = false)  String sortBy,
            @Parameter(name = "ascending", description = "value of amount used to filter list ascending activity") @RequestParam(value = "ascending", required = false) Boolean ascending,
            @Parameter(name = "projectId", description = "value of projectId used to filter list budget activity") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "typeBudgetId", description = "value of typeBudgetId used to filter list budget activity") @RequestParam(value = "typeBudgetId", required = false) Long typeBudgetId

    ) throws ParseException {
        return budgetActivityService.readAll(pageable, typeBudgetId, year, amount,sortBy,
                ascending, projectId);
    }

}
