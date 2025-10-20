package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.DecisionagDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.DecisionagServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/decisionags")
@RequiredArgsConstructor
public class DecisionagController {

    private final DecisionagServiceImpl decisionagService;

    @Operation(summary = "Create Decisionag", description = "this endpoint takes input Decisionag and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createDecisonAg(@RequestBody DecisionagDto decisionag) {
        try {
            var dto = decisionagService.create(decisionag);
            return Response.ok().setPayload(dto).setMessage("Decisionag créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Decisionag", description = "This endpoint is used to read Decisionag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{decisionagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readDecisonAg(@Parameter(name = "decisionagId", description = "the type Decisionag id to valid") @PathVariable Long decisionagId) {
        try {
            var dto = decisionagService.read(decisionagId);
            return Response.ok().setPayload(dto).setMessage("Decisionag trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{decisionagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateDecisonAg(@Parameter(name = "decisionagId", description = "the Decisionag id to updated") @PathVariable Long decisionagId, @RequestBody DecisionagDto decisionag) {
        decisionag.setId(decisionagId);
        try {
            var dto = decisionagService.update(decisionag);
            return Response.ok().setPayload(dto).setMessage("Decisionag modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Decisionag", description = "Delete Decisionag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{decisionagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "decisionagId", description = "the Decisionag id to be deleted") @PathVariable Long decisionagId) {
        decisionagService.delete(decisionagId);
    }

    @Operation(summary = "Read all Decisionag", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllDecisonAg(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = decisionagService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  AssembleGeneral", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/assemblegeneral/{assemblegeneralId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByAssemblegeneralId(
        @Parameter(name = "assemblegeneralId", description = "the Id of the assemblegeneral to retrieve decisionag") @PathVariable Long assemblegeneralId){
     List<DecisionagDto> entites = decisionagService.readByAssemblegeneralId(assemblegeneralId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Decisionag to CSV", description = "Exports the list of Decisionag to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportDecisionag(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Decisionags.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        decisionagService.exportDecisionag(response.getWriter());
    }
}
