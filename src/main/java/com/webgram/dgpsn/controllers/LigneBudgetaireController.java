package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
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
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.services.LigneBudgetaireService;

import java.util.List;

@RestController
@RequestMapping("/ligne-budgetaire")
@Tag(name = "LigneBudgetaire :", description = "Endpoint pour gérer les lignes budgétaires associées à un budget DGPSN")
@RequiredArgsConstructor
public class LigneBudgetaireController {
    private final LigneBudgetaireService ligneBudgetaireService;

    @Operation(summary = "Create ligne budgetaire", description = "This endpoint takes input ligne budgetaire and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LigneBudgetaireDTO addLigneBudgetaire(@RequestBody LigneBudgetaireDTO ligneBudgetaireDTO) {
        return ligneBudgetaireService.create(ligneBudgetaireDTO);
    }

    @Operation(summary = "Create multiple lignes budgetaires", description = "This endpoint takes a list of lignes budgetaires and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<LigneBudgetaireDTO> addMultipleLigneBudgetaire(@RequestBody List<LigneBudgetaireDTO> ligneBudgetaireDTOs) {
        return ligneBudgetaireService.createMultiple(ligneBudgetaireDTOs);
    }

    @Operation(summary = "Update ligne budgetaire", description = "This endpoint updates an existing ligne budgetaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{ligneBudgetaireId}")
    @ResponseStatus(HttpStatus.OK)
    public LigneBudgetaireDTO updateLigneBudgetaire(
            @Parameter(name = "ligneBudgetaireId", description = "The ligne budgetaire id updated") @PathVariable Long ligneBudgetaireId,
            @RequestBody LigneBudgetaireDTO ligneBudgetaireDTO) {
        ligneBudgetaireDTO.setId(ligneBudgetaireId);
        return ligneBudgetaireService.update(ligneBudgetaireDTO);
    }

    @Operation(summary = "Delete ligne budgetaire", description = "Delete ligne budgetaire, it takes input id ligne budgetaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{ligneBudgetaireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLigneBudgetaire(@Parameter(name = "ligneBudgetaireId", description = "The ligne budgetaire id deleted") @PathVariable Long ligneBudgetaireId) {
        ligneBudgetaireService.delete(ligneBudgetaireId);
    }

    @Operation(summary = "Read all ligne budgetaire", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<LigneBudgetaireDTO> readAllLigneBudgetaire(
            Pageable pageable,
            @Parameter(name = "libelle", description = "Value of libelle used to filter list ligne budgetaire") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "rubriqueId", description = "Value of rubriqueId used to filter list ligne budgetaire") @RequestParam(value = "rubriqueId", required = false) Long rubriqueId,
            @Parameter(name = "montant", description = "Value of montant used to filter list ligne budgetaire") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "typeLigneBugetaire", description = "Value of typeLigneBugetaire used to filter list ligne budgetaire") @RequestParam(value = "typeLigneBugetaire", required = false) TypeLigneBugetaire typeLigneBugetaire,
            @Parameter(name = "commentaire", description = "Value of commentaire used to filter list ligne budgetaire") @RequestParam(value = "commentaire", required = false) String commentaire,
            @Parameter(name = "budgetId", description = "Value of budgetId used to filter list ligne budgetaire") @RequestParam(value = "budgetId", required = false) Long budgetId
    ) {
        return ligneBudgetaireService.readAll(pageable,  rubriqueId, montant, typeLigneBugetaire, commentaire, budgetId);
    }
}