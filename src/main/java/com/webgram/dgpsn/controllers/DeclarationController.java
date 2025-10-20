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
import com.webgram.dgpsn.models.DeclarationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.DeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/declarations")
@Tag(name = "declaration-controller", description = "declaration controller")
@RequiredArgsConstructor
public class DeclarationController {
    private final DeclarationService declarationService;

    @Operation(summary = "Create declaration", description = "this endpoint take input agent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createDeclaration(@RequestBody DeclarationDTO declarationDTO) {
        try {
            var dto = declarationService.create(declarationDTO);
            return Response.ok().setPayload(dto).setMessage("Declaration créée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Update declaration", description = "this endpoint take input agent and update it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{declarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateDeclaration(@Parameter(name = "declarationId", description = "the declaration id updated") @PathVariable Long declarationId ,
                                        @RequestBody DeclarationDTO declarationDTO) {
        declarationDTO.setId(declarationId);
        try {
            var dto = declarationService.update(declarationDTO);
            return Response.ok().setPayload(dto).setMessage("Declaration modifiée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }


    }

    @Operation(summary = "Read the declaration", description = "This endpoint is used to read agent it take input id declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{declarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readDeclaration(@Parameter(name = "declarationId", description = "the declaration id to read") @PathVariable Long declarationId) {
        try {
            var dto = declarationService.read(declarationId);
            return Response.ok().setPayload(dto).setMessage("Declaration trouvée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }


    @Operation(summary = "delete the declaration", description = "Delete declaration, it take input id declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{declarationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDeclaration(@Parameter(name = "declarationId", description = "the declaration id deleted") @PathVariable Long declarationId) {
        declarationService.delete(declarationId);
    }

    @Operation(summary = "Read all declaration", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllDeclaration(@RequestParam Map<String,String> searchParams, Pageable pageable) {
        var page = declarationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response
                .ok().setPayload(page.getContent())
                .setMetadata(metadata);
    }
}
