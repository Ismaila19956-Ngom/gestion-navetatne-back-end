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
import com.webgram.dgpsn.models.SocialImpactDTO;
import com.webgram.dgpsn.services.SocialImpactService;

@RestController
@RequestMapping("/social-impact")
@Tag(name = "impacts sociaux", description = "Impacts sociaux controller")
@RequiredArgsConstructor
public class SocialImpactController {
    private final SocialImpactService socialImpactService;

    @Operation(summary = "Creer un impact social", description = "Ce endpoint prend un impact social d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the impact environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SocialImpactDTO addImpactSocial(@RequestBody SocialImpactDTO socialImpactDTO) {
        return socialImpactService.create(socialImpactDTO);
    }

    @PutMapping("/{socialImpactId}")
    @ResponseStatus(HttpStatus.OK)
    public SocialImpactDTO updateImpactSocial(@Parameter(name = "socialImpactId", description = "the impact social id updated") @PathVariable Long socialImpactId, @RequestBody SocialImpactDTO socialImpactDTO) {
        socialImpactDTO.setId(socialImpactId);
        return socialImpactService.update(socialImpactDTO);
    }

    @Operation(summary = "delete the socialImpactId", description = "Delete impact social, it take input id impact social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{socialImpactId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImpactSocial(@Parameter(name = "socialImpactId", description = "the impact social id deleted") @PathVariable Long socialImpactId) {
        socialImpactService.delete(socialImpactId);
    }

    @Operation(summary = "Read all impact social", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SocialImpactDTO> readAllImpactSocialProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list impact social") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list impact social") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "nbPersonnesAffectees", description = "value of nbPersonnesAffectees used to filter list impact social") @RequestParam(value = "nbPersonnesAffectees", required = false) Number nbPersonnesAffectees,
            @Parameter(name = "nbMenagesAffectees", description = "value of nbMenagesAffectees used to filter list impact social") @RequestParam(value = "nbMenagesAffectees", required = false) Number nbMenagesAffectees,
            @Parameter(name = "source", description = "value of source used to filter list impact social") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "natureImpact", description = "value of natureImpact used to filter list impact social") @RequestParam(value = "natureImpact", required = false) String natureImpact,
            @Parameter(name = "importanceImpact", description = "value of importanceImpact used to filter list impact social") @RequestParam(value = "importanceImpact", required = false) String importanceImpact,
            @Parameter(name = "startDate", description = "value of startDate used to filter list impact social") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list impact social") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "categorieId", description = "value of categorieId used to filter list impact social") @RequestParam(value = "categorieId", required = false) Long categorieId,
            @Parameter(name = "typeImpactId", description = "value of typeImpactId used to filter list impact social") @RequestParam(value = "typeImpactId", required = false) Long typeImpactId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list impact social") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return socialImpactService.readAll(pageable, code,  libelle, nbPersonnesAffectees, nbMenagesAffectees, source, natureImpact, importanceImpact, startDate, endDate, categorieId, typeImpactId, projetId,sortBy, ascending);
    }
    
}
