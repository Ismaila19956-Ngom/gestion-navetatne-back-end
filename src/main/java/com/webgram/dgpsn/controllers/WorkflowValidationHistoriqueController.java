package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.webg.gestionrisque.models.WorkflowValidationHistoriqueDTO;
import sn.webg.gestionrisque.services.WorkflowValidationHistoriqueService;

import java.util.Map;

@RestController
@RequestMapping("/workflow-validation-historiques")
@Tag(name = "workflow-validation-historiques-controller", description = "workflow validation historiques controller")
@RequiredArgsConstructor
public class WorkflowValidationHistoriqueController {
    private final WorkflowValidationHistoriqueService historiqueService;

//    @Operation(summary = "Create historique", description = "this endpoint take input historique and save it")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the historique was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public WorkflowValidationHistoriqueDTO createHistorique(@RequestBody WorkflowValidationHistoriqueDTO historique) {
//        return historiqueService.createHistorique(historique);
//    }

//    @PutMapping("/{historiqueId}")
//    @ResponseStatus(HttpStatus.OK)
//    public WorkflowValidationHistoriqueDTO updateHistorique(@Parameter(name = "historiqueId", description = "the historique id to updated") @PathVariable Long historiqueId, @RequestBody WorkflowValidationHistoriqueDTO historique) {
//        historique.setId(historiqueId);
//        return historiqueService.updateHistorique(historique);
//    }

    @Operation(summary = "Read the historique", description = "This endpoint is used to read historique, it take input id historique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{historiqueId}")
    @ResponseStatus(HttpStatus.OK)
    public WorkflowValidationHistoriqueDTO readHistorique(@Parameter(name = "historiqueId", description = "the type historique id to read") @PathVariable Long historiqueId) {
        return historiqueService.readHistorique(historiqueId);
    }

    @Operation(summary = "delete the historique", description = "Delete historique, it take input id historique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the historique was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{historiqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHistorique(@Parameter(name = "historiqueId", description = "the historique id deleted") @PathVariable Long historiqueId) {
        historiqueService.deleteHistorique(historiqueId);
    }

    @Operation(summary = "Read all historique", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<WorkflowValidationHistoriqueDTO> readAllHistorique(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return historiqueService.readAllHistorique(searchParams, page, size);
    }
}
