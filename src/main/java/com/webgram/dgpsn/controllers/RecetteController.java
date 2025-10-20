package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.RecetteDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.RecetteServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recettes")
@RequiredArgsConstructor
public class RecetteController {

    private final RecetteServiceImpl recetteService;

    @Operation(summary = "Create Recette", description = "this endpoint takes input Recette and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRecette(@RequestBody RecetteDto recette) {
        try {
            var dto = recetteService.create(recette);
            return Response.ok().setPayload(dto).setMessage("Recette créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Recette", description = "This endpoint is used to read Recette, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{recetteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readRecette(@Parameter(name = "recetteId", description = "the type Recette id to valid") @PathVariable Long recetteId) {
        try {
            var dto = recetteService.read(recetteId);
            return Response.ok().setPayload(dto).setMessage("Recette trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{recetteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRecette(@Parameter(name = "recetteId", description = "the Recette id to updated") @PathVariable Long recetteId, @RequestBody RecetteDto recette) {
        recette.setId(recetteId);
        try {
            var dto = recetteService.update(recette);
            return Response.ok().setPayload(dto).setMessage("Recette modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Recette", description = "Delete Recette, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{recetteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecette(@Parameter(name = "recetteId", description = "the Recette id to be deleted") @PathVariable Long recetteId) {
        recetteService.delete(recetteId);
    }

    @Operation(summary = "Read all Recette", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllRecettes(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = recetteService.readAll(searchParams, pageable);
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
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve recette") @PathVariable Long entrepriseId){
     List<RecetteDto> entites = recetteService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Recette to CSV", description = "Exports the list of Recette to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportRecette(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Recettes.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        recetteService.exportRecette(response.getWriter());
    }
}
