package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.SuivDeclarationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.SuivDeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/suiv-declarations")
@Tag(name = "suiv-declaration-controller", description = "Suiv Declaration controller")
@RequiredArgsConstructor
public class SuivDeclarationController {
    private final SuivDeclarationService suivDeclarationService;

    @Operation(summary = "Create suiv declaration", description = "This endpoint takes input suiv declaration and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createSuivDeclaration(@RequestBody SuivDeclarationDTO suivDeclarationDTO) {
        try {
            var dto = suivDeclarationService.create(suivDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("SuivDeclaration créée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update suiv declaration", description = "This endpoint takes input suiv declaration and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{suivDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateSuivDeclaration(@Parameter(name = "suivDeclarationId", description = "The suiv declaration id updated") @PathVariable Long suivDeclarationId,
                                                 @RequestBody SuivDeclarationDTO suivDeclarationDTO) {
        suivDeclarationDTO.setId(suivDeclarationId);
        try {
            var dto = suivDeclarationService.update(suivDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("SuivDeclaration modifiée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the suiv declaration", description = "This endpoint is used to read suiv declaration; it takes input id suiv declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{suivDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readSuivDeclaration(@Parameter(name = "suivDeclarationId", description = "The suiv declaration id to read") @PathVariable Long suivDeclarationId) {
        try {
            var dto = suivDeclarationService.read(suivDeclarationId);
            return Response.ok().setPayload(dto).setMessage("SuivDeclaration trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the suiv declaration", description = "Delete suiv declaration; it takes input id suiv declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{suivDeclarationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSuivDeclaration(@Parameter(name = "suivDeclarationId", description = "The suiv declaration id deleted") @PathVariable Long suivDeclarationId) {
        suivDeclarationService.delete(suivDeclarationId);
    }

    @Operation(summary = "Read all suiv declarations", description = "It takes input parameters of the page and returns the list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllSuivDeclaration(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = suivDeclarationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}