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
import com.webgram.dgpsn.models.TypeExpenseDTO;
import com.webgram.dgpsn.services.TypeExpenseService;

@RestController
@RequestMapping("/typeExpense")
@Tag(name = "Referentiel-controller", description = "Type Depense et Type Requete controller")
@RequiredArgsConstructor
public class TypeExpenseController {
    private final TypeExpenseService typeExpenseService;

    @Operation(summary = "Create expense", description = "this endpoint take input expense and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TypeExpenseDTO createTypeExpense(@RequestBody TypeExpenseDTO expense) {
        return typeExpenseService.create(expense);
    }

    @PutMapping("/{typeExpenseId}")
    @ResponseStatus(HttpStatus.OK)
    public TypeExpenseDTO updateTypeExpense(@Parameter(name = "typeExpenseId", description = "the typeExpense type id updated") @PathVariable Long typeExpenseId, @RequestBody TypeExpenseDTO typeExpenseDTO) {
        typeExpenseDTO.setId(typeExpenseId);
        return typeExpenseService.update(typeExpenseDTO);
    }

    @Operation(summary = "Read the typeExpense", description = "This endpoint is used to read indicator  it take input id typeExpense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{typeExpenseId}")
    @ResponseStatus(HttpStatus.OK)
    public TypeExpenseDTO readTypeExpense(@Parameter(name = "typeExpenseId", description = "the typeExpense id to read") @PathVariable Long typeExpenseId) {
        return typeExpenseService.read(typeExpenseId);
    }

    @Operation(summary = "delete the indicator", description = "Delete typeExpense, it take input   id typeExpense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{typeExpenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeExpense(@Parameter(name = "typeExpenseId", description = "the typeExpense id deleted") @PathVariable Long typeExpenseId) {
       typeExpenseService.delete(typeExpenseId);
    }

    @Operation(summary = "Read all indicator", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TypeExpenseDTO> readAllITypeExpense(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list  categorieDepense") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list categorieDepense") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "categorieDepenseId,", description = "value of unit used to filter list categorieDepense") @RequestParam(value = "categorieDepenseId,", required = false) Long categorieDepenseId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list categorieDepense") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list categorieDepense") @RequestParam(value = "ascending", required = false) Boolean ascending

    ) {
        return typeExpenseService.readAll(pageable, code, libelle, categorieDepenseId,sortBy,ascending);
    }
}
