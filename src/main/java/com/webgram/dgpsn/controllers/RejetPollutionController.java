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
import com.webgram.dgpsn.models.RejetPollutionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.RejetPollutionService;

import java.util.Map;

@RestController
@RequestMapping("/rejet-pollution")
@Tag(name = "rejet-pollution", description = "pollution rejete des eaux")
@RequiredArgsConstructor
public class RejetPollutionController {
    private final RejetPollutionService rejetPollutionService;

    @Operation(summary = "Create actor project", description = "this endpoint take input actor project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type actor project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RejetPollutionDTO createRejetPollution(@RequestBody RejetPollutionDTO rejetPollution) {
        return rejetPollutionService.create(rejetPollution);
    }

    @PutMapping("/{rejetId}")
    @ResponseStatus(HttpStatus.OK)
    public RejetPollutionDTO updateRejetPollution(@Parameter(name = "rejetId", description = "the rejetId updated") @PathVariable Long rejetId, @RequestBody RejetPollutionDTO rejetPollutionDTO) {
        rejetPollutionDTO.setId(rejetId);
        return rejetPollutionService.update(rejetPollutionDTO);
    }

    @Operation(summary = "Read the rejetPollution", description = "This endpoint is used to read partner project it take input id actor project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{rejetId}")
    @ResponseStatus(HttpStatus.OK)
    public RejetPollutionDTO readRejetPollution(@Parameter(name = "rejetId", description = "the type rejet id to read") @PathVariable Long rejetId) {
        return rejetPollutionService.read(rejetId);
    }

    @Operation(summary = "delete the rejetId", description = "Delete rejetId, it take input id rejetPollution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{rejetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleterejetPollution(@Parameter(name = "rejetId", description = "the rejetId id deleted") @PathVariable Long rejetId) {
        rejetPollutionService.delete(rejetId);
    }

    @Operation(summary = "Read all rejetPollution", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllRejetPollution(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = rejetPollutionService.readAll(searchParams, pageable);
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
    public ResponseEntity<RejetPollutionDTO> updateStatutRejet(
            @PathVariable Long id,
            @RequestParam StatutType statutType) {
        return ResponseEntity.ok(rejetPollutionService.updateStatut(id, statutType));
    }

}
