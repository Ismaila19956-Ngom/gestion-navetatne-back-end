package com.webgram.dgpsn.controllers;

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
import com.webgram.dgpsn.entities.enums.Period;
import com.webgram.dgpsn.models.CompletionRateDTO;
import com.webgram.dgpsn.models.responses.CompletionRateResponse;
import com.webgram.dgpsn.services.CompletionRateService;

import java.util.Date;

@RestController
@RequestMapping("/completion-rates")
@Tag(name = "completion-rates-controller", description = "completion rate controller")
@RequiredArgsConstructor
public class CompletionRateController {
    private final CompletionRateService completionRateService;

    @Operation(summary = "Create completion-rate", description = "this endpoint take input completion-rates and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the completion-rate was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletionRateDTO createCompletionRate(@RequestBody CompletionRateDTO completionRateDTO) {
        return completionRateService.create(completionRateDTO);
    }

    @Operation(summary = "Update completionRate", description = "this endpoint take input completion rate and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the completion rate was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{completionRateId}")
    @ResponseStatus(HttpStatus.OK)
    public CompletionRateDTO updateCompletionRate(@Parameter(name = "completionRateId", description = "the completionRate id updated") @PathVariable Long completionRateId,@RequestBody CompletionRateDTO completionRateDTO) {
        completionRateDTO.setId(completionRateId);
        return completionRateService.update(completionRateDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the completion rate was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{completionRateId}")
    @ResponseStatus(HttpStatus.OK)
    public CompletionRateDTO readCompletionRate(@Parameter(name = "completionRateId", description = "the completion rate id to read") @PathVariable Long completionRateId) {
        return completionRateService.read(completionRateId);
    }

    @Operation(summary = "delete the completion rate", description = "Delete completion rate, it take input   id completion rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the completion rate was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{completionRateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompletionRate(@Parameter(name = "completionRateId", description = "the completion rate id deleted") @PathVariable Long completionRateId) {
        completionRateService.delete(completionRateId);
    }

    @Operation(summary = "Read all completion rate", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CompletionRateDTO> readAllCompletionRates(
            Pageable pageable,
            @Parameter(name = "managementUnitId", description = "value of managementUnitId used to filter list completion rate") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId,
            @Parameter(name = "year", description = "value of year used to filter list ompletion rate") @RequestParam(value = "year", required = false) Integer year,
            @Parameter(name = "period", description = "value of period used to filter list ompletion rate") @RequestParam(value = "period", required = false) Period period,
            @Parameter(name = "targetValue", description = "value of targetValue used to filter list ompletion rate") @RequestParam(value = "targetValue", required = false) Double targetValue,
            @Parameter(name = "valueReched", description = "value of valueReched used to filter list ompletion rate") @RequestParam(value = "valueReched", required = false) Double valueReched,
            @Parameter(name = "startDate", description = "value of startDate used to filter list ompletion rate") @RequestParam(value = "startDate", required = false) Date startDate,
            @Parameter(name = "endDate", description = "value of period used to filter list ompletion rate") @RequestParam(value = "endDate", required = false) Date endDate
    ) {
        return completionRateService.readAll(pageable, managementUnitId, period, targetValue, valueReched,year, startDate, endDate);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the completion rate was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{managementUnitId}/{year}")
    @ResponseStatus(HttpStatus.OK)
    public CompletionRateResponse readCompletionRateResponse(@Parameter(name = "managementUnitId", description = "the management unit id to read") @PathVariable Long managementUnitId,
                                                             @Parameter(name = "year", description = "year to read") @PathVariable Integer year) {
        return completionRateService.readCompletionRateByManagementUnit(managementUnitId, year);
    }



}
