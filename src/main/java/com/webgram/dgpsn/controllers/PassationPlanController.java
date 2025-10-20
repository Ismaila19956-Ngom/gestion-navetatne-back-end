package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.webgram.dgpsn.models.PassationPlanDTO;
import com.webgram.dgpsn.services.PassationPlanService;

@RestController
@RequestMapping("/passationplan")
@Tag(name = "Plan-de-passation-controller", description ="Plan de passation controller")
@RequiredArgsConstructor
public class PassationPlanController {
    private final PassationPlanService passationPlanService;

    private final ObjectMapper objectMapper;

    @Operation(summary = "Create passation-plan", description = "this endpoint take input passation-plan and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassationPlanDTO createPassationPlan(@RequestBody PassationPlanDTO passationPlanDTO) {
        return passationPlanService.createPassationPlan(passationPlanDTO);
    }

    @PutMapping("/{passationId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationPlanDTO updatePassationplan(@Parameter(name = "passationId", description = "the structure id updated") @PathVariable Long passationId,
                                        @RequestBody PassationPlanDTO passationPlanDTO) {
        passationPlanDTO.setId(passationId);
        return passationPlanService.updatePassationPlan(passationPlanDTO);
    }

    @Operation(summary = "Read the passation-plan", description = "This endpoint is used to read passation-plan it take input id passationPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{passationId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationPlanDTO readPassationPlan(@Parameter(name = "passationId", description = "the passation id to read") @PathVariable Long passationId) {
        return passationPlanService.readPassationPlan(passationId);
    }

    @Operation(summary = "delete the passation-plan", description = "Delete the passation-plan, it take input id the passation-plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{passationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassationplan(@Parameter(name = "passationId", description = "the passation-plan id deleted") @PathVariable Long passationId) {
        passationPlanService.deletePassationPlan(passationId);
    }

    @Operation(summary = "Read all passation-plan", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PassationPlanDTO> readAllPassationplan(
            Pageable pageable,
            @Parameter(name = "reference", description = "value of label used to filter list passation-plan") @RequestParam(value = "reference", required = false) String reference,
            @Parameter(name = "libelle", description = "value of label used to filter list passation-plan") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "managementUnitId", description = "value of label used to filter list passation-plan") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list passation-plan") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return passationPlanService.readAllPassationPlan(pageable, reference, libelle ,managementUnitId,sortBy, ascending);
    }




}
