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
import com.webgram.dgpsn.models.EtapeMissionDTO;
import com.webgram.dgpsn.services.EtapeMissionService;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/etapeMission")
@Tag(name = "Activity-controller", description = "Etape Mission Activity")
@RequiredArgsConstructor
public class EtapeMissionController {
    private final EtapeMissionService etapeMissionService;
    @Operation(summary = "Create etapeMission", description = "this endpoint take input etapeMission activity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the ExpenseActivity was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public  EtapeMissionDTO EtapeActivityAddToMission(@RequestBody EtapeMissionDTO etapeMission) {
        return etapeMissionService.create(etapeMission);
    }

    @PutMapping("/{etapeMissionId}")
    @ResponseStatus(HttpStatus.OK)
    public  EtapeMissionDTO updateEtapeMissionActivity(@Parameter(name = "etapeMissionId", description = "the etapeMissionId id updated") @PathVariable Long etapeMissionId, @RequestBody  EtapeMissionDTO etapeMission) {
        etapeMission.setId(etapeMissionId);
        return etapeMissionService.update(etapeMission);
    }

    @Operation(summary = "delete the etapeMissionId", description = "Delete etapeMissionId, it take input id etapeMissionId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{etapeMissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtapeActivity(@Parameter(name = "etapeMissionId", description = "the etapeMission id deleted") @PathVariable Long etapeMissionId) {
        etapeMissionService.delete(etapeMissionId);
    }

    @Operation(summary = "Read all EtapeActivity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page< EtapeMissionDTO> readAllEtapeActivityByProject(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list etape mission activity") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "plannedStartDate", description = "value of plannedStartDate used to filter list etape mission activity") @RequestParam(value = "plannedStartDate", required = false) Date plannedStartDate,
            @Parameter(name = "planedEndDate", description = "value of planedEndDate used to filter listetape mission activity") @RequestParam(value = "planedEndDate", required = false) Date planedEndDate,
            @Parameter(name = "actualStartDate", description = "value of actualStartDate used to filter etape mission activity") @RequestParam(value = "totalAmount", required = false) Date actualStartDate,
            @Parameter(name = "actualEndDate", description = "value of actualEndDate used to filter list etape mission activity") @RequestParam(value = "actualEndDate", required = false) Date actualEndDate,
            @Parameter(name = "statusId", description = "value of statusId used to filter list etape mission activity") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "responsableId", description = "value of responsableId used to filter list etape mission activity") @RequestParam(value = "responsableId", required = false) Long responsableId,
            @Parameter(name = "assignmentId", description = "value of assignmentId used to filter list etape mission activity") @RequestParam(value = "assignmentId", required = false) Long assignmentId
    ) throws ParseException {
        return etapeMissionService.readAll(pageable,
                 libelle,
                 plannedStartDate,
                 planedEndDate,
                 actualStartDate,
                 actualEndDate,
                 statusId,
                 responsableId,
                 assignmentId
        );
    }

}
