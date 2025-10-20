package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.StatusQueryDTO;
import com.webgram.dgpsn.services.StatusQueryService;

import java.util.Date;

@RestController
@RequestMapping("/statusQueries")
@Tag(name = "statusQuery-controller", description = "StatusQuery controller")
@RequiredArgsConstructor
public class StatusQueryController {
    private final StatusQueryService statusQueryService;

    @Operation(summary = "Create statusQuery", description = "this endpoint take input statusQuery and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusQueryDTO createQuery(@RequestBody StatusQueryDTO statusQuery) {
        return statusQueryService.createStatusQuery(statusQuery);
    }

    @PutMapping("/{statusQueryId}")
    @ResponseStatus(HttpStatus.OK)
    public StatusQueryDTO updateStatusQuery(
            @Parameter(name = "statusQueryId", description = "the statusQuery type id updated") @PathVariable Long statusQueryId
            , @RequestBody StatusQueryDTO statusQuery) {
        statusQuery.setId(statusQueryId);
        return statusQueryService.updateStatusQuery(statusQuery);
    }

    @Operation(summary = "Read the statusQuery", description = "This endpoint is used to read statusQuery  it take input id statusQuery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{statusQueryId}")
    @ResponseStatus(HttpStatus.OK)
    public StatusQueryDTO readcQuery(
            @Parameter(name = "statusQueryId", description = "the statusQuery id to read") @PathVariable Long statusQueryId) {
        return statusQueryService.readStatusQuery(statusQueryId);
    }

    @Operation(summary = "delete the statusQuery", description = "Delete statusQuery, it take input   id statusQuery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{statusQueryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatusQuery(@Parameter(name = "statusQueryId", description = "the statusQuery id deleted") @PathVariable Long statusQueryId) {
        statusQueryService.deleteStatusQuery(statusQueryId);
    }

    @Operation(summary = "Read all statusQuery", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<StatusQueryDTO> readAlstatusQueries(
            Pageable pageable,
            @Parameter(name = "queryId", description = "value of query used to filter list status query") @RequestParam(value = "queryId", required = false) Long queryId,
            @Parameter(name = "libelle", description = "value of query used to filter list status query") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "responsable", description = "value of query used to filter list status query") @RequestParam(value = "responsable", required = false) String responsable,
            @Parameter(name = " identificationDate", description = "value of query used to filter list status query") @RequestParam(value = "identificationDate", required = false) Date identificationDate,
            @Parameter(name = "deadline", description = "value of query used to filter list status query") @RequestParam(value = "deadline", required = false) Date deadline
    ) {
        var pageable1 = PageRequest.of(0,1000000, Sort.by("identificationDate").descending());
        return statusQueryService.readAllStatusQuery(pageable1, libelle, responsable,identificationDate,deadline, queryId);

    }

}
