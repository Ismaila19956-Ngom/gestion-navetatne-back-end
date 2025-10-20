package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.models.TypedvaluationDto;
import com.webgram.dgpsn.services.Impl.TypedvaluationServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/typedvaluations")
@RequiredArgsConstructor
public class TypedvaluationController {

    private final TypedvaluationServiceImpl typedvaluationService;

    @Operation(summary = "Create Typedvaluation", description = "this endpoint takes input Typedvaluation and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createTypeEvaluation(@RequestBody TypedvaluationDto typedvaluation) {
        try {
            var dto = typedvaluationService.create(typedvaluation);
            return Response.ok().setPayload(dto).setMessage("Typedvaluation créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Typedvaluation", description = "This endpoint is used to read Typedvaluation, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{typedvaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readTypeEvaluation(@Parameter(name = "typedvaluationId", description = "the type Typedvaluation id to valid") @PathVariable Long typedvaluationId) {
        try {
            var dto = typedvaluationService.read(typedvaluationId);
            return Response.ok().setPayload(dto).setMessage("Typedvaluation trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{typedvaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTypeEvaluation(@Parameter(name = "typedvaluationId", description = "the Typedvaluation id to updated") @PathVariable Long typedvaluationId, @RequestBody TypedvaluationDto typedvaluation) {
        typedvaluation.setId(typedvaluationId);
        try {
            var dto = typedvaluationService.update(typedvaluation);
            return Response.ok().setPayload(dto).setMessage("Typedvaluation modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Typedvaluation", description = "Delete Typedvaluation, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{typedvaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeEvaluation(@Parameter(name = "typedvaluationId", description = "the Typedvaluation id to be deleted") @PathVariable Long typedvaluationId) {
        typedvaluationService.delete(typedvaluationId);
    }

    @Operation(summary = "Read all Typedvaluation", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllTypeEvaluations(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = typedvaluationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Typedvaluation to CSV", description = "Exports the list of Typedvaluation to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportTypedvaluation(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Typedvaluations.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        typedvaluationService.exportTypedvaluation(response.getWriter());
    }
}
