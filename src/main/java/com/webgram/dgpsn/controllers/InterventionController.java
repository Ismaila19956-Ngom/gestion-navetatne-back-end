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
import com.webgram.dgpsn.models.InterventionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.InterventionService;

import java.util.Map;

@RestController
@RequestMapping("/interventions")
@Tag(name = "intervention-controller", description = "intervention controller")
@RequiredArgsConstructor
public class InterventionController {
    private final InterventionService interventionService;

    @Operation(summary = "Create intervention", description = "this endpoint take input agent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createIntervention(@RequestBody InterventionDTO interventionDTO) {
        try {
            var dto = interventionService.create(interventionDTO);
            return Response.ok().setPayload(dto).setMessage("Intervention créée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Update intervention", description = "this endpoint take input agent and update it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{interventionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateIntervention(@Parameter(name = "interventionId", description = "the intervention id updated") @PathVariable Long interventionId ,
                                        @RequestBody InterventionDTO interventionDTO) {
        interventionDTO.setId(interventionId);
        try {
            var dto = interventionService.update(interventionDTO);
            return Response.ok().setPayload(dto).setMessage("Intervention modifiée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }


    }

    @Operation(summary = "Read the intervention", description = "This endpoint is used to read agent it take input id intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{interventionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readIntervention(@Parameter(name = "interventionId", description = "the intervention id to read") @PathVariable Long interventionId) {
        try {
            var dto = interventionService.read(interventionId);
            return Response.ok().setPayload(dto).setMessage("Intervention trouvée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }


    @Operation(summary = "delete the intervention", description = "Delete intervention, it take input id intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{interventionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIntervention(@Parameter(name = "interventionId", description = "the intervention id deleted") @PathVariable Long interventionId) {
        interventionService.delete(interventionId);
    }

    @Operation(summary = "Read all intervention", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllIntervention(@RequestParam Map<String,String> searchParams, Pageable pageable) {
        var page = interventionService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response
                .ok().setPayload(page.getContent())
                .setMetadata(metadata);
    }
}
