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
import com.webgram.dgpsn.models.RecommandationDTO;
import com.webgram.dgpsn.services.RecommandationService;


@RestController
@RequestMapping("/recommendations")
@Tag(name = "recommendation-controller", description = "Recommendation controller")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommandationService recommandationService;

    @Operation(summary = "Create recommendation", description = "this endpoint take input recommendation and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the recommendation was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecommandationDTO createRecommendation(@RequestBody RecommandationDTO recommandationDTO) {
        return recommandationService.create(recommandationDTO);
    }

    @PutMapping("/{recommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public RecommandationDTO updateRecommendation(
            @Parameter(name = "recommendationId", description = "the recommendation type id updated") @PathVariable Long recommendationId,
            @RequestBody RecommandationDTO recommandationDTO) {
        recommandationDTO.setId(recommendationId);
        return recommandationService.update(recommandationDTO);
    }

    @Operation(summary = "Read the recommendation", description = "This endpoint is used to read recommendation  it take input id recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{recommendationId}")
    @ResponseStatus(HttpStatus.OK)
    public RecommandationDTO readcRecommendation(
            @Parameter(name = "recommendationId", description = "the recommendation id to read") @PathVariable Long recommendationId) {
        return recommandationService.read(recommendationId);
    }

    @Operation(summary = "delete the recommendation", description = "Delete recommendation, it take input   id recommendation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the recommendation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{recommendationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecommendation(@Parameter(name = "recommendationId", description = "the recommendation id deleted") @PathVariable Long recommendationId) {
        recommandationService.delete(recommendationId);
    }

    @Operation(summary = "Read all recommendation", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RecommandationDTO> readRecommendations(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list recommendation") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "responsable", description = "value of responsable used to filter list recommendation") @RequestParam(value = "responsable", required = false) String responsable,
            @Parameter(name = "deadline", description = "value of deadline used to filter list recommendation") @RequestParam(value = "deadline", required = false) String deadline,
            @Parameter(name = "issueLogId", description = "value of issueLog used to filter list recommendation") @RequestParam(value = "issueLogId", required = false) Long issueLogId,
            @Parameter(name = "riskId", description = "value of risk used to filter list recommendation") @RequestParam(value = "riskId", required = false) Long riskId,
            @Parameter(name = "assignmentId", description = "value of assignment used to filter list recommendation") @RequestParam(value = "assignmentId", required = false) Long assignmentId,
            @Parameter(name = "statusId", description = "value of v used to filter list statusId") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "projectId", description = "value of projectId used to filter list recommendation") @RequestParam(value = "projectId", required = false) Long projectId
    ) {
        return recommandationService.readAll(pageable, libelle, responsable, deadline, issueLogId, riskId, assignmentId, statusId, projectId);
    }

}
