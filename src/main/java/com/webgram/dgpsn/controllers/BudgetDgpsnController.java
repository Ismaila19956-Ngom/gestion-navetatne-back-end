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
import com.webgram.dgpsn.models.BudgetDgpsnDTO;
import com.webgram.dgpsn.services.BudgetDgpsnService;

@RestController
@RequestMapping("/budgetGlobal")
@Tag(name = "BudgetDgpsn :", description = "Endpoint pour gérer les budgets DGPSN dans programme, projet et activité")
@RequiredArgsConstructor
public class BudgetDgpsnController {
    private final BudgetDgpsnService budgetDgpsnService;

    @Operation(summary = "Create budget DGPSN", description = "This endpoint takes input budget DGPSN and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDgpsnDTO addBudget(@RequestBody BudgetDgpsnDTO budgetDgpsnDTO) {
        return budgetDgpsnService.create(budgetDgpsnDTO);
    }

    @Operation(summary = "Update budget DGPSN", description = "This endpoint updates an existing budget DGPSN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetDgpsnDTO updateBudget(
            @Parameter(name = "budgetId", description = "The budget DGPSN id updated") @PathVariable Long budgetId,
            @RequestBody BudgetDgpsnDTO budgetDgpsnDTO) {
        budgetDgpsnDTO.setId(budgetId);
        return budgetDgpsnService.update(budgetDgpsnDTO);
    }

    @Operation(summary = "Delete budget DGPSN", description = "Delete budget DGPSN, it takes input id budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{budgetDgpsnId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@Parameter(name = "budgetDgpsnId", description = "The budget DGPSN id deleted") @PathVariable Long budgetDgpsnId) {
        budgetDgpsnService.delete(budgetDgpsnId);
    }

    @Operation(summary = "Read all budget DGPSN", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<BudgetDgpsnDTO> readAllBudgetDgpsn(
            Pageable pageable,
            @Parameter(name = "code", description = "Value of code used to filter list budget") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "Value of libelle used to filter list budget") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "montant", description = "Value of montant used to filter list budget") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "annee", description = "Value of annee used to filter list budget") @RequestParam(value = "annee", required = false) Integer annee
    ) {
        return budgetDgpsnService.readAll(pageable, code, libelle, montant, annee);
    }
}