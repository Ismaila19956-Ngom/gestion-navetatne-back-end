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
import com.webgram.dgpsn.models.IndicatorDTO;
import com.webgram.dgpsn.services.IndicatorService;

@RestController
@RequestMapping("/indicators")
@Tag(name = "indicator-controller", description = "Indicator controller")
@RequiredArgsConstructor
public class IndicatorController {
    private final IndicatorService indicatorService;

    @Operation(summary = "Create indicator", description = "this endpoint take input indicator and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IndicatorDTO createIndicator(@RequestBody IndicatorDTO indicator) {
        return indicatorService.create(indicator);
    }

    @PutMapping("/{indicatorId}")
    @ResponseStatus(HttpStatus.OK)
    public IndicatorDTO updateIndicator(@Parameter(name = "indicatorId", description = "the indicator type id updated") @PathVariable Long indicatorId, @RequestBody IndicatorDTO indicatorDTO) {
        indicatorDTO.setId(indicatorId);
        return indicatorService.update(indicatorDTO);
    }

    @Operation(summary = "Read the indicator", description = "This endpoint is used to read indicator  it take input id indicator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{indicatorId}")
    @ResponseStatus(HttpStatus.OK)
    public IndicatorDTO readIndicator(@Parameter(name = "indicatorId", description = "the indicator id to read") @PathVariable Long indicatorId) {
        return indicatorService.read(indicatorId);
    }

    @Operation(summary = "delete the indicator", description = "Delete period, it take input   id period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{indicatorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndicator(@Parameter(name = "indicatorId", description = "the indicator id deleted") @PathVariable Long indicatorId) {
       indicatorService.delete(indicatorId);
    }

    @Operation(summary = "Read all indicator", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<IndicatorDTO> readAllIndicators(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list indicator") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list indicator") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "unitId", description = "value of unit used to filter list indicator") @RequestParam(value = "unitId", required = false) Long unitId,
            @Parameter(name = "indicatorTypeId", description = "value of unit used to filter list indicator") @RequestParam(value = "indicatorTypeId", required = false) Long indicatorTypeId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list indicator") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list indicator") @RequestParam(value = "ascending", required = false) Boolean ascending

    ) {
        return indicatorService.readAll(pageable, code, libelle, unitId, indicatorTypeId,sortBy,ascending);
    }
}
