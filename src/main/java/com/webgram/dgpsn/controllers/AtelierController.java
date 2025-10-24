package com.webgram.dgpsn.controllers;
import com.webgram.dgpsn.models.AtelierDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.AtelierService;
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
@RequestMapping("ateliers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AtelierController {

    private final AtelierService atelierService;

    @Operation(summary = "Create Atelier", description = "Endpoint to create a new Atelier")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    public ResponseEntity<Response<Object>> createAtelier(@RequestBody AtelierDTO dto) {
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var savedDto = atelierService.createAtelier(dto, files);
            return new ResponseEntity<>(Response.ok().setPayload(savedDto).setMessage("Created Successfully"), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(Response.badRequest().setMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Atelier", description = "Endpoint to update an existing Atelier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping(value = "/{id}")
    public Response<Object> updateAtelier(@PathVariable Long id, @RequestBody AtelierDTO dto) {
        dto.setId(id);
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var updatedDto = atelierService.updateAtelier(dto, files);
            return Response.ok().setPayload(updatedDto).setMessage("Updated Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete Atelier", description = "Endpoint to delete a Atelier by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAtelier(@PathVariable Long id) {
        atelierService.deleteAtelier(id);
    }

    @Operation(summary = "Get Atelier by ID", description = "Endpoint to retrieve a Atelier by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public Response<Object> getAtelierById(@PathVariable Long id) {
        try {
            var dto = atelierService.getAtelierById(id);
            return Response.ok().setPayload(dto).setMessage("Found Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all Ateliers", description = "Endpoint to retrieve all Ateliers with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/all")
    public Response<Object> getAllAteliers(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = atelierService.getAllAteliers(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Ateliers to CSV", description = "Endpoint to export Ateliers to a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/export")
    public void exportAteliers(HttpServletResponse response) {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"ateliers.csv\"");
        try (PrintWriter writer = response.getWriter()) {
            atelierService.exportAteliers(writer);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Import Ateliers from CSV", description = "Endpoint to import Ateliers from a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PostMapping("/import")
    public Response<Object> importAteliers(@RequestBody List<AtelierDTO> dtos) {
        try {
            List<AtelierDTO> imported = atelierService.importAteliers(dtos);
            return Response.ok().setPayload(imported).setMessage("Imported Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage("Error during import: " + ex.getMessage());
        }
    }


}
