package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.DecisionreunionDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.DecisionreunionServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/decisionreunions")
@RequiredArgsConstructor
public class DecisionreunionController {

    private final DecisionreunionServiceImpl decisionreunionService;

    @Operation(summary = "Create Decisionreunion", description = "this endpoint takes input Decisionreunion and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody DecisionreunionDto decisionreunion) {
        try {
            var dto = decisionreunionService.create(decisionreunion);
            return Response.ok().setPayload(dto).setMessage("Decisionreunion créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Decisionreunion", description = "This endpoint is used to read Decisionreunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{decisionreunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "decisionreunionId", description = "the type Decisionreunion id to valid") @PathVariable Long decisionreunionId) {
        try {
            var dto = decisionreunionService.read(decisionreunionId);
            return Response.ok().setPayload(dto).setMessage("Decisionreunion trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{decisionreunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "decisionreunionId", description = "the Decisionreunion id to updated") @PathVariable Long decisionreunionId, @RequestBody DecisionreunionDto decisionreunion) {
        decisionreunion.setId(decisionreunionId);
        try {
            var dto = decisionreunionService.update(decisionreunion);
            return Response.ok().setPayload(dto).setMessage("Decisionreunion modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Decisionreunion", description = "Delete Decisionreunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{decisionreunionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "decisionreunionId", description = "the Decisionreunion id to be deleted") @PathVariable Long decisionreunionId) {
        decisionreunionService.delete(decisionreunionId);
    }

    @Operation(summary = "Read all Decisionreunion", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = decisionreunionService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Reunion", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/reunion/{reunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByReunionId(
        @Parameter(name = "reunionId", description = "the Id of the reunion to retrieve decisionreunion") @PathVariable Long reunionId){
     List<DecisionreunionDto> entites = decisionreunionService.readByReunionId(reunionId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Decisionreunion to CSV", description = "Exports the list of Decisionreunion to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportDecisionreunion(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Decisionreunions.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        decisionreunionService.exportDecisionreunion(response.getWriter());
    }
}
