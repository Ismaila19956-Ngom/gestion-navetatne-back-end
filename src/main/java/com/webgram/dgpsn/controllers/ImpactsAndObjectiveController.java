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


import com.webgram.dgpsn.entities.enums.DetailType;
import com.webgram.dgpsn.models.ImpactsAndObjectiveDTO;
import com.webgram.dgpsn.services.ImpactsAndObjectiveService;

@RestController
@RequestMapping("/impactsAndObjectives")
@Tag(name = "ImpactsAndObjective-controller", description = "Impacts attendus et Objectifs specifiques controller")
@RequiredArgsConstructor
public class ImpactsAndObjectiveController {
    private final ImpactsAndObjectiveService impactsAndObjectiveService;

    @Operation(summary = "Create label", description = "this endpoint take input label and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the label was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImpactsAndObjectiveDTO createImpactsAndObjective(@RequestBody ImpactsAndObjectiveDTO impactsAndObjectiveDTO) {
        return impactsAndObjectiveService.create(impactsAndObjectiveDTO);
    }

    @PutMapping("/{impactsAndObjectiveId}")
    @ResponseStatus(HttpStatus.OK)
    public ImpactsAndObjectiveDTO updateImpactsAndObjective(@Parameter(name = "impactsAndObjectiveId", description = "the impactsAndObjective id to updated") @PathVariable Long impactsAndObjectiveId, @RequestBody ImpactsAndObjectiveDTO impactsAndObjectiveDTO) {
        impactsAndObjectiveDTO.setId(impactsAndObjectiveId);
        return impactsAndObjectiveService.update(impactsAndObjectiveDTO);
    }

    @Operation(summary = "Read the ImpactsAndObjective", description = "This endpoint is used to read ImpactsAndObjective, it take input id ImpactsAndObjective")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{impactsAndObjectiveId}")
    @ResponseStatus(HttpStatus.OK)
    public ImpactsAndObjectiveDTO readImpactsAndObjective(@Parameter(name = "impactsAndObjectiveId", description = "the ImpactsAndObjective id to read") @PathVariable Long impactsAndObjectiveId) {
        return impactsAndObjectiveService.read(impactsAndObjectiveId);
    }

    @Operation(summary = "delete the ImpactsAndObjective", description = "Delete ImpactsAndObjective, it take input id ImpactsAndObjective")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the ImpactsAndObjective was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{impactsAndObjectiveId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImpactsAndObjective(@Parameter(name = "impactsAndObjectiveId", description = "the ImpactsAndObjective id deleted") @PathVariable Long impactsAndObjectiveId) {
        impactsAndObjectiveService.delete(impactsAndObjectiveId);
    }

    @Operation(summary = "Read all label", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ImpactsAndObjectiveDTO> readAllLabel(
            Pageable pageable,
            @Parameter(name = "detailType", description = "value of detailType used to filter list impact and objective") @RequestParam(value = "detailType", required = false) DetailType detailType,
            @Parameter(name = "description", description = "value of label used to filter list description") @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "managementUnitId", description = "value of managementUnitId used to filter list Impacts or Objectives") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId
    ) {
        return impactsAndObjectiveService.readAll(pageable, detailType, description, managementUnitId);
    }


}
