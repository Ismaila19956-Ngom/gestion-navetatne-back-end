package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.CessationFonctionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CessationFonctionService;

import java.text.ParseException;
import java.util.Map;


@RestController
@RequestMapping("/cessationFonction")
@Tag(name = "cessation-controller", description = "cessation controller")
@RequiredArgsConstructor
public class CessationFonctionController {
    private final CessationFonctionService cessationService;

    @Operation(summary = "Create cessation", description = "this endpoint take input cessationDto and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createCessation(@RequestBody CessationFonctionDTO cessationDto) {
        try {
            cessationService.create(cessationDto);
            return Response.ok().setMessage("creation reussie");
        } catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
    

    @Operation(summary = "Update cessation", description = "this endpoint take input cessation id and cessationDto and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{cessationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCessation(
            @Parameter(name = "cessationId", description = "the cessationId id to update") @PathVariable Long cessationId,
            @Parameter(name = "cessationDto", description = "the cessationDto to update")  @Valid @RequestBody CessationFonctionDTO cessationDto) {
        try {
            cessationService.update(cessationId, cessationDto);
            return Response.ok().setMessage("Mise a Jour reussie");
        } catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the cessation", description = "This endpoint is used to read a single cessation. it take input cessation id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Request sent by the cessation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{cessationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readCessation(@Parameter(name = "cessationId", description = "the cessation id to read") @PathVariable Long cessationId) {
        return Response.ok().setPayload(cessationService.read(cessationId));
    }

    @Operation(summary = "Read all cessations", description = "It return list of cessations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Response<Object> readListCessation() {
        return Response.ok().setPayload(cessationService.readAll());
    }

    @Operation(summary = "Read cessations per page", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/page")
    public Response<Object> readPageCessation(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) throws ParseException {
        var pageCessation = cessationService.readPage(searchParams, page, size);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(pageCessation.getNumber())
                .totalElements(pageCessation.getTotalElements())
                .size(pageCessation.getSize())
                .build();
        return Response
                .ok()
                .setPayload(pageCessation.getContent())
                .setMetadata(metadata);
    }

    @GetMapping("/{cessationId}/solde")
    public ResponseEntity<Integer> getSoldeAnnuelByCongeId(@PathVariable Long cessationId) {
        Integer soldeAnnuel = cessationService.getLatestSoldeAnnuelByCongeIdAndMatricule(cessationId);
        return new ResponseEntity<>(soldeAnnuel, HttpStatus.OK);
    }

    @GetMapping("/duree/{cessationId}")
    public ResponseEntity<Integer> getLatestDureeSoldeByCongeId(@PathVariable Long cessationId) {
        Integer soldeTotal = cessationService.getLatestDureeSoldeByCongeId(cessationId);
        return new ResponseEntity<>(soldeTotal, HttpStatus.OK);
    }
    @Operation(summary = "delete the cessation", description = "Delete cessation, it take input cessation id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the cessation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{cessationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Object> deleteCessation(@Parameter(name = "cessationId", description = "the cessation id deleted") @PathVariable Long cessationId) {
        cessationService.delete(cessationId);
        return Response.deleted();

    }
    @Operation(summary = "Traiter manuellement les soldes restants", description = "Cet endpoint permet de traiter les soldes restants de congés pour tous les agents")
    @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Soldes restants traités avec succès"),
                @ApiResponse(responseCode = "400", description = "Requête incorrecte"),
                @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")})

    @PostMapping("/traiter-solde-restant")
    public Response<Object> traiterSoldeRestantManuellement(@RequestParam int annee) {
        try {
            String resultat = cessationService.traitementSoldeRestant(annee);
            return Response.ok()
                    .setMessage(resultat);
           } catch (IllegalArgumentException | IllegalStateException e) {
             return Response.badRequest()
                     .setMessage(e.getMessage());
               }
           }
    }

