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
import com.webgram.dgpsn.models.WorkflowStepDTO;
import com.webgram.dgpsn.services.WorkflowStepService;

import java.util.Map;

@RestController
@RequestMapping("/workflow-steps")
@Tag(name = "workflow-step-controller", description = "workflow step controller")
@RequiredArgsConstructor
public class WorkflowStepController {
    private final WorkflowStepService workflowStepStepService;

    @Operation(summary = "Create workflowStep", description = "this endpoint take input workflow step and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflowStep was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowStepDTO createWorkflow(@RequestBody WorkflowStepDTO workflowStep) {
        return workflowStepStepService.createWorkflowStep(workflowStep);
    }

    @PutMapping("/{workflowStepId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowStepDTO updateWorkflow(@Parameter(name = "workflowStepId", description = "the workflow step id to updated") @PathVariable Long workflowStepId, @RequestBody WorkflowStepDTO workflowStep) {
        workflowStep.setId(workflowStepId);
        return workflowStepStepService.updateWorkflowStep(workflowStep);
    }

    @Operation(summary = "Read the workflowStep", description = "This endpoint is used to read workflow step, it take input id workflowStep")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{workflowStepId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowStepDTO readWorkflow(@Parameter(name = "workflowStepId", description = "the type workflowStep id to read") @PathVariable Long workflowStepId) {
        return workflowStepStepService.readWorkflowStep(workflowStepId);
    }

    @Operation(summary = "delete the workflow step", description = "Delete workflow step, it take input id workflow step")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflowStep was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{workflowStepId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkflow(@Parameter(name = "workflowStepId", description = "the workflowStep id deleted") @PathVariable Long workflowStepId) {
        workflowStepStepService.deleteWorkflowStep(workflowStepId);
    }

    @Operation(summary = "Read all workflow step", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<WorkflowStepDTO> readAllWorkflow(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return workflowStepStepService.readAllWorkflowStep(searchParams, page, size);
    }
}
