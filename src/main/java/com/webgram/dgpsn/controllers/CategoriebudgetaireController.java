package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.CategoriebudgetaireDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.CategoriebudgetaireServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/categoriebudgetaires")
@RequiredArgsConstructor
public class CategoriebudgetaireController {

    private final CategoriebudgetaireServiceImpl categoriebudgetaireService;

    @Operation(summary = "Create Categoriebudgetaire", description = "this endpoint takes input Categoriebudgetaire and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCategorieBudgetaire(@RequestBody CategoriebudgetaireDto categoriebudgetaire) {
        try {
            var dto = categoriebudgetaireService.create(categoriebudgetaire);
            return Response.ok().setPayload(dto).setMessage("Categoriebudgetaire créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Categoriebudgetaire", description = "This endpoint is used to read Categoriebudgetaire, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{categoriebudgetaireId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readCategorieBudgetaire(@Parameter(name = "categoriebudgetaireId", description = "the type Categoriebudgetaire id to valid") @PathVariable Long categoriebudgetaireId) {
        try {
            var dto = categoriebudgetaireService.read(categoriebudgetaireId);
            return Response.ok().setPayload(dto).setMessage("Categoriebudgetaire trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{categoriebudgetaireId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCategorieBudgetaire(@Parameter(name = "categoriebudgetaireId", description = "the Categoriebudgetaire id to updated") @PathVariable Long categoriebudgetaireId, @RequestBody CategoriebudgetaireDto categoriebudgetaire) {
        categoriebudgetaire.setId(categoriebudgetaireId);
        try {
            var dto = categoriebudgetaireService.update(categoriebudgetaire);
            return Response.ok().setPayload(dto).setMessage("Categoriebudgetaire modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Categoriebudgetaire", description = "Delete Categoriebudgetaire, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{categoriebudgetaireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategorieBudgetaire(@Parameter(name = "categoriebudgetaireId", description = "the Categoriebudgetaire id to be deleted") @PathVariable Long categoriebudgetaireId) {
        categoriebudgetaireService.delete(categoriebudgetaireId);
    }

    @Operation(summary = "Read all Categoriebudgetaire", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllCategorieBudgetaire(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = categoriebudgetaireService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Categoriebudgetaire to CSV", description = "Exports the list of Categoriebudgetaire to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportCategoriebudgetaire(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Categoriebudgetaires.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        categoriebudgetaireService.exportCategoriebudgetaire(response.getWriter());
    }
}
