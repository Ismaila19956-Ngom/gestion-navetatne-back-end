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
import com.webgram.dgpsn.models.PassationMarketDTO;
import com.webgram.dgpsn.services.PassationMarketService;

@RestController
@RequestMapping("/passationmarket")
@Tag(name = "Plan-de-passation-market-controller", description ="Plan de passation market  controller")
@RequiredArgsConstructor
public class PassationMarketController {

    private final PassationMarketService passationMarketService;

    @Operation(summary = "Create passation-plan-market", description = "this endpoint take input passation-plan-market and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassationMarketDTO createPassationPlan(@RequestBody PassationMarketDTO passationMarketDTO) {
        return passationMarketService.createPassationMarket(passationMarketDTO);
    }

    @PutMapping("/{passationMarketId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationMarketDTO updatePassationplan(@Parameter(name = "passationMarketId", description = "the structure id updated") @PathVariable Long passationMarketId,
                                        @RequestBody PassationMarketDTO passationMarketDTO) {
        passationMarketDTO.setId(passationMarketId);
        return passationMarketService.updatePassationMarket(passationMarketDTO);
    }

    @Operation(summary = "Read the passation-plan", description = "This endpoint is used to read passation-plan it take input id passationPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{passationMarketId}")
    @ResponseStatus(HttpStatus.OK)
    public PassationMarketDTO readPassationPlan(@Parameter(name = "passationMarketId", description = "the passation market id to read") @PathVariable Long passationMarketId) {
        return passationMarketService.readPassationMarket(passationMarketId);
    }

    @Operation(summary = "delete the passation-plan", description = "Delete the passation-plan, it take input id the passation-plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{passationMarketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassationplan(@Parameter(name = "passationMarketId", description = "the passation-plan-market id deleted") @PathVariable Long passationMarketId) {
        passationMarketService.deletePassationMarket(passationMarketId);
    }

    @Operation(summary = "Read all passation-plan-market", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PassationMarketDTO> readAllPassationplanMarket(
            Pageable pageable,
            @Parameter(name = "reference", description = "value of label used to filter list passation-plan") @RequestParam(value = "reference", required = false) String reference,
            @Parameter(name = "libelle", description = "value of label used to filter list passation-plan") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "passationPlanId", description = "value of label used to filter list passation-plan") @RequestParam(value = "passationPlanId", required = false) Long passationPlanId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list passation-plan") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return passationMarketService.readAllPassationMarket(pageable, reference, libelle ,passationPlanId,sortBy, ascending);
    }




}
