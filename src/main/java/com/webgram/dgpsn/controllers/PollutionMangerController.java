package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.PollutionManagerDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.PollutionManagerService;

import java.util.Map;

@RestController
@RequestMapping("/pollution-manager")
@Tag(name = "manager-pollution", description = "pollution manager")
@RequiredArgsConstructor
public class PollutionMangerController {
    private final PollutionManagerService pollutionManagerService;

    @Operation(summary = "Create manager", description = "this endpoint take input actor project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type actor project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PollutionManagerDTO createPollutionManager(@RequestBody PollutionManagerDTO pollutionManagerDTO) {
        return pollutionManagerService.create(pollutionManagerDTO);
    }

    @PutMapping("/{pollutionId}")
    @ResponseStatus(HttpStatus.OK)
    public PollutionManagerDTO updatePollutionManager(@Parameter(name = "pollutionId", description = "the pollutionId updated") @PathVariable Long pollutionId, @RequestBody PollutionManagerDTO PollutionManagerDTO) {
        PollutionManagerDTO.setId(pollutionId);
        return pollutionManagerService.update(PollutionManagerDTO);
    }

    @Operation(summary = "Read the PollutionManager", description = "This endpoint is used to read partner project it take input id actor project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{pollutionId}")
    @ResponseStatus(HttpStatus.OK)
    public PollutionManagerDTO readPollutionManager(@Parameter(name = "pollutionId", description = "the type rejet id to read") @PathVariable Long pollutionId) {
        return pollutionManagerService.read(pollutionId);
    }

    @Operation(summary = "delete the pollutionId", description = "Delete pollutionId, it take input id PollutionManager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{pollutionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePollutionManager(@Parameter(name = "pollutionId", description = "the PollutionManagerId id deleted") @PathVariable Long pollutionId) {
        pollutionManagerService.delete(pollutionId);
    }

    @Operation(summary = "Read all PollutionManager", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllPollutionManager(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = pollutionManagerService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Mettre à jour le statut d'un solde journalier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statut mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "404", description = "Solde journalier non trouvé")
    })
    public ResponseEntity<PollutionManagerDTO> updateStatutManagement(
            @PathVariable Long id,
            @RequestParam StatutType statutType) {
        return ResponseEntity.ok(pollutionManagerService.updateStatut(id, statutType));
    }

}
