package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.EtatFinancierDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.EtatFinancierService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/etatFinanciers")
@RequiredArgsConstructor
public class EtatFinancierController {

    private final EtatFinancierService etatFinancierService;

    @Operation(summary = "Create etatFinancier", description = "this endpoint takes input etatFinancier and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createEtatFinancier(@RequestBody EtatFinancierDto etatFinancierDto) {
        try {
            var dto = etatFinancierService.create(etatFinancierDto);
            return Response.ok().setPayload(dto).setMessage("Etat financier créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Etat financier", description = "This endpoint is used to read Etat financier, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{etatFinancierId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readEtatFinancier(@Parameter(name = "etatFinancierId", description = "the  id to valid") @PathVariable Long etatFinancierId) {
        try {
            var dto = etatFinancierService.read(etatFinancierId);
            return Response.ok().setPayload(dto).setMessage("Etat financier trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{etatFinancierId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateEtatFinancier(@Parameter(name = "etatFinancierId", description = "the etat financier id to updated") @PathVariable Long etatFinancierId, @RequestBody EtatFinancierDto etatFinancierDto) {
        etatFinancierDto.setId(etatFinancierId);
        try {
            var dto = etatFinancierService.update(etatFinancierDto);
            return Response.ok().setPayload(dto).setMessage("Etat financier modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the etat financier", description = "Delete etat financier, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{etatFinancierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtatFinancier(@Parameter(name = "etatFinancierId", description = "the etat financier id to be deleted") @PathVariable Long etatFinancierId) {
        etatFinancierService.delete(etatFinancierId);
    }

    @Operation(summary = "Read all etat financier", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllEtatFinancier(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = etatFinancierService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Entreprise", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/entreprise/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve etat financier") @PathVariable Long entrepriseId){
     List<EtatFinancierDto> entites = etatFinancierService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }


}
