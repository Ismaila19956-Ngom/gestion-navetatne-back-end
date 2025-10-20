package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.EvaluationfinanciereDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.EvaluationfinanciereServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evaluationfinancieres")
@RequiredArgsConstructor
public class EvaluationfinanciereController {

    private final EvaluationfinanciereServiceImpl evaluationfinanciereService;

    @Operation(summary = "Create Evaluationfinanciere", description = "this endpoint takes input Evaluationfinanciere and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createFinance(@RequestBody EvaluationfinanciereDto evaluationfinanciere) {
        try {
            var dto = evaluationfinanciereService.create(evaluationfinanciere);
            return Response.ok().setPayload(dto).setMessage("Evaluationfinanciere créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Evaluationfinanciere", description = "This endpoint is used to read Evaluationfinanciere, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{evaluationfinanciereId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readFinance(@Parameter(name = "evaluationfinanciereId", description = "the type Evaluationfinanciere id to valid") @PathVariable Long evaluationfinanciereId) {
        try {
            var dto = evaluationfinanciereService.read(evaluationfinanciereId);
            return Response.ok().setPayload(dto).setMessage("Evaluationfinanciere trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{evaluationfinanciereId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateFinance(@Parameter(name = "evaluationfinanciereId", description = "the Evaluationfinanciere id to updated") @PathVariable Long evaluationfinanciereId, @RequestBody EvaluationfinanciereDto evaluationfinanciere) {
        evaluationfinanciere.setId(evaluationfinanciereId);
        try {
            var dto = evaluationfinanciereService.update(evaluationfinanciere);
            return Response.ok().setPayload(dto).setMessage("Evaluationfinanciere modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Evaluationfinanciere", description = "Delete Evaluationfinanciere, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{evaluationfinanciereId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "evaluationfinanciereId", description = "the Evaluationfinanciere id to be deleted") @PathVariable Long evaluationfinanciereId) {
        evaluationfinanciereService.delete(evaluationfinanciereId);
    }

    @Operation(summary = "Read all Evaluationfinanciere", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllFinance(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = evaluationfinanciereService.readAll(searchParams, pageable);
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
    public Response<Object> readByEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve evaluationfinanciere") @PathVariable Long entrepriseId){
     List<EvaluationfinanciereDto> entites = evaluationfinanciereService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Evaluationfinanciere to CSV", description = "Exports the list of Evaluationfinanciere to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportEvaluationfinanciere(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Evaluationfinancieres.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        evaluationfinanciereService.exportEvaluationfinanciere(response.getWriter());
    }
}
