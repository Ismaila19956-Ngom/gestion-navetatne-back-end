package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.FormationExterieurDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.FormationExterieurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;

@RestController
@RequestMapping("/formation-exterieurs")
@RequiredArgsConstructor
@CrossOrigin("*")

public class FormationExterieurController {

    private final FormationExterieurService formationExterieurService;

    @Operation(summary = "Create FormationExterieur", description = "Endpoint to create a new FormationExterieur")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createFormationExterieur(@RequestBody FormationExterieurDTO dto) {
        try {
            var savedDto = formationExterieurService.create(dto);
            return Response.ok().setPayload(savedDto).setMessage("FormationExterieur created successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update FormationExterieur", description = "Endpoint to update an existing FormationExterieur")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateFormationExterieur(@PathVariable Long id, @RequestBody FormationExterieurDTO dto) {
        dto.setId(id);
        try {
            var updatedDto = formationExterieurService.update(dto);
            return Response.ok().setPayload(updatedDto).setMessage("FormationExterieur updated successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete FormationExterieur", description = "Endpoint to delete a FormationExterieur by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFormationExterieur(@PathVariable Long id) {
        formationExterieurService.delete(id);
    }

    @Operation(summary = "Get FormationExterieur by ID", description = "Endpoint to retrieve a FormationExterieur by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getFormationExterieurById(@PathVariable Long id) {
        try {
            var dto = formationExterieurService.read(id);
            return Response.ok().setPayload(dto).setMessage("FormationExterieur found successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all FormationExterieurs", description = "Endpoint to retrieve all FormationExterieurs with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllFormationExterieurs(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = formationExterieurService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}