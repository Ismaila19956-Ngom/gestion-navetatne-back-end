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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.StationDTO;
import com.webgram.dgpsn.services.StationService;

import java.io.IOException;

@RestController
@RequestMapping("/stations")
@Tag(name = "stations", description = "Stations controller")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @Operation(summary = "Créer une station", description = "Ce endpoint prend une station en entrée et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StationDTO addStation(
            @RequestBody StationDTO stationDTO
    ) throws IOException {
        return stationService.create(stationDTO);
    }

    @Operation(summary = "Mettre à jour une station", description = "Ce endpoint met à jour une station existante avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping(value = "/{stationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StationDTO updateStation(
            @Parameter(name = "stationId", description = "L’ID de la station à mettre à jour")
            @PathVariable Long stationId,
            @RequestBody StationDTO stationDTO
    ) throws IOException {
        stationDTO.setId(stationId);
        return stationService.update(stationDTO);
    }

    @Operation(summary = "Lire une station", description = "Ce endpoint retourne une station avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public StationDTO readStation(
            @Parameter(name = "stationId", description = "L’ID de la station à lire")
            @PathVariable Long stationId
    ) {
        return stationService.read(stationId);
    }

    @Operation(summary = "Supprimer une station", description = "Ce endpoint supprime une station avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{stationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(
            @Parameter(name = "stationId", description = "L’ID de la station à supprimer")
            @PathVariable Long stationId
    ) {
        stationService.delete(stationId);
    }

    @Operation(summary = "Lire toutes les stations", description = "Ce endpoint retourne une liste paginée de stations avec des filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StationDTO> readAllStations(
            Pageable pageable,
            @Parameter(name = "code", description = "Code de la station pour filtrer")
            @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "name", description = "Nom de la station pour filtrer")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "typeId", description = "ID du type de station pour filtrer")
            @RequestParam(value = "typeId", required = false) Long typeId,
            @Parameter(name = "regionId", description = "ID de la région pour filtrer")
            @RequestParam(value = "regionId", required = false) Long regionId,
            @Parameter(name = "departementId", description = "ID du département pour filtrer")
            @RequestParam(value = "departementId", required = false) Long departementId,
            @Parameter(name = "sortBy", description = "Champ utilisé pour trier les résultats")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Ordre de tri (true pour ascendant, false pour descendant)")
            @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return stationService.readAll(pageable, code, name, typeId, regionId, departementId, sortBy, ascending);
    }
}