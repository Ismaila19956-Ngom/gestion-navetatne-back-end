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
import com.webgram.dgpsn.models.InformationDeclarationDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.InformationDeclarationService;

import java.util.Map;

@RestController
@RequestMapping("/information-declarations")
@Tag(name = "information-declaration-controller", description = "Information Declaration controller")
@RequiredArgsConstructor
public class InformationDeclarationController {
    private final InformationDeclarationService informationDeclarationService;

    @Operation(summary = "Create information declaration", description = "This endpoint takes input information declaration and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createInformationDeclaration(@RequestBody InformationDeclarationDTO informationDeclarationDTO) {
        try {
            var dto = informationDeclarationService.create(informationDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("InformationDeclaration créée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update information declaration", description = "This endpoint takes input information declaration and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{informationDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateInformationDeclaration(@Parameter(name = "informationDeclarationId", description = "The information declaration id updated") @PathVariable Long informationDeclarationId,
                                                        @RequestBody InformationDeclarationDTO informationDeclarationDTO) {
        informationDeclarationDTO.setId(informationDeclarationId);
        try {
            var dto = informationDeclarationService.update(informationDeclarationDTO);
            return Response.ok().setPayload(dto).setMessage("InformationDeclaration modifiée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the information declaration", description = "This endpoint is used to read information declaration; it takes input id information declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{informationDeclarationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readInformationDeclaration(@Parameter(name = "informationDeclarationId", description = "The information declaration id to read") @PathVariable Long informationDeclarationId) {
        try {
            var dto = informationDeclarationService.read(informationDeclarationId);
            return Response.ok().setPayload(dto).setMessage("InformationDeclaration trouvée");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete the information declaration", description = "Delete information declaration; it takes input id information declaration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{informationDeclarationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInformationDeclaration(@Parameter(name = "informationDeclarationId", description = "The information declaration id deleted") @PathVariable Long informationDeclarationId) {
        informationDeclarationService.delete(informationDeclarationId);
    }

    @Operation(summary = "Read all information declarations", description = "It takes input parameters of the page and returns the list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllInformationDeclaration(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = informationDeclarationService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}