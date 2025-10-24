package com.webgram.dgpsn.controllers;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.FormationExterieurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("formation-exterieurs")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FormationExterieurController {

    private final FormationExterieurService formationExterieurService;

    @Operation(summary = "Create FormationExterieur", description = "Endpoint to create a new FormationExterieur")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    public ResponseEntity<Response<Object>> createFormationExterieur(@RequestBody FormationExterieurDTO dto) {
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var savedDto = formationExterieurService.createFormationExterieur(dto, files);
            return new ResponseEntity<>(Response.ok().setPayload(savedDto).setMessage("Created Successfully"), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(Response.badRequest().setMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update FormationExterieur", description = "Endpoint to update an existing FormationExterieur")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping(value = "/{id}")
    public Response<Object> updateFormationExterieur(@PathVariable Long id, @RequestBody FormationExterieurDTO dto) {
        dto.setId(id);
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var updatedDto = formationExterieurService.updateFormationExterieur(dto, files);
            return Response.ok().setPayload(updatedDto).setMessage("Updated Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete FormationExterieur", description = "Endpoint to delete a FormationExterieur by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFormationExterieur(@PathVariable Long id) {
        formationExterieurService.deleteFormationExterieur(id);
    }

    @Operation(summary = "Get FormationExterieur by ID", description = "Endpoint to retrieve a FormationExterieur by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public Response<Object> getFormationExterieurById(@PathVariable Long id) {
        try {
            var dto = formationExterieurService.getFormationExterieurById(id);
            return Response.ok().setPayload(dto).setMessage("Found Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all FormationExterieurs", description = "Endpoint to retrieve all FormationExterieurs with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/all")
    public Response<Object> getAllFormationExterieurs(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = formationExterieurService.getAllFormationExterieurs(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export FormationExterieurs to CSV", description = "Endpoint to export FormationExterieurs to a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/export")
    public void exportFormationExterieurs(HttpServletResponse response) {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"formationExterieurs.csv\"");
        try (PrintWriter writer = response.getWriter()) {
            formationExterieurService.exportFormationExterieurs(writer);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Import FormationExterieurs from CSV", description = "Endpoint to import FormationExterieurs from a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PostMapping("/import")
    public Response<Object> importFormationExterieurs(@RequestBody List<FormationExterieurDTO> dtos) {
        try {
            List<FormationExterieurDTO> imported = formationExterieurService.importFormationExterieurs(dtos);
            return Response.ok().setPayload(imported).setMessage("Imported Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage("Error during import: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Update status")
    public ResponseEntity<FormationExterieurDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam Statut statut) {
        return ResponseEntity.ok(formationExterieurService.updateStatut(id, statut));
    }
}
