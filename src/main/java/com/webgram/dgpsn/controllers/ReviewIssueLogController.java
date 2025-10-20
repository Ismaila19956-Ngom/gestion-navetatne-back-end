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
import com.webgram.dgpsn.models.requests.ReviewIssueLogDTO;
import com.webgram.dgpsn.models.requests.ReviewIssueLogListDTO;
import com.webgram.dgpsn.services.ReviewIssueLogService;

import java.util.Date;

@RestController
@RequestMapping("/reviewIssuelogs")
@Tag(name = "reviewIssuelog-controller", description = "ReviewIssuelog controller")
@RequiredArgsConstructor
public class ReviewIssueLogController {
    private final ReviewIssueLogService reviewIssueLogService;
    @Operation(summary = "Create reviewIssuelog", description = "this endpoint take input reviewIssuelog and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewIssuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReviewIssuelogs(@RequestBody ReviewIssueLogListDTO reviewIssueLogListDTO) {
       reviewIssueLogService.create(reviewIssueLogListDTO);
    }

    @Operation(summary = "Create reviewIssuelog", description = "this endpoint take input reviewIssuelog and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewIssuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReviewIssuelog(@RequestBody ReviewIssueLogDTO reviewIssueLogDTO) {
       reviewIssueLogService.create(reviewIssueLogDTO);
    }

    @Operation(summary = "delete the reviewIssuelog", description = "Delete reviewIssuelog, it take input   id reviewIssuelog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewIssuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{reviewIssuelogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReviewIssuelog(@Parameter(name = "reviewIssuelogId", description = "the reviewIssuelog id deleted") @PathVariable Long reviewIssuelogId) {
        reviewIssueLogService.delete(reviewIssuelogId);
    }

    @Operation(summary = "Read all reviewIssuelog", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ReviewIssueLogDTO> readReviewIssuelogs(
            Pageable pageable,
            @Parameter(name = "issueLogId", description = "value of issueLog used to filter list reviewIssuelog") @RequestParam(value = "issueLogId", required = false) Long issueLogId,
            @Parameter(name = "reviewId", description = "value of review used to filter list reviewIssuelog") @RequestParam(value = "reviewId", required = false) Long reviewId,
            @Parameter(name = "libelle", description = "value of libelle used to filter list reviewIssuelog") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "identificationDate", description = "value of identificationDate used to filter list reviewIssuelog") @RequestParam(value = "identificationDate", required = false) Date identificationDate,
            @Parameter(name = "resolutionDate", description = "value of resolutionDate used to filter list reviewIssuelog") @RequestParam(value = "resolutionDate", required = false) Date resolutionDate,
            @Parameter(name = "criticityId", description = "value of criticityId used to filter list reviewIssuelog") @RequestParam(value = "criticityId", required = false) Long criticityId,
            @Parameter(name = "statusId", description = "value of statusId used to filter list reviewIssuelog") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "natureId", description = "value of natureId used to filter list reviewIssuelog") @RequestParam(value = "natureId", required = false) Long natureId

    ) {
        return reviewIssueLogService.readAll(pageable, reviewId, issueLogId,libelle, identificationDate, resolutionDate, criticityId, statusId, natureId);
    }
}
