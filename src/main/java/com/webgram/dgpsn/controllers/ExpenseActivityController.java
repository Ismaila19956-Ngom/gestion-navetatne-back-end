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
import com.webgram.dgpsn.models.ExpenseActivityDTO;
import com.webgram.dgpsn.services.ExpenseActivityService;

import java.text.ParseException;

@RestController
@RequestMapping("/depense")
@Tag(name = "Depense-Activity-controller", description = "Depense Activity")
@RequiredArgsConstructor
public class ExpenseActivityController {
    private final ExpenseActivityService expenseActivityService;

    @Operation(summary = "Create ExpenseActivity", description = "this endpoint take input ExpenseActivity activity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the ExpenseActivity was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseActivityDTO expenseActivityAddToProject(@RequestBody ExpenseActivityDTO expenseActivityDTO) {
        return expenseActivityService.create(expenseActivityDTO);
    }

    @PutMapping("/{depenseId}")
    @ResponseStatus(HttpStatus.OK)
    public ExpenseActivityDTO updateExpenseActivity(@Parameter(name = "depenseId", description = "the depenseId id updated") @PathVariable Long depenseId, @RequestBody ExpenseActivityDTO expenseActivityDTO) {
        expenseActivityDTO.setId(depenseId);
        return expenseActivityService.update(expenseActivityDTO);
    }

    @Operation(summary = "delete the depenseId", description = "Delete depenseId, it take input id depenseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{depenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpenseActivity(@Parameter(name = "depenseId", description = "the depenseId id deleted") @PathVariable Long depenseId) {
        expenseActivityService.delete(depenseId);
    }

    @Operation(summary = "Read all ExpenseActivity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ExpenseActivityDTO> readAllExpenseActivityByProject(
            Pageable pageable,
            @Parameter(name = "date", description = "value of date used to filter list expense activity") @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "unitAmount", description = "value of unitAmount used to filter list expense activity") @RequestParam(value = "unitAmount", required = false) String unitAmount,
            @Parameter(name = "quantity", description = "value of quantity used to filter list expense activity") @RequestParam(value = "quantity", required = false) String quantity,
            @Parameter(name = "totalAmount", description = "value of totalAmount used to filter list expense activity") @RequestParam(value = "totalAmount", required = false) Double totalAmount,
            @Parameter(name = "projectId", description = "value of projectId used to filter list expense activity") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "categorieDepenseId", description = "value of categorieDepenseId used to filter list expense activity") @RequestParam(value = "categorieDepenseId", required = false) Long categorieDepenseId,
            @Parameter(name = "typeDepenseId", description = "value of typeDepenseId used to filter list expense activity") @RequestParam(value = "typeDepenseId", required = false) Long typeDepenseId

    ) throws ParseException {
        return expenseActivityService.readAll(pageable,
                date,
                unitAmount,
                quantity,
                totalAmount,
                categorieDepenseId,
                typeDepenseId,
                projectId);
    }

}
