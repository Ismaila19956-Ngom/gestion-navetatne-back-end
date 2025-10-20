package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import com.webgram.dgpsn.services.Impl.PeriodeServiceImpl;
import com.webgram.dgpsn.models.PeriodeDto;
import com.webgram.dgpsn.models.Response;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/periodes")
@RequiredArgsConstructor
public class PeriodeController {

    private final PeriodeServiceImpl periodeService;

    @Operation(summary = "Create Periode", description = "this endpoint takes input Periode and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPeriode(@RequestBody PeriodeDto periode) {
        try {
            var dto = periodeService.create(periode);
            return Response.ok().setPayload(dto).setMessage("Periode créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Periode", description = "This endpoint is used to read Periode, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{periodeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readPeriode(@Parameter(name = "periodeId", description = "the type Periode id to valid") @PathVariable Long periodeId) {
        try {
            var dto = periodeService.read(periodeId);
            return Response.ok().setPayload(dto).setMessage("Periode trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{periodeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePeriode(@Parameter(name = "periodeId", description = "the Periode id to updated") @PathVariable Long periodeId, @RequestBody PeriodeDto periode) {
        periode.setId(periodeId);
        try {
            var dto = periodeService.update(periode);
            return Response.ok().setPayload(dto).setMessage("Periode modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Periode", description = "Delete Periode, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{periodeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePeriode(@Parameter(name = "periodeId", description = "the Periode id to be deleted") @PathVariable Long periodeId) {
        periodeService.delete(periodeId);
    }

    @Operation(summary = "Read all Periode", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllPeriodes(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = periodeService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Periode to CSV", description = "Exports the list of Periode to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportPeriode(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Periodes.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        periodeService.exportPeriode(response.getWriter());
    }
}
