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
import com.webgram.dgpsn.models.MilieuxPollutionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.MilieuxPollutionService;

import java.util.Map;

@RestController
@RequestMapping("/pollution-milieux")
@Tag(name = "milieux-pollution", description = "pollution milieux")
@RequiredArgsConstructor
public class MilieuxPollutionController {
    private final MilieuxPollutionService milieuxPollutionService;

    @Operation(summary = "Create actor project", description = "this endpoint take input actor project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type actor project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilieuxPollutionDTO createMilieuxPollution(@RequestBody MilieuxPollutionDTO milieuxPollution) {
        return milieuxPollutionService.create(milieuxPollution);
    }

    @PutMapping("/{milieuxId}")
    @ResponseStatus(HttpStatus.OK)
    public MilieuxPollutionDTO updateMilieuxPollution(@Parameter(name = "milieuxId", description = "the milieuxId updated") @PathVariable Long milieuxId, @RequestBody MilieuxPollutionDTO milieuxPollutionDTO) {
        milieuxPollutionDTO.setId(milieuxId);
        return milieuxPollutionService.update(milieuxPollutionDTO);
    }

    @Operation(summary = "Read the milieuxPollution", description = "This endpoint is used to read partner project it take input id actor project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{milieuxId}")
    @ResponseStatus(HttpStatus.OK)
    public MilieuxPollutionDTO readMilieuxPollution(@Parameter(name = "milieuxId", description = "the type rejet id to read") @PathVariable Long milieuxId) {
        return milieuxPollutionService.read(milieuxId);
    }

    @Operation(summary = "delete the milieuxId", description = "Delete milieuxId, it take input id milieuxPollution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{milieuxId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilieuxPollution(@Parameter(name = "milieuxId", description = "the milieuxPollutionId id deleted") @PathVariable Long milieuxId) {
        milieuxPollutionService.delete(milieuxId);
    }

    @Operation(summary = "Read all milieuxPollution", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllMilieuxPollution(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = milieuxPollutionService.readAll(searchParams, pageable);
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
    public ResponseEntity<MilieuxPollutionDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutType statutType) {
        return ResponseEntity.ok(milieuxPollutionService.updateStatut(id, statutType));
    }

}
