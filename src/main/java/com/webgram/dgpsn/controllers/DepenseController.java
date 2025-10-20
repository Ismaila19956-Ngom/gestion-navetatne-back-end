package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.DepenseDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.DepenseServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/depenses")
@RequiredArgsConstructor
public class DepenseController {

    private final DepenseServiceImpl depenseService;

    @Operation(summary = "Create Depense", description = "this endpoint takes input Depense and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createDepense(@RequestBody DepenseDto depense) {
        try {
            var dto = depenseService.create(depense);
            return Response.ok().setPayload(dto).setMessage("Depense créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Depense", description = "This endpoint is used to read Depense, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{depenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readDepense(@Parameter(name = "depenseId", description = "the type Depense id to valid") @PathVariable Long depenseId) {
        try {
            var dto = depenseService.read(depenseId);
            return Response.ok().setPayload(dto).setMessage("Depense trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{depenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateDepense(@Parameter(name = "depenseId", description = "the Depense id to updated") @PathVariable Long depenseId, @RequestBody DepenseDto depense) {
        depense.setId(depenseId);
        try {
            var dto = depenseService.update(depense);
            return Response.ok().setPayload(dto).setMessage("Depense modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Depense", description = "Delete Depense, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{depenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepense(@Parameter(name = "depenseId", description = "the Depense id to be deleted") @PathVariable Long depenseId) {
        depenseService.delete(depenseId);
    }

    @Operation(summary = "Read all Depense", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllDepenses(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = depenseService.readAll(searchParams, pageable);
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
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve depense") @PathVariable Long entrepriseId){
     List<DepenseDto> entites = depenseService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Depense to CSV", description = "Exports the list of Depense to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportDepense(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Depenses.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        depenseService.exportDepense(response.getWriter());
    }
}
