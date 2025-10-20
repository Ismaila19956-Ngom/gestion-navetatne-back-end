package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.CessionacquisitionDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.CessionacquisitionServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cessionacquisitions")
@RequiredArgsConstructor
public class CessionacquisitionController {

    private final CessionacquisitionServiceImpl cessionacquisitionService;

    @Operation(summary = "Create Cessionacquisition", description = "this endpoint takes input Cessionacquisition and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCessionacquisition(@RequestBody CessionacquisitionDto cessionacquisition) {
        try {
            var dto = cessionacquisitionService.create(cessionacquisition);
            return Response.ok().setPayload(dto).setMessage("Cessionacquisition créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Cessionacquisition", description = "This endpoint is used to read Cessionacquisition, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{cessionacquisitionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readCessionacquisition(@Parameter(name = "cessionacquisitionId", description = "the type Cessionacquisition id to valid") @PathVariable Long cessionacquisitionId) {
        try {
            var dto = cessionacquisitionService.read(cessionacquisitionId);
            return Response.ok().setPayload(dto).setMessage("Cessionacquisition trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{cessionacquisitionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCessionacquisition(@Parameter(name = "cessionacquisitionId", description = "the Cessionacquisition id to updated") @PathVariable Long cessionacquisitionId, @RequestBody CessionacquisitionDto cessionacquisition) {
        cessionacquisition.setId(cessionacquisitionId);
        try {
            var dto = cessionacquisitionService.update(cessionacquisition);
            return Response.ok().setPayload(dto).setMessage("Cessionacquisition modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Cessionacquisition", description = "Delete Cessionacquisition, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{cessionacquisitionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCessionacquisition(@Parameter(name = "cessionacquisitionId", description = "the Cessionacquisition id to be deleted") @PathVariable Long cessionacquisitionId) {
        cessionacquisitionService.delete(cessionacquisitionId);
    }

    @Operation(summary = "Read all Cessionacquisition", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllCessionacquisitions(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = cessionacquisitionService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Entreprise", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/entreprise/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve cessionacquisition") @PathVariable Long entrepriseId){
     List<CessionacquisitionDto> entites = cessionacquisitionService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Cessionacquisition to CSV", description = "Exports the list of Cessionacquisition to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportCessionacquisition(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Cessionacquisitions.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        cessionacquisitionService.exportCessionacquisition(response.getWriter());
    }
}
