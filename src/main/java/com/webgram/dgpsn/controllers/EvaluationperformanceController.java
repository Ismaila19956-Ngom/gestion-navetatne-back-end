package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.EvaluationperformanceDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.EvaluationperformanceServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluationperformances")
@RequiredArgsConstructor
public class EvaluationperformanceController {

    private final EvaluationperformanceServiceImpl evaluationperformanceService;

    @Operation(summary = "Create Evaluationperformance", description = "this endpoint takes input Evaluationperformance and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPerformance(@RequestBody EvaluationperformanceDto evaluationperformance) {
        try {
            var dto = evaluationperformanceService.create(evaluationperformance);
            return Response.ok().setPayload(dto).setMessage("Evaluationperformance créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Evaluationperformance", description = "This endpoint is used to read Evaluationperformance, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{evaluationperformanceId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readPerformance(@Parameter(name = "evaluationperformanceId", description = "the type Evaluationperformance id to valid") @PathVariable Long evaluationperformanceId) {
        try {
            var dto = evaluationperformanceService.read(evaluationperformanceId);
            return Response.ok().setPayload(dto).setMessage("Evaluationperformance trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{evaluationperformanceId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePerformance(@Parameter(name = "evaluationperformanceId", description = "the Evaluationperformance id to updated") @PathVariable Long evaluationperformanceId, @RequestBody EvaluationperformanceDto evaluationperformance) {
        evaluationperformance.setId(evaluationperformanceId);
        try {
            var dto = evaluationperformanceService.update(evaluationperformance);
            return Response.ok().setPayload(dto).setMessage("Evaluationperformance modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Evaluationperformance", description = "Delete Evaluationperformance, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{evaluationperformanceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerformance(@Parameter(name = "evaluationperformanceId", description = "the Evaluationperformance id to be deleted") @PathVariable Long evaluationperformanceId) {
        evaluationperformanceService.delete(evaluationperformanceId);
    }

    @Operation(summary = "Read all Evaluationperformance", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllPerformance(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = evaluationperformanceService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Entreprise", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/entreprise/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readPerformanceByEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve evaluationperformance") @PathVariable Long entrepriseId){
     List<EvaluationperformanceDto> entites = evaluationperformanceService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Evaluationperformance to CSV", description = "Exports the list of Evaluationperformance to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportEvaluationPerformance(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Evaluationperformances.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        evaluationperformanceService.exportEvaluationperformance(response.getWriter());
    }
}
