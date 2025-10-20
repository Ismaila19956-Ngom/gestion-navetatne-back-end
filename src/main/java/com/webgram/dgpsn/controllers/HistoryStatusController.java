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
import com.webgram.dgpsn.models.HistoryStatusDTO;
import com.webgram.dgpsn.services.HistoryStatusService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/histories-status")
@Tag(name = "histories-status-controller", description = "histories-status controller")
@RequiredArgsConstructor
public class HistoryStatusController {
    private final HistoryStatusService historyStatusService;

    @Operation(summary = "Create history", description = "this endpoint take input history status and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryStatusDTO createHistoryStatus(@RequestBody HistoryStatusDTO historyStatus) {
        return historyStatusService.create(historyStatus);
    }

    @Operation(summary = "Update history", description = "this endpoint take input history status and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{historyStatusId}")
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryStatusDTO updateHistoryStatus(@PathVariable Long historyStatusId, @RequestBody HistoryStatusDTO historyStatus) {
        historyStatus.setId(historyStatusId);
        return historyStatusService.update(historyStatus);
    }

    @Operation(summary = "Delete history", description = "this endpoint take input history statusId and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{historyStatusId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteHistoryStatus(@PathVariable Long historyStatusId) {
        historyStatusService.delete(historyStatusId);
    }

    @Operation(summary = "Read all history status", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<HistoryStatusDTO> readAllHistoriesEntities(
            Pageable pageable,
            @Parameter(name = "startDate", description = "value of startDate used to filter list partnerProject") @RequestParam(value = "startDate", required = false) String startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list partnerProject") @RequestParam(value = "endDate", required = false) String endDate,
            @Parameter(name = "statusId", description = "value of statusId used to filter list partnerProject") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "projectId", description = "value of projectId used to filter list partnerProject") @RequestParam(value = "projectId", required = false) Long projectId
    ) throws ParseException {
        return historyStatusService.readAll(pageable, startDate, endDate, statusId, projectId);
    }

    @Operation(summary = "Read by statusCode", description = "It take param code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/byStatusLibelle")
    public List<HistoryStatusDTO> readByStatusLibelle(@RequestParam String libelle) {
        return historyStatusService.readStatusProjectByLibelle(libelle);
    }
}
