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
import com.webgram.dgpsn.models.RapportDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.RapportDeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/rapport-declarations")
@Tag(name = "rapport-controller", description = "rapport controller")
@RequiredArgsConstructor
public class RapportDeclarationController {
    private final RapportDeclarationService rapportService;

    @Operation(summary = "Create rapport", description = "this endpoint take input agent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRapport(@RequestBody RapportDTO rapportDTO) {
        try {
            var dto = rapportService.create(rapportDTO);
            return Response.ok().setPayload(dto).setMessage("Rapport créée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Update rapport", description = "this endpoint take input agent and update it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{rapportId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRapport(@Parameter(name = "rapportId", description = "the rapport id updated") @PathVariable Long rapportId ,
                                        @RequestBody RapportDTO rapportDTO) {
        rapportDTO.setId(rapportId);
        try {
            var dto = rapportService.update(rapportDTO);
            return Response.ok().setPayload(dto).setMessage("Rapport modifiée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }


    }

    @Operation(summary = "Read the rapport", description = "This endpoint is used to read agent it take input id rapport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{rapportId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readRapport(@Parameter(name = "rapportId", description = "the rapport id to read") @PathVariable Long rapportId) {
        try {
            var dto = rapportService.read(rapportId);
            return Response.ok().setPayload(dto).setMessage("Rapport trouvée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }


    @Operation(summary = "delete the rapport", description = "Delete rapport, it take input id rapport")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{rapportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRapport(@Parameter(name = "rapportId", description = "the rapport id deleted") @PathVariable Long rapportId) {
        rapportService.delete(rapportId);
    }

    @Operation(summary = "Read all rapport", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllRapport(@RequestParam Map<String,String> searchParams, Pageable pageable) {
        var page = rapportService.readAll(searchParams, pageable);
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
