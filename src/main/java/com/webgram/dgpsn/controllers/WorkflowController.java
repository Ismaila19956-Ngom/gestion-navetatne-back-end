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
import com.webgram.dgpsn.models.WorkflowDTO;
import com.webgram.dgpsn.services.WorkflowService;

import java.util.Map;

@RestController
@RequestMapping("/workflows")
@Tag(name = "workflow-controller", description = "workflow controller")
@RequiredArgsConstructor
public class WorkflowController {
    private final WorkflowService workflowService;

    @Operation(summary = "Create workflow", description = "this endpoint take input workflow and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflow was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowDTO createWorkflow(@RequestBody WorkflowDTO workflow) {
        return workflowService.createWorkflow(workflow);
    }

    @PutMapping("/{workflowId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowDTO updateWorkflow(@Parameter(name = "workflowId", description = "the workflow id to updated") @PathVariable Long workflowId, @RequestBody WorkflowDTO workflow) {
        workflow.setId(workflowId);
        return workflowService.updateWorkflow(workflow);
    }

    @Operation(summary = "Read the workflow", description = "This endpoint is used to read workflow, it take input id workflow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{workflowId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowDTO readWorkflow(@Parameter(name = "workflowId", description = "the type workflow id to read") @PathVariable Long workflowId) {
        return workflowService.readWorkflow(workflowId);
    }

    @Operation(summary = "delete the workflow", description = "Delete workflow, it take input id workflow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the workflow was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{workflowId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkflow(@Parameter(name = "workflowId", description = "the workflow id deleted") @PathVariable Long workflowId) {
        workflowService.deleteWorkflow(workflowId);
    }

    @Operation(summary = "Read all workflow", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<WorkflowDTO> readAllWorkflow(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return workflowService.readAllWorkflow(searchParams, page, size);
    }
}
