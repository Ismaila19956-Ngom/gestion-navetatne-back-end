package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.EntrepriseDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.EntrepriseServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/entreprises")
@RequiredArgsConstructor
public class EntrepriseController {

    private final EntrepriseServiceImpl entrepriseService;

    @Operation(summary = "Create Entreprise", description = "this endpoint takes input Entreprise and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody EntrepriseDto entreprise) {
        try {
            var dto = entrepriseService.create(entreprise);
            return Response.ok().setPayload(dto).setMessage("Entreprise créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Entreprise", description = "This endpoint is used to read Entreprise, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "entrepriseId", description = "the type Entreprise id to valid") @PathVariable Long entrepriseId) {
        try {
            var dto = entrepriseService.read(entrepriseId);
            return Response.ok().setPayload(dto).setMessage("Entreprise trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "entrepriseId", description = "the Entreprise id to updated") @PathVariable Long entrepriseId, @RequestBody EntrepriseDto entreprise) {
        entreprise.setId(entrepriseId);
        try {
            var dto = entrepriseService.update(entreprise);
            return Response.ok().setPayload(dto).setMessage("Entreprise modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Entreprise", description = "Delete Entreprise, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{entrepriseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "entrepriseId", description = "the Entreprise id to be deleted") @PathVariable Long entrepriseId) {
        entrepriseService.delete(entrepriseId);
    }

    @Operation(summary = "Read all Entreprise", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = entrepriseService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Entreprise to CSV", description = "Exports the list of Entreprise to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportEntreprise(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Entreprises.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        entrepriseService.exportEntreprise(response.getWriter());
    }
}
