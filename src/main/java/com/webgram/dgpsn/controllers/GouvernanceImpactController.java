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
import com.webgram.dgpsn.models.GouvernanceImpactDTO;
import com.webgram.dgpsn.services.GouvernanceImpactService;

@RestController
@RequestMapping("/gouvernance-impact")
@Tag(name = "impacts-gouvernance", description = "Impacts gouvernance controller")
@RequiredArgsConstructor
public class GouvernanceImpactController {
    private final GouvernanceImpactService gouvernanceImpactService;

    @Operation(summary = "Creer un impact gouvernance", description = "Ce endpoint prend un impact de gouvernance d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the impact environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GouvernanceImpactDTO addImpactGouvernance(@RequestBody GouvernanceImpactDTO gouvernanceImpactDTO) {
        return gouvernanceImpactService.create(gouvernanceImpactDTO);
    }

    @PutMapping("/{gouvernanceImpactId}")
    @ResponseStatus(HttpStatus.OK)
    public GouvernanceImpactDTO updateImpactGouvernance(@Parameter(name = "gouvernanceImpactId", description = "the impact gouvernance id updated") @PathVariable Long gouvernanceImpactId, @RequestBody GouvernanceImpactDTO gouvernanceImpactDTO) {
        gouvernanceImpactDTO.setId(gouvernanceImpactId);
        return gouvernanceImpactService.update(gouvernanceImpactDTO);
    }

    @Operation(summary = "delete the gouvernanceImpactId", description = "Delete impact gouvernance, it take input id impact gouvernance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{gouvernanceImpactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImpactGouvernance(@Parameter(name = "gouvernanceImpactId", description = "the impact gouvernance id deleted") @PathVariable Long gouvernanceImpactId) {
        gouvernanceImpactService.delete(gouvernanceImpactId);
    }

    @Operation(summary = "Read all impact gouvernance", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GouvernanceImpactDTO> readAllImpactGouvernanceProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list impact gouvernance") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list impact gouvernance") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "source", description = "value of source used to filter list impact gouvernance") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "natureImpact", description = "value of natureImpact used to filter list impact gouvernance") @RequestParam(value = "natureImpact", required = false) String natureImpact,
            @Parameter(name = "importanceImpact", description = "value of importanceImpact used to filter list impact gouvernance") @RequestParam(value = "importanceImpact", required = false) String importanceImpact,
            @Parameter(name = "startDate", description = "value of startDate used to filter list impact gouvernance") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list impact gouvernance") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "categorieId", description = "value of categorieId used to filter list impact gouvernance") @RequestParam(value = "categorieId", required = false) Long categorieId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list impact gouvernance") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return gouvernanceImpactService.readAll(pageable, code,  libelle, source, natureImpact, importanceImpact, startDate, endDate, categorieId, projetId,sortBy, ascending);
    }
    
}
