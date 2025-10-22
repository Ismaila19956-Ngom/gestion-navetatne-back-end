package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.OrdreMissionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.OrdreMissionService;

import java.io.IOException;

@RestController
@RequestMapping("/ordreMission")
@Tag(name = "ordreMission-controller", description = "OrdreMission controller")
@RequiredArgsConstructor
public class OrdreMissionController {
    private final OrdreMissionService ordreMissionService;

    @Operation(summary = "Create ordreMission", description = "this endpoint take input ordreMission and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the family was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdreMissionDTO createOrdreMission(@RequestBody OrdreMissionDTO ordreMissionDTO) throws IOException {
        return ordreMissionService.create(ordreMissionDTO);
    }

    @Operation(summary = "Create document", description = "this endpoint take input document and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(value = "/docOrdre/{ordreMissionId}" ,consumes = {

            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public OrdreMissionDTO createOrdreMissionDocument(@PathVariable("ordreMissionId") Long ordreMissionId, @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "DocumentDTO", required = true) String document) throws IOException  {
        System.out.println("requete entrante"+ordreMissionId);
        return ordreMissionService.createDocumentOrdreMission(ordreMissionId,file, document);
    }

    @PutMapping("/{ordreMissionId}")
    @ResponseStatus(HttpStatus.OK)
    public OrdreMissionDTO updateOrdreMission(@Parameter(name = "ordreMissionId", description = "the family id updated") @PathVariable Long ordreMissionId
            , @RequestBody OrdreMissionDTO ordreMissionDTO) throws IOException {

           ordreMissionDTO.setId(ordreMissionId);
        return ordreMissionService.update(ordreMissionDTO);


    }

    @PutMapping("status/{ordreMissionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateStatusOrdreMission(
            @Parameter(name = "ordreMissionId", description = "Change the Status of ordre mission") @PathVariable Long ordreMissionId,
            @Parameter(name = "statut", description = "value for changing status ordre Mission") @RequestParam(value = "statut", required = false) String statut) throws IOException {

        try {
            ordreMissionService.updateStatusOrdreMission(ordreMissionId,statut);
            return Response.ok().setMessage("Affectation validé");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the ordreMissionId", description = "This endpoint is used to read ordreMission it take input  ordreMissionId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{ordreMissionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readOrdreMission(@RequestParam Long ordreMissionId
    ) {
        try {
            var ordreMission= ordreMissionService.read(ordreMissionId);
            return Response.ok().setPayload(ordreMission).setMessage("Ordre mission trouvé");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage("Agent non retrouvé");
        }
    }


    @Operation(summary = "delete the ordreMissionId", description = "Delete ordreMissionId, it take input ordreMissionId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{ordreMissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdreMission(@Parameter(name = "ordreMissionId", description = "the familyId id deleted") @PathVariable Long ordreMissionId) {
        ordreMissionService.delete(ordreMissionId);
    }

    @Operation(summary = "delete the ordreMission document", description = "Delete Ordre Mission document, it take input id documentOrdreMissionId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("doc/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocumentFamily(@Parameter(name = "documentId", description = "the documentId id deleted") @PathVariable Long documentId) {
        ordreMissionService.deleteDocumentOrdreMission(documentId);
    }


    @Operation(summary = "Read all ordreMission", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllOrdreMission(
            Pageable pageable,
            @Parameter(name = "groupe", description = "value of prenom used to filter list family") @RequestParam(value = "groupe", required = false) String groupe,
            @Parameter(name = "indice", description = "value of nom used to filter list family") @RequestParam(value = "indice", required = false) String indice,
            @Parameter(name = "objectMission", description = "value of placeOfBorn used to filter list family") @RequestParam(value = "objectMission", required = false) String objectMission,
            @Parameter(name = "priseEnCharge", description = "value of placeOfBorn used to filter list family") @RequestParam(value = "priseEnCharge", required = false) String priseEnCharge,
            @Parameter(name = "frais", description = "value of linkFamily used to filter list family") @RequestParam(value = "frais", required = false) String frais,
            @Parameter(name = "ordreMissionId", description = "value of linkFamily used to filter list family") @RequestParam(value = "ordreMissionId", required = false) Long ordreMission,
            @Parameter(name = "agentId", description = "value of linkFamily used to filter list family") @RequestParam(value = "agentId", required = false) Long agentId,
            @Parameter(name = "sortBy", description = "list of sort Request used to filter list agent") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list agent") @RequestParam(value = "ascending", required = false) Boolean ascending

    ) {
        var pageFamily = ordreMissionService.readAll(pageable,groupe,indice,objectMission, priseEnCharge, frais, ordreMission,agentId,sortBy, ascending, null);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(pageFamily.getNumber())
                .totalElements(pageFamily.getTotalElements())
                .size(pageFamily.getSize())
                .build();
        return Response
                .ok().setPayload(pageFamily.getContent())
                .setMetadata(metadata);

    }

}
