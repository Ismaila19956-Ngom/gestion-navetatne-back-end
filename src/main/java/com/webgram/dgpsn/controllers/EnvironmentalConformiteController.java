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
import com.webgram.dgpsn.models.EnvironmentalConformiteDTO;
import com.webgram.dgpsn.services.EnvironmentalConformiteService;

@RestController
@RequestMapping("/environmental-conformites-reglementaire")
@Tag(name = "conformites environnementaux", description = "Conformites environnementaux controller")
@RequiredArgsConstructor
public class EnvironmentalConformiteController {
    private final EnvironmentalConformiteService environmentalConformiteService;

    @Operation(summary = "Creer un conformite environnemental", description = "Ce endpoint prend un conformite environnemental d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the conformite environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnvironmentalConformiteDTO addConformiteEnvironmental(@RequestBody EnvironmentalConformiteDTO environmentalConformiteDTO) {
        return environmentalConformiteService.create(environmentalConformiteDTO);
    }

    @PutMapping("/{environmentalConformiteId}")
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalConformiteDTO updateConformiteEnvironnemental(@Parameter(name = "environmentalConformiteId", description = "the conformite environnemental id updated") @PathVariable Long environmentalConformiteId, @RequestBody EnvironmentalConformiteDTO environmentalConformiteDTO) {
        environmentalConformiteDTO.setId(environmentalConformiteId);
        return environmentalConformiteService.update(environmentalConformiteDTO);
    }

    @Operation(summary = "delete the environmentalConformiteId", description = "Delete conformite environnemental, it take input id conformite environnemental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{environmentalConformiteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConformiteEnvironnemental(@Parameter(name = "environmentalConformiteId", description = "the conformite environnemental id deleted") @PathVariable Long environmentalConformiteId) {
        environmentalConformiteService.delete(environmentalConformiteId);
    }

    @Operation(summary = "Read all conformite environnemental", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<EnvironmentalConformiteDTO> readAllConformiteEnvironmentalProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list conformite environnemental") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list conformite environnemental") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "source", description = "value of source used to filter list conformite environnemental") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "etat", description = "value of etat used to filter list conformite environnemental") @RequestParam(value = "etat", required = false) String etat,
            @Parameter(name = "startDate", description = "value of startDate used to filter list conformite environnemental") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list conformite environnemental") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "categorieId", description = "value of categorieId used to filter list conformite environnemental") @RequestParam(value = "categorieId", required = false) Long categorieId,
            @Parameter(name = "typeReferenceId", description = "value of typeReferenceId used to filter list conformite environnemental") @RequestParam(value = "typeReferenceId", required = false) Long typeReferenceId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list conformite environnemental") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return environmentalConformiteService.readAll(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy, ascending);
    }
    
}
