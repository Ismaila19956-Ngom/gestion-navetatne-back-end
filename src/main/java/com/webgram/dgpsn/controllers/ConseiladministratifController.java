package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.ConseiladministratifDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.ConseiladministratifServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conseiladministratifs")
@RequiredArgsConstructor
public class ConseiladministratifController {

    private final ConseiladministratifServiceImpl conseiladministratifService;

    @Operation(summary = "Create Conseiladministratif", description = "this endpoint takes input Conseiladministratif and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object>createConseilAdministration(@RequestBody ConseiladministratifDto conseiladministratif) {
        try {
            var dto = conseiladministratifService.create(conseiladministratif);
            return Response.ok().setPayload(dto).setMessage("Conseiladministratif créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Conseiladministratif", description = "This endpoint is used to read Conseiladministratif, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{conseiladministratifId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object>readConseilAdministration(@Parameter(name = "conseiladministratifId", description = "the type Conseiladministratif id to valid") @PathVariable Long conseiladministratifId) {
        try {
            var dto = conseiladministratifService.read(conseiladministratifId);
            return Response.ok().setPayload(dto).setMessage("Conseiladministratif trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{conseiladministratifId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object>updateConseilAdministration(@Parameter(name = "conseiladministratifId", description = "the Conseiladministratif id to updated") @PathVariable Long conseiladministratifId, @RequestBody ConseiladministratifDto conseiladministratif) {
        conseiladministratif.setId(conseiladministratifId);
        try {
            var dto = conseiladministratifService.update(conseiladministratif);
            return Response.ok().setPayload(dto).setMessage("Conseiladministratif modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Conseiladministratif", description = "Delete Conseiladministratif, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{conseiladministratifId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConseilAdministration(@Parameter(name = "conseiladministratifId", description = "the Conseiladministratif id to be deleted") @PathVariable Long conseiladministratifId) {
        conseiladministratifService.delete(conseiladministratifId);
    }

    @Operation(summary = "Read all Conseiladministratif", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllConseilAdministration(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = conseiladministratifService.readAll(searchParams, pageable);
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
    public Response<Object> readByConseilAdministrationEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve conseiladministratif") @PathVariable Long entrepriseId){
     List<ConseiladministratifDto> entites = conseiladministratifService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Conseiladministratif to CSV", description = "Exports the list of Conseiladministratif to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportConseiladministratif(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Conseiladministratifs.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        conseiladministratifService.exportConseiladministratif(response.getWriter());
    }
}
