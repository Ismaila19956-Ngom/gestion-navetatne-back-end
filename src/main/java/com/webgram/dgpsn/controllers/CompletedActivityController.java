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
import com.webgram.dgpsn.models.CompletedActivityDTO;
import com.webgram.dgpsn.services.CompletedActivityService;

import java.util.Date;

@RestController
@RequestMapping("/completedActivity")
@Tag(name = "completedActivity-controller", description = "completedActivity controller")
@RequiredArgsConstructor
public class CompletedActivityController {
    private final CompletedActivityService completedActivityService;

    @Operation(summary = "Create completedActivity", description = "this endpoint take input completedActivity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletedActivityDTO createCompletedActivity(@RequestBody CompletedActivityDTO completedActivityDTO) {
        return completedActivityService.create(completedActivityDTO);
    }

    @PutMapping("/{completedActivityId}")
    @ResponseStatus(HttpStatus.OK)
    public CompletedActivityDTO updateCompletedActivity(@Parameter(name = "categoryId", description = "the axePSE id to updated") @PathVariable Long completedActivityId, @RequestBody CompletedActivityDTO completedActivityDTO) {
        completedActivityDTO.setId(completedActivityId);
        return completedActivityService.update(completedActivityDTO);
    }

    @Operation(summary = "Read the completedActivity", description = "This endpoint is used to read completedActivity, it take input id completedActivity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{completedActivityId}")
    @ResponseStatus(HttpStatus.OK)
    public CompletedActivityDTO readCompletedActivity(@Parameter(name = "completedActivityId", description = "the completedActivityId id to read") @PathVariable Long completedActivityId) {
        return completedActivityService.read(completedActivityId);
    }

    @Operation(summary = "delete the completedActivity", description = "Delete category, it take input id completedActivity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the action was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{completedActivityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompletedActivity(@Parameter(name = "completedActivityId", description = "the completedActivity id deleted") @PathVariable Long completedActivityId) {
        completedActivityService.delete(completedActivityId);
    }

    @Operation(summary = "Read all completedActivity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CompletedActivityDTO> readAllCompletedActivity(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list completedActivity") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list completedActivity") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "dateDebut", description = "value of dateDebut used to filter list completedActivity") @RequestParam(value = "dateDebut", required = false) Date dateDebut,
            @Parameter(name = "dateFin", description = "value of dateFin used to filter list completedActivity") @RequestParam(value = "dateFin", required = false) Date dateFin,
            @Parameter(name = "issueLogId", description = "value of issueLogId used to filter list completedActivity") @RequestParam(value = "issueLogId", required = false) Long issueLogId
    ) {
        return completedActivityService.readAll(pageable, code, libelle, dateDebut, dateFin, issueLogId);
    }

}
