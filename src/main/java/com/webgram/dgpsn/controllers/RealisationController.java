package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.services.RealisationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/realisation")
@Tag(name = "Realisation :", description = "Endpoint pour gérer les réalisations associées à une ligne budgétaire")
@RequiredArgsConstructor
public class RealisationController {
    private final RealisationService realisationService;

    @Operation(summary = "Create realisation", description = "This endpoint takes input realisation and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealisationDTO addRealisation(@RequestBody RealisationDTO realisationDTO) {
        return realisationService.create(realisationDTO);
    }

    @Operation(summary = "Create multiple realisations", description = "This endpoint takes a list of realisations and saves them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<RealisationDTO> addMultipleRealisation(@RequestBody List<RealisationDTO> realisationDTOs) {
        return realisationService.createMultiple(realisationDTOs);
    }

    @Operation(summary = "Update realisation", description = "This endpoint updates an existing realisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{realisationId}")
    @ResponseStatus(HttpStatus.OK)
    public RealisationDTO updateRealisation(
            @Parameter(name = "realisationId", description = "The realisation id updated") @PathVariable Long realisationId,
            @RequestBody RealisationDTO realisationDTO) {
        realisationDTO.setId(realisationId);
        return realisationService.update(realisationDTO);
    }

    @Operation(summary = "Delete realisation", description = "Delete realisation, it takes input id realisation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{realisationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRealisation(@Parameter(name = "realisationId", description = "The realisation id deleted") @PathVariable Long realisationId) {
        realisationService.delete(realisationId);
    }

    @Operation(summary = "Read all realisations", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RealisationDTO> readAllRealisation(
            Pageable pageable,
            @Parameter(name = "code", description = "Value of code used to filter list realisation") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "montant", description = "Value of montant used to filter list realisation") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "date", description = "Value of date used to filter list realisation") @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(name = "fournisseur", description = "Value of fournisseur used to filter list realisation") @RequestParam(value = "fournisseur", required = false) String fournisseur,
            @Parameter(name = "numeroBon", description = "Value of numeroBon used to filter list realisation") @RequestParam(value = "numeroBon", required = false) String numeroBon,
            @Parameter(name = "numeroBE", description = "Value of numeroBE used to filter list realisation") @RequestParam(value = "numeroBE", required = false) String numeroBE,
            @Parameter(name = "numeroMandat", description = "Value of numeroMandat used to filter list realisation") @RequestParam(value = "numeroMandat", required = false) String numeroMandat,
            @Parameter(name = "description", description = "Value of description used to filter list realisation") @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "ligneBudgetaireId", description = "Value of ligneBudgetaireId used to filter list realisation") @RequestParam(value = "ligneBudgetaireId", required = false) Long ligneBudgetaireId,
            @Parameter(name = "realisationsId", description = "Value of realisationsId used to filter list realisation") @RequestParam(value = "realisationsId", required = false) Long realisationsId
    ) {
        return realisationService.readAll(pageable, code, realisationsId, montant, date, fournisseur, numeroBon, numeroBE, numeroMandat, description, ligneBudgetaireId);
    }
}