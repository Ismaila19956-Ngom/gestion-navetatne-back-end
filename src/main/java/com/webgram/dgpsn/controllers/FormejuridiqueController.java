package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.FormejuridiqueDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.FormejuridiqueServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/formejuridiques")
@RequiredArgsConstructor
public class FormejuridiqueController {

    private final FormejuridiqueServiceImpl formejuridiqueService;

    @Operation(summary = "Create Formejuridique", description = "this endpoint takes input Formejuridique and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createFormejuridique(@RequestBody FormejuridiqueDto formejuridique) {
        try {
            var dto = formejuridiqueService.create(formejuridique);
            return Response.ok().setPayload(dto).setMessage("Formejuridique créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Formejuridique", description = "This endpoint is used to read Formejuridique, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{formejuridiqueId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readFormejuridique(@Parameter(name = "formejuridiqueId", description = "the type Formejuridique id to valid") @PathVariable Long formejuridiqueId) {
        try {
            var dto = formejuridiqueService.read(formejuridiqueId);
            return Response.ok().setPayload(dto).setMessage("Formejuridique trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{formejuridiqueId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateFormejuridique(@Parameter(name = "formejuridiqueId", description = "the Formejuridique id to updated") @PathVariable Long formejuridiqueId, @RequestBody FormejuridiqueDto formejuridique) {
        formejuridique.setId(formejuridiqueId);
        try {
            var dto = formejuridiqueService.update(formejuridique);
            return Response.ok().setPayload(dto).setMessage("Formejuridique modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Formejuridique", description = "Delete Formejuridique, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{formejuridiqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFormejuridique(@Parameter(name = "formejuridiqueId", description = "the Formejuridique id to be deleted") @PathVariable Long formejuridiqueId) {
        formejuridiqueService.delete(formejuridiqueId);
    }

    @Operation(summary = "Read all Formejuridique", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllFormejuridiques(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = formejuridiqueService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Formejuridique to CSV", description = "Exports the list of Formejuridique to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportFormejuridique(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Formejuridiques.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        formejuridiqueService.exportFormejuridique(response.getWriter());
    }
}
