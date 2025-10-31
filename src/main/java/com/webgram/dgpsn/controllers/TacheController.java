package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.TacheEntity.StatutTache;
import com.webgram.dgpsn.models.TacheDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.TacheServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/taches")
@RequiredArgsConstructor
public class TacheController {

    private final TacheServiceImpl tacheService;

    @Operation(summary = "Create Tache", description = "This endpoint takes input Tache and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createTache(@RequestBody TacheDto tache) {
        try {
            var dto = tacheService.create(tache);
            return Response.ok().setPayload(dto).setMessage("Tâche créée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Tache", description = "This endpoint is used to read Tache by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{tacheId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readTache(@Parameter(name = "tacheId", description = "The tache id") @PathVariable Long tacheId) {
        try {
            var dto = tacheService.read(tacheId);
            return Response.ok().setPayload(dto).setMessage("Tâche trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update Tache", description = "Update an existing Tache")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Tache not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{tacheId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTache(
            @Parameter(name = "tacheId", description = "The Tache id to update") @PathVariable Long tacheId, 
            @RequestBody TacheDto tache) {
        tache.setId(tacheId);
        try {
            var dto = tacheService.update(tache);
            return Response.ok().setPayload(dto).setMessage("Tâche modifiée avec succès");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update Tache Status", description = "Update the status of a Tache")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Tache not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{tacheId}/statut")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateTacheStatut(
            @Parameter(name = "tacheId", description = "The Tache id") @PathVariable Long tacheId,
            @Parameter(name = "statut", description = "New status (PLANIFIE, EN_COURS, TERMINE)")
            @RequestParam StatutTache statut) {
        try {
            tacheService.updateStatut(tacheId, statut);
            return Response.ok().setPayload(null).setMessage("Statut de la tâche mis à jour");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the Tache", description = "Delete Tache by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{tacheId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTache(@Parameter(name = "tacheId", description = "The Tache id to be deleted") @PathVariable Long tacheId) {
        tacheService.delete(tacheId);
    }

    @Operation(summary = "Read all Taches", description = "Get paginated list of all Taches with optional filters")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllTaches(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = tacheService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By Activite", description = "Get all Taches for a specific Activite")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/activite/{activiteId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByActiviteId(
        @Parameter(name = "activiteId", description = "The Id of the activite to retrieve taches") 
        @PathVariable Long activiteId) {
        List<TacheDto> taches = tacheService.readByActiviteId(activiteId);
        return Response.ok().setPayload(taches);
    }

    @Operation(summary = "Export Taches to CSV", description = "Exports the list of Taches to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportTache(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Taches_%s.csv", 
            LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        tacheService.exportTache(response.getWriter());
    }
}
