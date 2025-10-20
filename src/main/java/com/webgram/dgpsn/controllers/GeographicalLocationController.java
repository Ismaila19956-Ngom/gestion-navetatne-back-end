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
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.models.GeographicalLocationDTO;
import com.webgram.dgpsn.models.responses.ReportingByRegionDTO;
import com.webgram.dgpsn.services.GeographicalLocationService;

import java.util.List;

@RestController
@RequestMapping("/geographicalLocations")
@Tag(name = "geographicalLocation-controller", description = "GeographicalLocation controller")
@RequiredArgsConstructor
public class GeographicalLocationController {
    private final GeographicalLocationService geographicalLocationService;

    @Operation(summary = "Create geographicalLocation", description = "this endpoint take input geographicalLocation and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the geographicalLocation was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGeographicalLocations(@RequestBody List<GeographicalLocationDTO> geographicalLocationDTO) {
        geographicalLocationService.createGeographicalLocation(geographicalLocationDTO);
    }

    @Operation(summary = "delete the geographicalLocation", description = "Delete geographicalLocation, it take input   id geographicalLocation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the assignmentIssuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{geographicalLocationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGeographicalLocation(@Parameter(name = "geographicalLocationId", description = "the geographicalLocation id deleted") @PathVariable Long geographicalLocationId) {
        geographicalLocationService.deleteGeographicalLocation(geographicalLocationId);
    }

    @Operation(summary = "Read all geographicalLocation", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<GeographicalLocationDTO> readGeographicalLocations(
            Pageable pageable,
            @Parameter(name = "projetId", description = "value of projet used to filter list geographicalLocation") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "typeCardreLogiqueId", description = "value of typeCardreLogique used to filter list geographicalLocation") @RequestParam(value = "typeCardreLogique", required = false) CadreLogiqueType cadreLogiqueType,
            @Parameter(name = "libelle", description = "value of label used to filter list geographicalLocation") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "code", description = "value of code used to filter list geographicalLocation") @RequestParam(value = "code", required = false) String code
    ) {

        return geographicalLocationService.readAllGeographicalLocation(pageable,code, libelle, cadreLogiqueType, projetId);
    }

    @Operation(summary = "Read the project of region", description = "This endpoint is used to read reporting by structure it take input id structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/reportingByRegion/{regionId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportingByRegionDTO> getReportingByRegion(@Parameter(name = "regionId", description = "the region id to read") @PathVariable Long regionId) {
        return geographicalLocationService.getReportingBySRegion(regionId);
    }

}
