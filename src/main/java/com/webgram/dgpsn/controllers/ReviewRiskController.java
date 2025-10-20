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

import com.webgram.dgpsn.models.ReviewRiskDTO;
import com.webgram.dgpsn.services.ReviewRiskService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reviewRisks")
@Tag(name = "reviewRisk-controller", description = "ReviewRisk controller")
@RequiredArgsConstructor
public class ReviewRiskController {
    private final ReviewRiskService reviewRiskService;

    @Operation(summary = "Create reviewRisk", description = "this endpoint take input reviewRisk and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewIssuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAllReviewRisk(@RequestBody List<ReviewRiskDTO> reviewRiskDTO) {
        reviewRiskService.createAllReviewRisk(reviewRiskDTO);
    }

    @Operation(summary = "Create reviewRisk", description = "this endpoint take input reviewRisk and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewRisk was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public void createReviewRisk(@RequestBody ReviewRiskDTO reviewRiskDTO) {
        reviewRiskService.createReviewRisk(reviewRiskDTO);
    }

    @Operation(summary = "delete the reviewRisk", description = "Delete reviewRisk, it take input   id reviewRisk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the reviewRisk was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{reviewRiskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReviewRisk(@Parameter(name = "reviewRiskId", description = "the reviewRisk id deleted") @PathVariable Long reviewRiskId) {

        reviewRiskService.deleteReview(reviewRiskId);
    }

    @Operation(summary = "Read all reviewIssuelog", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ReviewRiskDTO> readReviewRisks(
            Pageable pageable,
            @Parameter(name = "riskId", description = "value of risk used to filter list reviewRisk") @RequestParam(value = "riskId", required = false) Long riskId,
            @Parameter(name = "reviewId", description = "value of review used to filter list reviewRisk") @RequestParam(value = "reviewId", required = false) Long reviewId,
            @Parameter(name = "libelle", description = "value of libelle used to filter list reviewIssuelog") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "identificationDate", description = "value of identificationDate used to filter list reviewIssuelog") @RequestParam(value = "identificationDate", required = false) Date identificationDate,
            @Parameter(name = "resolutionDate", description = "value of resolutionDate used to filter list reviewIssuelog") @RequestParam(value = "resolutionDate", required = false) Date resolutionDate,
            @Parameter(name = "criticityId", description = "value of criticityId used to filter list reviewIssuelog") @RequestParam(value = "criticityId", required = false) Long criticityId,
            @Parameter(name = "statusId", description = "value of statusId used to filter list reviewIssuelog") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "natureId", description = "value of natureId used to filter list reviewIssuelog") @RequestParam(value = "natureId", required = false) Long natureId
    ) {
        return reviewRiskService.readAllReviewRisk(pageable, reviewId, riskId, libelle, identificationDate, resolutionDate, criticityId, statusId, natureId);
    }

}
