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
import com.webgram.dgpsn.models.TypeagDto;
import com.webgram.dgpsn.services.Impl.TypeagServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/typeags")
@RequiredArgsConstructor
public class TypeagController {

    private final TypeagServiceImpl typeagService;

    @Operation(summary = "Create Typeag", description = "this endpoint takes input Typeag and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createTypeAG(@RequestBody TypeagDto typeag) {
        try {
            var dto = typeagService.create(typeag);
            return Response.ok().setPayload(dto).setMessage("Typeag créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Typeag", description = "This endpoint is used to read Typeag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{typeagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readTypeAG(@Parameter(name = "typeagId", description = "the type Typeag id to valid") @PathVariable Long typeagId) {
        try {
            var dto = typeagService.read(typeagId);
            return Response.ok().setPayload(dto).setMessage("Typeag trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{typeagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTypeAG(@Parameter(name = "typeagId", description = "the Typeag id to updated") @PathVariable Long typeagId, @RequestBody TypeagDto typeag) {
        typeag.setId(typeagId);
        try {
            var dto = typeagService.update(typeag);
            return Response.ok().setPayload(dto).setMessage("Typeag modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Typeag", description = "Delete Typeag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{typeagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeAG(@Parameter(name = "typeagId", description = "the Typeag id to be deleted") @PathVariable Long typeagId) {
        typeagService.delete(typeagId);
    }

    @Operation(summary = "Read all Typeag", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllTypeAG(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = typeagService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Typeag to CSV", description = "Exports the list of Typeag to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportTypeAg(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Typeags.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        typeagService.exportTypeag(response.getWriter());
    }
}
