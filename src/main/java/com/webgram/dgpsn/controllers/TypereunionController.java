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
import com.webgram.dgpsn.models.TypereunionDto;
import com.webgram.dgpsn.services.Impl.TypereunionServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/typereunions")
@RequiredArgsConstructor
public class TypereunionController {

    private final TypereunionServiceImpl typereunionService;

    @Operation(summary = "Create Typereunion", description = "this endpoint takes input Typereunion and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createTypeReunion(@RequestBody TypereunionDto typereunion) {
        try {
            var dto = typereunionService.create(typereunion);
            return Response.ok().setPayload(dto).setMessage("Typereunion créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Typereunion", description = "This endpoint is used to read Typereunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{typereunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readTypeReunion(@Parameter(name = "typereunionId", description = "the type Typereunion id to valid") @PathVariable Long typereunionId) {
        try {
            var dto = typereunionService.read(typereunionId);
            return Response.ok().setPayload(dto).setMessage("Typereunion trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{typereunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTypeReunion(@Parameter(name = "typereunionId", description = "the Typereunion id to updated") @PathVariable Long typereunionId, @RequestBody TypereunionDto typereunion) {
        typereunion.setId(typereunionId);
        try {
            var dto = typereunionService.update(typereunion);
            return Response.ok().setPayload(dto).setMessage("Typereunion modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Typereunion", description = "Delete Typereunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{typereunionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeReunion(@Parameter(name = "typereunionId", description = "the Typereunion id to be deleted") @PathVariable Long typereunionId) {
        typereunionService.delete(typereunionId);
    }

    @Operation(summary = "Read all Typereunion", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllTypeReunion(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = typereunionService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Typereunion to CSV", description = "Exports the list of Typereunion to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportTypereunion(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Typereunions.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        typereunionService.exportTypereunion(response.getWriter());
    }
}
