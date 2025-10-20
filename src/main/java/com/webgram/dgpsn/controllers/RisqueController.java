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
import com.webgram.dgpsn.models.RisqueDto;
import com.webgram.dgpsn.services.Impl.RisqueServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/risques")
@RequiredArgsConstructor
public class RisqueController {

    private final RisqueServiceImpl risqueService;

    @Operation(summary = "Create Risque", description = "this endpoint takes input Risque and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRisque(@RequestBody RisqueDto risque) {
        try {
            var dto = risqueService.create(risque);
            return Response.ok().setPayload(dto).setMessage("Risque créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Risque", description = "This endpoint is used to read Risque, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{risqueId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readRisque(@Parameter(name = "risqueId", description = "the type Risque id to valid") @PathVariable Long risqueId) {
        try {
            var dto = risqueService.read(risqueId);
            return Response.ok().setPayload(dto).setMessage("Risque trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{risqueId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRisque(@Parameter(name = "risqueId", description = "the Risque id to updated") @PathVariable Long risqueId, @RequestBody RisqueDto risque) {
        risque.setId(risqueId);
        try {
            var dto = risqueService.update(risque);
            return Response.ok().setPayload(dto).setMessage("Risque modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Risque", description = "Delete Risque, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{risqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRisque(@Parameter(name = "risqueId", description = "the Risque id to be deleted") @PathVariable Long risqueId) {
        risqueService.delete(risqueId);
    }

    @Operation(summary = "Read all Risque", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllRisque(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = risqueService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Risque to CSV", description = "Exports the list of Risque to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportRisque(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Risques.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        risqueService.exportRisque(response.getWriter());
    }
}
