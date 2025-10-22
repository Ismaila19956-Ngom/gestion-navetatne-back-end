package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.WorkflowStepValidationDTO;
import com.webgram.dgpsn.services.WorkflowStepValidationService;

import java.util.Map;

@RestController
@RequestMapping("/workflow-step-validations")
@Tag(name = "workflow-step-validation-controller", description = "workflow step validation controller")
@RequiredArgsConstructor
public class WorkflowStepValidationController {
    private final WorkflowStepValidationService validationService;

    @Operation(summary = "Create workflowStepValidation", description = "this endpoint take input workflow step validation and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflow step validation was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowStepValidationDTO createWorkflow(@RequestBody WorkflowStepValidationDTO workflowStepValidation) {
        return validationService.createWorkflowStepValidation(workflowStepValidation);
    }

    @PutMapping("/{workflowStepValidationId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowStepValidationDTO updateWorkflow(@Parameter(name = "workflowStepValidationId", description = "the workflow step validation id to updated") @PathVariable Long workflowStepValidationId, @RequestBody WorkflowStepValidationDTO workflowStepValidation) {
        workflowStepValidation.setId(workflowStepValidationId);
        return validationService.updateWorkflowStepValidation(workflowStepValidation);
    }

    @Operation(summary = "Read the workflowStepValidation", description = "This endpoint is used to read workflow step validation, it take input id workflowStepValidation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{workflowStepValidationId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowStepValidationDTO readWorkflow(@Parameter(name = "workflowStepValidationId", description = "the type workflowStepValidation id to read") @PathVariable Long workflowStepValidationId) {
        return validationService.readWorkflowStepValidation(workflowStepValidationId);
    }

    @Operation(summary = "delete the workflow step validation", description = "Delete workflow step validation, it take input id workflow step validation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflowStepValidation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{workflowStepValidationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkflow(@Parameter(name = "workflowStepValidationId", description = "the workflowStepValidation id deleted") @PathVariable Long workflowStepValidationId) {
        validationService.readWorkflowStepValidation(workflowStepValidationId);
    }

    @Operation(summary = "Read all workflow step validation", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<WorkflowStepValidationDTO> readAllWorkflow(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return validationService.readAllWorkflowStepValidation(searchParams, page, size);
    }

    @Operation(summary = "Read workflow user configurations", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/config-user")
    public WorkflowStepValidationDTO readWorkflowUsers(@RequestParam(name = "profileId", required = false) Long profileId,
                                                       @RequestParam(name = "workflowStepId") Long workflowStepId) {
        return validationService.readWorkflowUsers(profileId, workflowStepId);
    }
}
