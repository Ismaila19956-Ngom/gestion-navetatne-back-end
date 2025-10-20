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
import com.webgram.dgpsn.models.HistoryFlagDTO;
import com.webgram.dgpsn.services.HistoryFlagService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/histories-flag")
@Tag(name = "histories-flag-controller", description = "histories-flag controller")
@RequiredArgsConstructor
public class HistoryFlagController {
    private final HistoryFlagService historyFlagService;

    @Operation(summary = "Create history flag", description = "this endpoint take input histories flag and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryFlagDTO createHistoryFlag(@RequestBody HistoryFlagDTO historyFlagDTO) {
        return historyFlagService.create(historyFlagDTO);
    }

    @Operation(summary = "Update history", description = "this endpoint take input history status and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{historyFlagId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryFlagDTO updateHistoryFlag(@PathVariable Long historyFlagId, @RequestBody HistoryFlagDTO historyFlagDTO) {
        historyFlagDTO.setId(historyFlagId);
        return historyFlagService.update(historyFlagDTO);
    }

    @Operation(summary = "Delete history", description = "this endpoint take input history statusId and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{historyFlagId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteHistoryFlag(@PathVariable Long historyFlagId) {
        historyFlagService.delete(historyFlagId);
    }

    @Operation(summary = "Read all history flag", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<HistoryFlagDTO> readAllHistoriesFlags(
            Pageable pageable,
            @Parameter(name = "startDate", description = "value of startDate used to filter list partnerProject") @RequestParam(value = "startDate", required = false) String startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list partnerProject") @RequestParam(value = "endDate", required = false) String endDate,
            @Parameter(name = "flagId", description = "value of flagId used to filter list partnerProject") @RequestParam(value = "flagId", required = false) Long flagId,
            @Parameter(name = "projectId", description = "value of projectId used to filter list partnerProject") @RequestParam(value = "projectId", required = false) Long projectId
    )throws ParseException {
        return historyFlagService.readAll(pageable, startDate, endDate, flagId, projectId);
    }

    @Operation(summary = "Read by flagCode", description = "It take param code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/byFlagCode")
    public List<HistoryFlagDTO> readByFlagCode(@RequestParam String code) {
        return historyFlagService.readByFlagCode(code);
    }

}
