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
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.models.StatusDTO;
import com.webgram.dgpsn.services.StatusService;

@RestController
@RequestMapping("/status")
@Tag(name = "status-controller", description = "Status controller")
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @Operation(summary = "Create status", description = "this endpoint take input status and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusDTO createStatus(@RequestBody StatusDTO status) {
        return statusService.createStatus(status);
    }

    @PutMapping("/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public StatusDTO updateStatus(@Parameter(name = "statusId", description = "the status type id updated") @PathVariable Long statusId, @RequestBody StatusDTO status) {
        status.setId(statusId);
        return statusService.updateStatus(status);
    }

    @Operation(summary = "Read the status", description = "This endpoint is used to read status  it take input id status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{statusId}")
    @ResponseStatus(HttpStatus.OK)
    public StatusDTO readStatus(@Parameter(name = "statusId", description = "the status id to read") @PathVariable Long statusId) {
        return statusService.readStatus(statusId);
    }

    @Operation(summary = "delete the status", description = "Delete status, it take input   id status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{statusId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatus(@Parameter(name = "statusId", description = "the status id deleted") @PathVariable Long statusId) {
        statusService.deleteStatus(statusId);
    }

    @Operation(summary = "Read all status", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<StatusDTO> readAllStatus(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list status") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list status") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "statusType", description = "value of statusType used to filter list status") @RequestParam(value = "statusType", required = false) StatusType statusType,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {

        return statusService.readAllStatus(pageable, code, libelle,statusType,sortBy,ascending);

    }


}
