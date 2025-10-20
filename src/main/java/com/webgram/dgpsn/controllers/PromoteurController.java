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
import com.webgram.dgpsn.models.PromoteurDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.PromoteurServiceImpl;

@RestController
@RequestMapping("/promoteurs")
@RequiredArgsConstructor
@Tag(name="")
public class PromoteurController {

    private final PromoteurServiceImpl promoteurService;

    @Operation(summary = "Create promoteur", description = "this endpoint take input promoteur and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createPromoteur(@RequestBody PromoteurDTO promoteur) {
        return Response.ok().setPayload(promoteurService.create(promoteur));
    }

    @Operation(summary = "Update promoteur", description = "this endpoint take input promoteur and update it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{promoteurId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updatePromoteur(@Parameter(name = "promoteurId", description = "the promoteur id updated") @PathVariable Long promoteurId, @RequestBody PromoteurDTO promoteur) {
        return Response.ok().setPayload(promoteurService.update(promoteur));
    }

    @Operation(summary = "Read the promoteur", description = "This endpoint is used to read promoteur it take input id promoteur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{promoteurId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readPromoteur(@Parameter(name = "promoteurId", description = "the promoteur id to read") @PathVariable Long promoteurId) {
        return Response.ok().setPayload(promoteurService.read(promoteurId));
    }

    @Operation(summary = "Supprimer le promoteur", description = "Supprime promoteur, prend en parametre l'id du promoteur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),  @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{promoteurId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePromoteur(@Parameter(name = "promoteurId", description = "the promoteurId id deleted") @PathVariable Long promoteurId) {
        promoteurService.delete(promoteurId);
    }

    @Operation(summary = "Lire tous les promoteurs", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllPromoteur(Pageable pageable,
            @Parameter(name = "nomEntreprise", description = "Nom de l'entreprise")
            @RequestParam(name = "nomEntreprise", required = false) String nomEntreprise,
            @Parameter(name = "personneContact", description = "Nom du contact")
            @RequestParam(name = "personneContact", required = false) String personneContact,
            @Parameter(name = "fonctionContact", description = "Fonction du contact")
            @RequestParam(name = "fonctionContact", required = false) String fonctionContact,
            @Parameter(name = "telephone", description = "Numéro de téléphone")
            @RequestParam(name = "telephone", required = false) String telephone,
            @Parameter(name = "adresseSiege", description = "Adresse du siège")
            @RequestParam(name = "adresseSiege", required = false) String adresseSiege,
            @Parameter(name = "adresseSite", description = "Adresse du site")
            @RequestParam(name = "adresseSite", required = false) String adresseSite,
            @Parameter(name = "bureauEtudes", description = "Bureau d'études")
            @RequestParam(name = "bureauEtudes", required = false) String bureauEtudes) {
        var page = promoteurService.readAll(pageable, nomEntreprise, personneContact, fonctionContact, telephone, adresseSiege, adresseSite, bureauEtudes);
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
