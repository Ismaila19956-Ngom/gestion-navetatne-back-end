package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import com.webgram.dgpsn.services.Impl.ProcedurenominationServiceImpl;
import com.webgram.dgpsn.models.ProcedurenominationDto;
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
@RequestMapping("/procedurenominations")
@RequiredArgsConstructor
public class ProcedurenominationController {

    private final ProcedurenominationServiceImpl procedurenominationService;

    @Operation(summary = "Create Procedurenomination", description = "this endpoint takes input Procedurenomination and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createProcedureNomination(@RequestBody ProcedurenominationDto procedurenomination) {
        try {
            var dto = procedurenominationService.create(procedurenomination);
            return Response.ok().setPayload(dto).setMessage("Procedurenomination créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Procedurenomination", description = "This endpoint is used to read Procedurenomination, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{procedurenominationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readProcedureNomination(@Parameter(name = "procedurenominationId", description = "the type Procedurenomination id to valid") @PathVariable Long procedurenominationId) {
        try {
            var dto = procedurenominationService.read(procedurenominationId);
            return Response.ok().setPayload(dto).setMessage("Procedurenomination trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{procedurenominationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateProcedureNomination(@Parameter(name = "procedurenominationId", description = "the Procedurenomination id to updated") @PathVariable Long procedurenominationId, @RequestBody ProcedurenominationDto procedurenomination) {
        procedurenomination.setId(procedurenominationId);
        try {
            var dto = procedurenominationService.update(procedurenomination);
            return Response.ok().setPayload(dto).setMessage("Procedurenomination modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Procedurenomination", description = "Delete Procedurenomination, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{procedurenominationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProcedureNomination(@Parameter(name = "procedurenominationId", description = "the Procedurenomination id to be deleted") @PathVariable Long procedurenominationId) {
        procedurenominationService.delete(procedurenominationId);
    }

    @Operation(summary = "Read all Procedurenomination", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllProcedureNomination(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = procedurenominationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Procedurenomination to CSV", description = "Exports the list of Procedurenomination to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportProcedurenomination(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Procedurenominations.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        procedurenominationService.exportProcedurenomination(response.getWriter());
    }
}
