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
import com.webgram.dgpsn.models.GouvernanceConformiteDTO;
import com.webgram.dgpsn.services.GouvernanceConformiteService;

@RestController
@RequestMapping("/gouvernance-conformites-reglementaire")
@Tag(name = "conformites gouvernance", description = "Conformites gouvernance controller")
@RequiredArgsConstructor
public class GouvernanceConformiteController {
    private final GouvernanceConformiteService gouvernanceConformiteService;

    @Operation(summary = "Creer un conformite Gouvernance", description = "Ce endpoint prend un conformite Gouvernance d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the conformite environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GouvernanceConformiteDTO addConformiteGouvernance(@RequestBody GouvernanceConformiteDTO gouvernanceConformiteDTO) {
        return gouvernanceConformiteService.create(gouvernanceConformiteDTO);
    }

    @PutMapping("/{gouvernanceConformiteId}")
    @ResponseStatus(HttpStatus.OK)
    public GouvernanceConformiteDTO updateConformiteGouvernance(@Parameter(name = "gouvernanceConformiteId", description = "the conformite Gouvernance id updated") @PathVariable Long gouvernanceConformiteId, @RequestBody GouvernanceConformiteDTO gouvernanceConformiteDTO) {
        gouvernanceConformiteDTO.setId(gouvernanceConformiteId);
        return gouvernanceConformiteService.update(gouvernanceConformiteDTO);
    }

    @Operation(summary = "delete the gouvernanceConformiteId", description = "Delete conformite Gouvernance, it take input id conformite Gouvernance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{gouvernanceConformiteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConformiteGouvernance(@Parameter(name = "gouvernanceConformiteId", description = "the conformite Gouvernance id deleted") @PathVariable Long gouvernanceConformiteId) {
        gouvernanceConformiteService.delete(gouvernanceConformiteId);
    }

    @Operation(summary = "Read all conformite Gouvernance", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GouvernanceConformiteDTO> readAllConformiteGouvernanceProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list conformite Gouvernance") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list conformite Gouvernance") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "source", description = "value of source used to filter list conformite Gouvernance") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "etat", description = "value of etat used to filter list conformite Gouvernance") @RequestParam(value = "etat", required = false) String etat,
            @Parameter(name = "startDate", description = "value of startDate used to filter list conformite Gouvernance") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list conformite Gouvernance") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "categorieId", description = "value of categorieId used to filter list conformite Gouvernance") @RequestParam(value = "categorieId", required = false) Long categorieId,
            @Parameter(name = "typeReferenceId", description = "value of typeReferenceId used to filter list conformite Gouvernance") @RequestParam(value = "typeReferenceId", required = false) Long typeReferenceId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list conformite Gouvernance") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Gouvernance") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Gouvernance") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return gouvernanceConformiteService.readAll(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy, ascending);
    }
    
}
