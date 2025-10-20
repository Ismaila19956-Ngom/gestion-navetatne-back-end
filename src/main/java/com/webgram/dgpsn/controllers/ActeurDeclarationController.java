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
import com.webgram.dgpsn.models.ActeurDeclarationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.ActeurDeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/acteur-declarations")
@Tag(name = "acteur-declaration-controller", description = "Acteur Declaration controller")
@RequiredArgsConstructor
public class ActeurDeclarationController {
    private final ActeurDeclarationService acteurDeclarationService;

    @Operation(summary = "Create acteur declaration", description = "This endpoint takes input acteur declaration and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createActeurDeclaration(@RequestBody ActeurDeclarationDTO acteurDeclarationDTO) {
        try {
            var dto = acteurDeclarationService.create(acteurDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("ActeurDeclaration créée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update acteur declaration", description = "This endpoint takes input acteur declaration and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{acteurDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateActeurDeclaration(@Parameter(name = "acteurDeclarationId", description = "The acteur declaration id updated") @PathVariable Long acteurDeclarationId,
                                                   @RequestBody ActeurDeclarationDTO acteurDeclarationDTO) {
        acteurDeclarationDTO.setId(acteurDeclarationId);
        try {
            var dto = acteurDeclarationService.update(acteurDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("ActeurDeclaration modifiée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the acteur declaration", description = "This endpoint is used to read acteur declaration; it takes input id acteur declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{acteurDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readActeurDeclaration(@Parameter(name = "acteurDeclarationId", description = "The acteur declaration id to read") @PathVariable Long acteurDeclarationId) {
        try {
            var dto = acteurDeclarationService.read(acteurDeclarationId);
            return Response.ok().setPayload(dto).setMessage("ActeurDeclaration trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the acteur declaration", description = "Delete acteur declaration; it takes input id acteur declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{acteurDeclarationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActeurDeclaration(@Parameter(name = "acteurDeclarationId", description = "The acteur declaration id deleted") @PathVariable Long acteurDeclarationId) {
        acteurDeclarationService.delete(acteurDeclarationId);
    }

    @Operation(summary = "Read all acteur declarations", description = "It takes input parameters of the page and returns the list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllActeurDeclaration(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = acteurDeclarationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}