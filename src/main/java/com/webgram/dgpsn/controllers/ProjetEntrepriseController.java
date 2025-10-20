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
import com.webgram.dgpsn.models.ProjetEntrepriseDTO;
import com.webgram.dgpsn.services.ProjetEntrepriseService;

@RestController
@RequestMapping("/project-entreprise")
@Tag(name = "project-entreprise", description = "project de l'entreprise")
@RequiredArgsConstructor
public class ProjetEntrepriseController {
    private final ProjetEntrepriseService projetEntrepriseService;

    @Operation(summary = "Create project entreprise", description = "this endpoint take input project entreprise and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type project entreprise was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetEntrepriseDTO createProjetByEntreprise(@RequestBody ProjetEntrepriseDTO projetEntrepriseDTO) {
        return projetEntrepriseService.create(projetEntrepriseDTO);
    }

    @PutMapping("/{projetEntrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjetEntrepriseDTO updateProjetByEntreprise(@Parameter(name = "projetEntrepriseId", description = "the projetEntrepriseId updated") @PathVariable Long projetEntrepriseId, @RequestBody ProjetEntrepriseDTO projetEntreprise) {
        projetEntreprise.setId(projetEntrepriseId);
        return projetEntrepriseService.update(projetEntreprise);
    }

    @Operation(summary = "Read the projectEntreprise", description = "This endpoint is used to read partner project it take input id  project Entreprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{projectEntrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjetEntrepriseDTO readProjectByEntreprise(@Parameter(name = "projectEntrepriseId", description = "the type projectEntreprise id to read") @PathVariable Long projectEntrepriseId) {
        return projetEntrepriseService.read(projectEntrepriseId);
    }

    @Operation(summary = "delete the actorProjectId", description = "Delete actorProjectId, it take input id actorProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{projectEntrepriseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectByEntrepriseId(@Parameter(name = "projectEntrepriseId", description = "the projectEntreprise id deleted") @PathVariable Long projectEntrepriseId) {
        projetEntrepriseService.delete(projectEntrepriseId);
    }

    @Operation(summary = "Read all projectEntrepriseId", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProjetEntrepriseDTO> readAllProjectByEntrepriseId(
            Pageable pageable,
            @Parameter(name = "projectId", description = "value of projectId used to filter list ProjetEntrprise") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "entrepriseId", description = "value of entrepriseId used to filter list ProjetEntrprise") @RequestParam(value = "entrepriseId", required = false) Long entrepriseId,
            @Parameter(name = "roleEntrepriseId", description = "value of roleEntrepriseId used to filter list ProjetEntrprise") @RequestParam(value = "roleEntrepriseId", required = false) Long roleEntrepriseId,
            @Parameter(name = "flagId", description = "value of flagId used to filter list ProjetEntrprise") @RequestParam(value = "flagId", required = false) Long flagId
    ) {
        return projetEntrepriseService.readAll(pageable, projectId, entrepriseId, roleEntrepriseId,flagId);
    }

}
