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
import com.webgram.dgpsn.models.GouvernanceIndicateurDTO;
import com.webgram.dgpsn.services.GouvernanceIndicateurService;

@RestController
@RequestMapping("/gouvernance-indicateur")
@Tag(name = "indicateurs gouvernance", description = "Indicateurs gouvernance controller")
@RequiredArgsConstructor
public class GouvernanceIndicateurController {
    private final GouvernanceIndicateurService gouvernanceIndicateurService;

    @Operation(summary = "Creer un indicateur gouvernance", description = "Ce endpoint prend un indicateur gouvernance d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the indicateur environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GouvernanceIndicateurDTO addIndicateurGouvernance(@RequestBody GouvernanceIndicateurDTO gouvernanceIndicateurDTO) {
        return gouvernanceIndicateurService.create(gouvernanceIndicateurDTO);
    }

    @PutMapping("/{gouvernanceIndicateurId}")
    @ResponseStatus(HttpStatus.OK)
    public GouvernanceIndicateurDTO updateIndicateurGouvernance(@Parameter(name = "gouvernanceIndicateurId", description = "the indicateur gouvernance id updated") @PathVariable Long gouvernanceIndicateurId, @RequestBody GouvernanceIndicateurDTO gouvernanceIndicateurDTO) {
        gouvernanceIndicateurDTO.setId(gouvernanceIndicateurId);
        return gouvernanceIndicateurService.update(gouvernanceIndicateurDTO);
    }

    @Operation(summary = "delete the gouvernanceIndicateurId", description = "Delete indicateur gouvernance, it take input id indicateur gouvernance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{gouvernanceIndicateurId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndicateurGouvernance(@Parameter(name = "gouvernanceIndicateurId", description = "the indicateur gouvernance id deleted") @PathVariable Long gouvernanceIndicateurId) {
        gouvernanceIndicateurService.delete(gouvernanceIndicateurId);
    }

    @Operation(summary = "Read all indicateur gouvernance", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GouvernanceIndicateurDTO> readAllIndicateurGouvernanceProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list indicateur gouvernance") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list indicateur gouvernance") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "valeur", description = "value of valeur used to filter list indicateur gouvernance") @RequestParam(value = "valeur", required = false) String valeur,
            @Parameter(name = "source", description = "value of source used to filter list indicateur gouvernance") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "startDate", description = "value of startDate used to filter list indicateur gouvernance") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list indicateur gouvernance") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "indicateurId", description = "value of indicateurId used to filter list indicateur gouvernance") @RequestParam(value = "indicateurId", required = false) Long indicateurId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list indicateur gouvernance") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return gouvernanceIndicateurService.readAll(pageable, code, libelle, valeur, source, startDate, endDate, indicateurId, projetId,sortBy, ascending);
    }
    
}
