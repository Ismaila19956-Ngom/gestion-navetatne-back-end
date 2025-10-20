package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.IssueLogActionRealizedDTO;
import com.webgram.dgpsn.services.IssueLogActionRealizedService;

import java.util.List;

@RestController
@RequestMapping("/issueLogActionRealized")
@Tag(name = "issueLogActionRealized-controller", description = "issueLogActionRealized controller")
@RequiredArgsConstructor
public class IssueLogActionRealizedController {
    private final IssueLogActionRealizedService issueLogActionRealizedService;

    @Operation(summary = "Create issueLogActionRealized", description = "this endpoint take input issueLogActionRealized and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type country was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueLogActionRealizedDTO createIssueLogActionRealized(@RequestBody IssueLogActionRealizedDTO issueLogActionRealizedDTO) {
        return issueLogActionRealizedService.create(issueLogActionRealizedDTO);
    }

    @PutMapping("/{issueLogActionRealizedId}")
    @ResponseStatus(HttpStatus.OK)
    public IssueLogActionRealizedDTO updateIssueLogActionRealized(@Parameter(name = "issueLogActionRealizedId", description = "the issueLogActionRealized id to updated") @PathVariable Long issueLogActionRealizedId, @RequestBody IssueLogActionRealizedDTO issueLogActionRealizedDTO) {
        issueLogActionRealizedDTO.setId(issueLogActionRealizedId);
        return issueLogActionRealizedService.update(issueLogActionRealizedDTO);
    }

    @Operation(summary = "Read the IssueLogActionRealizedId", description = "This endpoint is used to read issueLogActionRealized, it take input id issueLogActionRealized")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{issueLogActionRealizedId}")
    @ResponseStatus(HttpStatus.OK)
    public IssueLogActionRealizedDTO readIssueLogActionRealized(@Parameter(name = "issueLogActionRealizedId", description = "the type IssueLogActionRealized id to read") @PathVariable Long issueLogActionRealizedId) {
        return issueLogActionRealizedService.read(issueLogActionRealizedId);
    }

    @Operation(summary = "delete the issueLogActionRealized", description = "Delete issueLogActionRealized, it take input id issueLogActionRealized")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the action was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{issueLogActionRealizedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssueLogActionRealized(@Parameter(name = "issuelogStructure", description = "the issuelogStructure id deleted") @PathVariable Long issueLogActionRealizedId) {
        issueLogActionRealizedService.delete(issueLogActionRealizedId);
    }

    @Operation(summary = "Read the issueLogActionRealized by issuelog", description = "This endpoint is used to read issueLogActionRealized, it take input id issuelogId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @GetMapping("/byIssuelog/{issuelogId}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueLogActionRealizedDTO> readByIssuelog(@Parameter(name = "issuelogId", description = "the type issuelogId id to read") @PathVariable Long issuelogId) {
        return issueLogActionRealizedService.readByIssueLog(issuelogId);
    }
}
