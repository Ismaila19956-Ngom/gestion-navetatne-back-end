package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.PassationMarketCritereDTO;
import com.webgram.dgpsn.services.PassationMarketCritereService;

import java.util.List;

@RestController
@RequestMapping("/criteremarket")
@Tag(name = "Market-critere-controller", description ="Market-critere-evaluation controller")
@RequiredArgsConstructor
public class PassationMarketCritereController {

    private final PassationMarketCritereService passationMarketCritereService;

    @Operation(summary = "Create critere-evaluation-market", description = "this endpoint take input critere-evaluation-market and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<PassationMarketCritereDTO> createPassationPlan(@RequestBody List<PassationMarketCritereDTO> passationMarketCritereDTO) {
        return passationMarketCritereService.createPassationMarketCritere(passationMarketCritereDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassationMarketCritereDTO updatePassationplan(@Parameter(name = "id", description = "the structure id updated") @PathVariable Long id,
                                                  @RequestBody PassationMarketCritereDTO passationMarketDTO) {
        System.out.println("ID: "+ id);
        passationMarketDTO.setId(id);
        return passationMarketCritereService.updatePassationMarketCritere(passationMarketDTO);
    }

    @Operation(summary = "Read the critere-market", description = "This endpoint is used to read critere-market it take input id critereMarketId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassationMarketCritereDTO readPassationPlan(@Parameter(name = "id", description = "the critere Market Id  to read") @PathVariable Long id) {
        return passationMarketCritereService.readPassationMarketCritere(id);
    }

    @Operation(summary = "Read the critere-market", description = "Voir tous les criteres d'evaluation d'un marches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("marketAll/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PassationMarketCritereDTO> readMarketPerId(@Parameter(name = "id", description = "the critere Market Id  to read") @PathVariable Long id) {
        return passationMarketCritereService.readAllCriterePerMarket(id);
    }


    @Operation(summary = "delete the passation-plan", description = "Delete the passation-plan, it take input id the passation-plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassationplan(@Parameter(name = "id", description = "the passation-plan id deleted") @PathVariable Long id) {
        passationMarketCritereService.deletePassationMarketCritere(id);
    }

    @Operation(summary = "Read all passation-plan-market", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PassationMarketCritereDTO> readAllPassationplanMarket(
            Pageable pageable,
            @Parameter(name = "ponderation", description = "value of label used to filter list critere-market") @RequestParam(value = "ponderation", required = false) Double ponderation,
            @Parameter(name = "expectedValue", description = "value of label used to filter list critere-market") @RequestParam(value = "expectedValue", required = false) String expectedValue,
            @Parameter(name = "passationMarketId", description = "value of label used to filter list critere-market") @RequestParam(value = "passationMarketId", required = false) Long passationMarketId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list critere-market") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list critere-market") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return passationMarketCritereService.readAllPassationMarketCritere(pageable,ponderation, expectedValue ,passationMarketId,sortBy, ascending);
    }


}
