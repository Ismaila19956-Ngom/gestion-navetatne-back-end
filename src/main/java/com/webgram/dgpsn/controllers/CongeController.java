package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.CongeDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CongeService;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/conge")
@Tag(name = "conge-controller", description = "conge controller")
@RequiredArgsConstructor
public class CongeController {
    private final CongeService congeService;

    @Operation(summary = "Create conge", description = "this endpoint take input congeDto and save it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createConge(@Valid @RequestBody CongeDTO congeDto) {

        try {
            congeService.create(congeDto);
            return Response.ok().setMessage("creation reussie");
        } catch (Exception ex) {
            Logger.getLogger(CongeController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Create document", description = "this endpoint take input document and save it")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(value = "/docOrdre/{congeId}", consumes = {

            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public CongeDTO createCongeDocument(@PathVariable("congeId") Long congeId, @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "DocumentDTO", required = true) String document) throws IOException {
        System.out.println("requete entrante" + congeId);
        return congeService.createDocumentConge(congeId, file, document);
    }

    @Operation(summary = "Update conge", description = "this endpoint take input conge id and congeDto and save it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{congeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateConge(@Parameter(name = "congeId", description = "the congeId id to update") @PathVariable Long congeId, @Parameter(name = "congeDto", description = "the congeDto to update") @Valid @RequestBody CongeDTO congeDto) {
        congeService.update(congeId, congeDto);
        return Response.ok();
    }

    @Operation(summary = "Update conge", description = "this endpoint takes input conge id and new statut and updates it")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{congeId}/updateStatut")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateCongeStatut(@Parameter(name = "congeId", description = "the congeId id to update") @PathVariable Long congeId, @Parameter(name = "newStatut", description = "the new statut for the conge") @RequestParam StatutType statutType) {

        try {
            congeService.validConge(congeId, statutType);
            return Response.ok().setMessage("Conge validé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the conge", description = "This endpoint is used to read a single conge. it take input conge id")
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Request sent by the conge was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{congeId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readConge(@Parameter(name = "congeId", description = "the conge id to read") @PathVariable Long congeId) {
        return Response.ok().setPayload(congeService.read(congeId));
    }

    @Operation(summary = "Read all conges", description = "It return list of conges")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Response<Object> readListConge() {
        return Response.ok().setPayload(congeService.readAll());
    }

    @Operation(summary = "Read conges per page", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/page")
    public Response<Object> readPageConge(@RequestParam Map<String, String> searchParams, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) throws ParseException {
        var pageConge = congeService.readPage(searchParams, page, size, null);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(pageConge.getNumber()).totalElements(pageConge.getTotalElements()).size(pageConge.getSize()).build();
        return Response.ok().setPayload(pageConge.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "delete the conge", description = "Delete conge, it take input conge id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the conge was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{congeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Object> deleteConge(@Parameter(name = "congeId", description = "the conge id deleted") @PathVariable Long congeId) {
        congeService.delete(congeId);
        return Response.deleted();

    }

    @Operation(summary = "delete the ordreMission document", description = "Delete Ordre Mission document, it take input id documentOrdreMissionId")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("doc/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocumentConge(@Parameter(name = "documentId", description = "the documentId id deleted") @PathVariable Long documentId) {
        congeService.deleteDocumentConge(documentId);
    }

    @Operation(summary = "Update numero decision", description = "this endpoint takes input conge id and new numeroDecision and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{congeId}/numero-decision")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateNumeroDecision(
            @Parameter(name = "congeId", description = "the conge id to update") @PathVariable Long congeId,
            @Parameter(name = "numeroDecision", description = "the new numeroDecision for the conge")
            @RequestParam String numeroDecision) {
        try {
            CongeDTO updatedConge = congeService.updateNumeroDecision(congeId, numeroDecision);
            return Response.ok().setPayload(updatedConge).setMessage("Numéro de décision mis à jour avec succès");
        } catch (Exception ex) {
            Logger.getLogger(CongeController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }
}
