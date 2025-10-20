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
import com.webgram.dgpsn.models.SecteuractiviteDto;
import com.webgram.dgpsn.services.Impl.SecteuractiviteServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/secteuractivites")
@RequiredArgsConstructor
public class SecteuractiviteController {

    private final SecteuractiviteServiceImpl secteuractiviteService;

    @Operation(summary = "Create Secteuractivite", description = "this endpoint takes input Secteuractivite and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody SecteuractiviteDto secteuractivite) {
        try {
            var dto = secteuractiviteService.create(secteuractivite);
            return Response.ok().setPayload(dto).setMessage("Secteuractivite créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Secteuractivite", description = "This endpoint is used to read Secteuractivite, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{secteuractiviteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "secteuractiviteId", description = "the type Secteuractivite id to valid") @PathVariable Long secteuractiviteId) {
        try {
            var dto = secteuractiviteService.read(secteuractiviteId);
            return Response.ok().setPayload(dto).setMessage("Secteuractivite trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{secteuractiviteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "secteuractiviteId", description = "the Secteuractivite id to updated") @PathVariable Long secteuractiviteId, @RequestBody SecteuractiviteDto secteuractivite) {
        secteuractivite.setId(secteuractiviteId);
        try {
            var dto = secteuractiviteService.update(secteuractivite);
            return Response.ok().setPayload(dto).setMessage("Secteuractivite modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Secteuractivite", description = "Delete Secteuractivite, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{secteuractiviteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "secteuractiviteId", description = "the Secteuractivite id to be deleted") @PathVariable Long secteuractiviteId) {
        secteuractiviteService.delete(secteuractiviteId);
    }

    @Operation(summary = "Read all Secteuractivite", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = secteuractiviteService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Secteuractivite to CSV", description = "Exports the list of Secteuractivite to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportSecteuractivite(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Secteuractivites.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        secteuractiviteService.exportSecteuractivite(response.getWriter());
    }
}
