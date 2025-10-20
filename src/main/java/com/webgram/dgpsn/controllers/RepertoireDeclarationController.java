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
import com.webgram.dgpsn.models.RepertoireDeclarationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.RepertoireDeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/repertoire-declarations")
@Tag(name = "repertoire-declaration-controller", description = "Repertoire Declaration controller")
@RequiredArgsConstructor
public class RepertoireDeclarationController {
    private final RepertoireDeclarationService repertoireDeclarationService;

    @Operation(summary = "Create repertoire declaration", description = "This endpoint takes input repertoire declaration and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRepertoireDeclaration(@RequestBody RepertoireDeclarationDTO repertoireDeclarationDTO) {
        try {
            var dto = repertoireDeclarationService.create(repertoireDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("RepertoireDeclaration créée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update repertoire declaration", description = "This endpoint takes input repertoire declaration and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{repertoireDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRepertoireDeclaration(@Parameter(name = "repertoireDeclarationId", description = "The repertoire declaration id updated") @PathVariable Long repertoireDeclarationId,
                                                       @RequestBody RepertoireDeclarationDTO repertoireDeclarationDTO) {
        repertoireDeclarationDTO.setId(repertoireDeclarationId);
        try {
            var dto = repertoireDeclarationService.update(repertoireDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("RepertoireDeclaration modifiée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the repertoire declaration", description = "This endpoint is used to read repertoire declaration; it takes input id repertoire declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{repertoireDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readRepertoireDeclaration(@Parameter(name = "repertoireDeclarationId", description = "The repertoire declaration id to read") @PathVariable Long repertoireDeclarationId) {
        try {
            var dto = repertoireDeclarationService.read(repertoireDeclarationId);
            return Response.ok().setPayload(dto).setMessage("RepertoireDeclaration trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the repertoire declaration", description = "Delete repertoire declaration; it takes input id repertoire declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{repertoireDeclarationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepertoireDeclaration(@Parameter(name = "repertoireDeclarationId", description = "The repertoire declaration id deleted") @PathVariable Long repertoireDeclarationId) {
        repertoireDeclarationService.delete(repertoireDeclarationId);
    }

    @Operation(summary = "Read all repertoire declarations", description = "It takes input parameters of the page and returns the list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllRepertoireDeclaration(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = repertoireDeclarationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}