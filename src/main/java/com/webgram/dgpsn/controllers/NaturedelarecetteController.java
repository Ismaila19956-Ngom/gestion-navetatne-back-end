package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.NaturedelarecetteDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.NaturedelarecetteServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/naturedelarecettes")
@RequiredArgsConstructor
public class NaturedelarecetteController {
    private final NaturedelarecetteServiceImpl naturedelarecetteService;

    @Operation(summary = "Create Naturedelarecette", description = "this endpoint takes input Naturedelarecette and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object>createRecettes(@RequestBody NaturedelarecetteDto naturedelarecette) {
        try {
            var dto = naturedelarecetteService.create(naturedelarecette);
            return Response.ok().setPayload(dto).setMessage("Naturedelarecette créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Naturedelarecette", description = "This endpoint is used to read Naturedelarecette, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{naturedelarecetteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object>readRecettes(@Parameter(name = "naturedelarecetteId", description = "the type Naturedelarecette id to valid") @PathVariable Long naturedelarecetteId) {
        try {
            var dto = naturedelarecetteService.read(naturedelarecetteId);
            return Response.ok().setPayload(dto).setMessage("Naturedelarecette trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{naturedelarecetteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object>updateRecettes(@Parameter(name = "naturedelarecetteId", description = "the Naturedelarecette id to updated") @PathVariable Long naturedelarecetteId, @RequestBody NaturedelarecetteDto naturedelarecette) {
        naturedelarecette.setId(naturedelarecetteId);
        try {
            var dto = naturedelarecetteService.update(naturedelarecette);
            return Response.ok().setPayload(dto).setMessage("Naturedelarecette modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Naturedelarecette", description = "Delete Naturedelarecette, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{naturedelarecetteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecettes(@Parameter(name = "naturedelarecetteId", description = "the Naturedelarecette id to be deleted") @PathVariable Long naturedelarecetteId) {
        naturedelarecetteService.delete(naturedelarecetteId);
    }

    @Operation(summary = "Read all Naturedelarecette", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object>readAllRecette(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = naturedelarecetteService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Naturedelarecette to CSV", description = "Exports the list of Naturedelarecette to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportNaturedelarecette(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Naturedelarecettes.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        naturedelarecetteService.exportNaturedelarecette(response.getWriter());
    }
}
