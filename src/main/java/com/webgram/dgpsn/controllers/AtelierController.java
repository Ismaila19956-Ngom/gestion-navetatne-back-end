package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.AtelierDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.AtelierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;

@RestController
@RequestMapping("/ateliers")
@RequiredArgsConstructor
public class AtelierController {

    private final AtelierService atelierService;

    @Operation(summary = "Create Atelier", description = "Endpoint to create a new Atelier")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createAtelier(@RequestBody AtelierDTO dto) {
        try {
            var savedDto = atelierService.create(dto);
            return Response.ok().setPayload(savedDto).setMessage("Atelier created successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update Atelier", description = "Endpoint to update an existing Atelier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateAtelier(@PathVariable Long id, @RequestBody AtelierDTO dto) {
        dto.setId(id);
        try {
            var updatedDto = atelierService.update(dto);
            return Response.ok().setPayload(updatedDto).setMessage("Atelier updated successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete Atelier", description = "Endpoint to delete a Atelier by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAtelier(@PathVariable Long id) {
        atelierService.delete(id);
    }

    @Operation(summary = "Get Atelier by ID", description = "Endpoint to retrieve a Atelier by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAtelierById(@PathVariable Long id) {
        try {
            var dto = atelierService.read(id);
            return Response.ok().setPayload(dto).setMessage("Atelier found successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all Ateliers", description = "Endpoint to retrieve all Ateliers with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllAteliers(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = atelierService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}