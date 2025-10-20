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
import com.webgram.dgpsn.models.SocialConformiteDTO;
import com.webgram.dgpsn.services.SocialConformiteService;

@RestController
@RequestMapping("/social-conformites-reglementaire")
@Tag(name = "conformites sociaux", description = "Conformites sociaux controller")
@RequiredArgsConstructor
public class SocialConformiteController {
    private final SocialConformiteService socialConformiteService;

    @Operation(summary = "Creer un conformite social", description = "Ce endpoint prend un conformite social d’entrée d'un projet et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the conformite environment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SocialConformiteDTO addConformiteSocial(@RequestBody SocialConformiteDTO socialConformiteDTO) {
        return socialConformiteService.create(socialConformiteDTO);
    }

    @PutMapping("/{socialConformiteId}")
    @ResponseStatus(HttpStatus.OK)
    public SocialConformiteDTO updateConformiteSocial(@Parameter(name = "socialConformiteId", description = "the conformite social id updated") @PathVariable Long socialConformiteId, @RequestBody SocialConformiteDTO socialConformiteDTO) {
        socialConformiteDTO.setId(socialConformiteId);
        return socialConformiteService.update(socialConformiteDTO);
    }

    @Operation(summary = "delete the socialConformiteId", description = "Delete conformite social, it take input id conformite social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{socialConformiteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConformiteSocial(@Parameter(name = "socialConformiteId", description = "the conformite social id deleted") @PathVariable Long socialConformiteId) {
        socialConformiteService.delete(socialConformiteId);
    }

    @Operation(summary = "Read all conformite social", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SocialConformiteDTO> readAllConformiteSocialProject(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list conformite social") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list conformite social") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "source", description = "value of source used to filter list conformite social") @RequestParam(value = "source", required = false) String source,
            @Parameter(name = "etat", description = "value of etat used to filter list conformite social") @RequestParam(value = "etat", required = false) String etat,
            @Parameter(name = "startDate", description = "value of startDate used to filter list conformite social") @RequestParam(value = "startDate", required = false) String  startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list conformite social") @RequestParam(value = "endDate", required = false) String  endDate,
            @Parameter(name = "categorieId", description = "value of categorieId used to filter list conformite social") @RequestParam(value = "categorieId", required = false) Long categorieId,
            @Parameter(name = "typeReferenceId", description = "value of typeReferenceId used to filter list conformite social") @RequestParam(value = "typeReferenceId", required = false) Long typeReferenceId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list conformite social") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list conformite social") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list conformite social") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return socialConformiteService.readAll(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy, ascending);
    }
    
}
