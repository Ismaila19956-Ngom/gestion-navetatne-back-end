package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.NaturedepenseDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.NaturedepenseServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/naturedepenses")
@RequiredArgsConstructor
public class NaturedepenseController {

    private final NaturedepenseServiceImpl naturedepenseService;

    @Operation(summary = "Create Naturedepense", description = "this endpoint takes input Naturedepense and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createNatureDepense(@RequestBody NaturedepenseDto naturedepense) {
        try {
            var dto = naturedepenseService.create(naturedepense);
            return Response.ok().setPayload(dto).setMessage("Naturedepense créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Naturedepense", description = "This endpoint is used to read Naturedepense, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{naturedepenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readNatureDepense(@Parameter(name = "naturedepenseId", description = "the type Naturedepense id to valid") @PathVariable Long naturedepenseId) {
        try {
            var dto = naturedepenseService.read(naturedepenseId);
            return Response.ok().setPayload(dto).setMessage("Naturedepense trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{naturedepenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateNatureDepense(@Parameter(name = "naturedepenseId", description = "the Naturedepense id to updated") @PathVariable Long naturedepenseId, @RequestBody NaturedepenseDto naturedepense) {
        naturedepense.setId(naturedepenseId);
        try {
            var dto = naturedepenseService.update(naturedepense);
            return Response.ok().setPayload(dto).setMessage("Naturedepense modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Naturedepense", description = "Delete Naturedepense, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{naturedepenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNatureDepense(@Parameter(name = "naturedepenseId", description = "the Naturedepense id to be deleted") @PathVariable Long naturedepenseId) {
        naturedepenseService.delete(naturedepenseId);
    }

    @Operation(summary = "Read all Naturedepense", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllNatureDepenses(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = naturedepenseService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Naturedepense to CSV", description = "Exports the list of Naturedepense to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportNaturedepense(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Naturedepenses.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        naturedepenseService.exportNaturedepense(response.getWriter());
    }
}
