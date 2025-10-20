package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import com.webgram.dgpsn.services.Impl.PeriodiciteServiceImpl;
import com.webgram.dgpsn.models.PeriodiciteDto;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/periodicites")
@RequiredArgsConstructor
public class PeriodiciteController {

    private final PeriodiciteServiceImpl periodiciteService;

    @Operation(summary = "Create Periodicite", description = "this endpoint takes input Periodicite and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody PeriodiciteDto periodicite) {
        try {
            var dto = periodiciteService.create(periodicite);
            return Response.ok().setPayload(dto).setMessage("Periodicite créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Periodicite", description = "This endpoint is used to read Periodicite, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{periodiciteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "periodiciteId", description = "the type Periodicite id to valid") @PathVariable Long periodiciteId) {
        try {
            var dto = periodiciteService.read(periodiciteId);
            return Response.ok().setPayload(dto).setMessage("Periodicite trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{periodiciteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "periodiciteId", description = "the Periodicite id to updated") @PathVariable Long periodiciteId, @RequestBody PeriodiciteDto periodicite) {
        periodicite.setId(periodiciteId);
        try {
            var dto = periodiciteService.update(periodicite);
            return Response.ok().setPayload(dto).setMessage("Periodicite modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Periodicite", description = "Delete Periodicite, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{periodiciteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "periodiciteId", description = "the Periodicite id to be deleted") @PathVariable Long periodiciteId) {
        periodiciteService.delete(periodiciteId);
    }

    @Operation(summary = "Read all Periodicite", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = periodiciteService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Periode", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/periode/{periodeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByPeriodeId(
        @Parameter(name = "periodeId", description = "the Id of the periode to retrieve periodicite") @PathVariable Long periodeId){
     List<PeriodiciteDto> entites = periodiciteService.readByPeriodeId(periodeId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Periodicite to CSV", description = "Exports the list of Periodicite to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportPeriodicite(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Periodicites.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        periodiciteService.exportPeriodicite(response.getWriter());
    }
}
