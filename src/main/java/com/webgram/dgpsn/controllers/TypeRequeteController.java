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
import com.webgram.dgpsn.models.TypeRequeteDTO;
import com.webgram.dgpsn.services.TypeRequeteService;

@RestController
@RequestMapping("/typeRequete")
@Tag(name = "Referentiel-controller", description = "Type Depense et Type Requete controller")
@RequiredArgsConstructor
public class TypeRequeteController {
    private final TypeRequeteService typeRequeteService;

    @Operation(summary = "Create requete", description = "this endpoint take input expense and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TypeRequeteDTO createTypeRequete(@RequestBody TypeRequeteDTO expense) {
        return typeRequeteService.create(expense);
    }

    @PutMapping("/{typeRequeteId}")
    @ResponseStatus(HttpStatus.OK)
    public TypeRequeteDTO updateTypeRequete(@Parameter(name = "typeRequeteId", description = "the typeRequete type id updated") @PathVariable Long typeRequeteId, @RequestBody TypeRequeteDTO typeRequeteDTO) {
        typeRequeteDTO.setId(typeRequeteId);
        return typeRequeteService.update(typeRequeteDTO);
    }

    @Operation(summary = "Read the typeRequete", description = "This endpoint is used to read indicator  it take input id typeRequete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{typeRequeteId}")
    @ResponseStatus(HttpStatus.OK)
    public TypeRequeteDTO readTypeRequete(@Parameter(name = "typeRequeteId", description = "the typeRequete id to read") @PathVariable Long typeRequeteId) {
        return typeRequeteService.read(typeRequeteId);
    }

    @Operation(summary = "delete the indicator", description = "Delete typeRequete, it take input   id typeRequete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{typeRequeteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeRequete(@Parameter(name = "typeRequeteId", description = "the typeRequete id deleted") @PathVariable Long typeRequeteId) {
       typeRequeteService.delete(typeRequeteId);
    }

    @Operation(summary = "Read all indicator", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TypeRequeteDTO> readAllITypeRequete(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list  categorieDepense") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list categorieDepense") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "categorieRequeteId,", description = "value of unit used to filter list categorieRequete") @RequestParam(value = "categorieRequeteId,", required = false) Long categorieRequeteId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list categorieDepense") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list categorieDepense") @RequestParam(value = "ascending", required = false) Boolean ascending

    ) {
        return typeRequeteService.readAll(pageable, code, libelle, categorieRequeteId,sortBy,ascending);
    }
}
