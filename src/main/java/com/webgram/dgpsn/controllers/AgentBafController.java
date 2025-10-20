package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.AgentBafDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.AgentBafService;

import java.util.Map;

@RestController
@RequestMapping("/agentBaf")
@Tag(name = "agentBaf", description = "Agent Baf Management")
@RequiredArgsConstructor
public class AgentBafController {
    private final AgentBafService agentBafService;

    @Operation(summary = "Create agent baf", description = "This endpoint takes an agent baf input and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgentBafDTO createAgentBaf(@RequestBody AgentBafDTO agentBafDTO) {
        return agentBafService.create(agentBafDTO);
    }

    @Operation(summary = "Update agent baf", description = "This endpoint updates an existing agent baf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{agentBafId}")
    @ResponseStatus(HttpStatus.OK)
    public AgentBafDTO updateAgentBaf(@Parameter(name = "agentBafId", description = "The agent baf ID to update") @PathVariable Long agentBafId, @RequestBody AgentBafDTO agentBafDTO) {
        agentBafDTO.setId(agentBafId);
        return agentBafService.update(agentBafDTO);
    }

    @Operation(summary = "Read agent baf", description = "This endpoint retrieves an agent baf by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{agentBafId}")
    @ResponseStatus(HttpStatus.OK)
    public AgentBafDTO readAgentBaf(@Parameter(name = "agentBafId", description = "The agent baf ID to read") @PathVariable Long agentBafId) {
        return agentBafService.read(agentBafId);
    }

    @Operation(summary = "Delete agent baf", description = "This endpoint deletes an agent baf by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{agentBafId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgentBaf(@Parameter(name = "agentBafId", description = "The agent baf ID to delete") @PathVariable Long agentBafId) {
        agentBafService.delete(agentBafId);
    }

    @Operation(summary = "Read all agent bafs", description = "This endpoint retrieves all agent bafs with optional search parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allAgentBafs")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllAgentBafs(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = agentBafService.readAllAgentBafs(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}