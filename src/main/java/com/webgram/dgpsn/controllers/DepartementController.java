package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.DepartementDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.DepartementServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementServiceImpl departementService;

    @Operation(summary = "Create Departement", description = "this endpoint takes input Departement and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody DepartementDto departement) {
        try {
            var dto = departementService.create(departement);
            return Response.ok().setPayload(dto).setMessage("Departement créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Departement", description = "This endpoint is used to read Departement, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{departementId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "departementId", description = "the type Departement id to valid") @PathVariable Long departementId) {
        try {
            var dto = departementService.read(departementId);
            return Response.ok().setPayload(dto).setMessage("Departement trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{departementId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "departementId", description = "the Departement id to updated") @PathVariable Long departementId, @RequestBody DepartementDto departement) {
        departement.setId(departementId);
        try {
            var dto = departementService.update(departement);
            return Response.ok().setPayload(dto).setMessage("Departement modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Departement", description = "Delete Departement, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{departementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "departementId", description = "the Departement id to be deleted") @PathVariable Long departementId) {
        departementService.delete(departementId);
    }

    @Operation(summary = "Read all Departement", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = departementService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Region", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/region/{regionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByRegionId(
        @Parameter(name = "regionId", description = "the Id of the region to retrieve departement") @PathVariable Long regionId){
     List<DepartementDto> entites = departementService.readByRegionId(regionId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Departement to CSV", description = "Exports the list of Departement to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportDepartement(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Departements.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        departementService.exportDepartement(response.getWriter());
    }
}
