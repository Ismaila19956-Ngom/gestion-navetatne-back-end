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
import com.webgram.dgpsn.entities.enums.SouceBudget;
import com.webgram.dgpsn.models.BudgetDTO;
import com.webgram.dgpsn.services.BudgetService;

import java.text.ParseException;

@RestController
@RequestMapping("/budget")
@Tag(name = "Budget-Financement :", description = "Enpoint pour gerer le budget de financement dans programme, projet et activite")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @Operation(summary = "Create budget", description = "this endpoint take input budget activity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the budget was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDTO AddBudget(@RequestBody BudgetDTO budgetDTO) {
        return budgetService.create(budgetDTO);
    }

    @PutMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetDTO updateBudget(@Parameter(name = "budgetId", description = "the budget id updated") @PathVariable Long budgetId, @RequestBody BudgetDTO budgetDTO) {
        budgetDTO.setId(budgetId);
        return budgetService.update(budgetDTO);
    }

    @Operation(summary = "delete the budgetId", description = "Delete budget, it take input id budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @DeleteMapping("/{budgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@Parameter(name = "budgetId", description = "the budget id deleted") @PathVariable Long budgetId) {
        budgetService.delete(budgetId);
    }

//    @GetMapping("/{budgetId}")
//    @ResponseStatus(HttpStatus.OK)
//    public AssignmentDTO readBudget(
//            @Parameter(name = "budgetId", description = "the budget id to read") @PathVariable Long budgetId
//    ) {
//        return budgetService.read(budgetId);
//    }

    @Operation(summary = "Read all budget", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<BudgetDTO> readAllBudgetByActivity(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list budget") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "souceBudget", description = "value of souceBudget used to filter list budget") @RequestParam(value = "souceBudget", required = false) SouceBudget souceBudget,
            @Parameter(name = "estimatedAmount", description = "value of estimatedAmount used to filter list budget") @RequestParam(value = "estimatedAmount", required = false) String  estimatedAmount,
            @Parameter(name = "actualAmount", description = "value of actualAmount used to filter list budget") @RequestParam(value = "actualAmount", required = false) String  actualAmount,
            @Parameter(name = "managementUnitId", description = "value of managementUnitId used to filter list budget") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId
    ) throws ParseException {
        return budgetService.readAll(pageable, libelle,souceBudget, estimatedAmount,actualAmount, managementUnitId);
    }

    @Operation(summary = "Read total funding", description = "It return total funding")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalBudget")
    public Long readTotalBudget() {
        return budgetService.totalFunding();
    }

}
