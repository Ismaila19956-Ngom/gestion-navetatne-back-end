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
import com.webgram.dgpsn.models.CompteRenduDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CompteRenduService;

import java.util.Map;

@RestController
@RequestMapping("/compteRendus")
@Tag(name = "compteRendu-controller", description = "compteRendu controller")
@RequiredArgsConstructor
public class CompteRenduController {
    private final CompteRenduService compteRenduService;

    @Operation(summary = "Create compteRendu", description = "this endpoint take input agent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCompteRendu(@RequestBody CompteRenduDTO compteRenduDTO) {
        try {
            var dto = compteRenduService.create(compteRenduDTO);
            return Response.ok().setPayload(dto).setMessage("CompteRendu créée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Update compteRendu", description = "this endpoint take input agent and update it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{compteRenduId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCompteRendu(@Parameter(name = "compteRenduId", description = "the compteRendu id updated") @PathVariable Long compteRenduId ,
                                        @RequestBody CompteRenduDTO compteRenduDTO) {
        compteRenduDTO.setId(compteRenduId);
        try {
            var dto = compteRenduService.update(compteRenduDTO);
            return Response.ok().setPayload(dto).setMessage("CompteRendu modifiée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }


    }

    @Operation(summary = "Read the compteRendu", description = "This endpoint is used to read agent it take input id compteRendu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{compteRenduId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readCompteRendu(@Parameter(name = "compteRenduId", description = "the compteRendu id to read") @PathVariable Long compteRenduId) {
        try {
            var dto = compteRenduService.read(compteRenduId);
            return Response.ok().setPayload(dto).setMessage("CompteRendu trouvée");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }


    @Operation(summary = "delete the compteRendu", description = "Delete compteRendu, it take input id compteRendu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{compteRenduId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompteRendu(@Parameter(name = "compteRenduId", description = "the compteRendu id deleted") @PathVariable Long compteRenduId) {
        compteRenduService.delete(compteRenduId);
    }

    @Operation(summary = "Read all compteRendu", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllCompteRendu(@RequestParam Map<String,String> searchParams, Pageable pageable) {
        var page = compteRenduService.readAll(searchParams, pageable);
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
