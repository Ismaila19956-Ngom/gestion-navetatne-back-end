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
import com.webgram.dgpsn.models.ProlongationDTO;
import com.webgram.dgpsn.services.ProlongationService;

@RestController
@RequestMapping("/prolongations")
@Tag(name = "prolongation-controller", description = "prolongation controller")
@RequiredArgsConstructor
public class ProlongationController {
    private final ProlongationService prolongationService;

    @Operation(summary = "Create prolongation", description = "this endpoint take input prolongation and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type prolongation was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProlongationDTO create(@RequestBody ProlongationDTO prolongationDTO) {
        return prolongationService.create(prolongationDTO);
    }

    @PutMapping("/{prolongationId}")
    @ResponseStatus(HttpStatus.OK)
    public ProlongationDTO update(@Parameter(name = "prolongationId", description = "the prolongation id to updated") @PathVariable Long prolongationId, @RequestBody ProlongationDTO prolongationDTO) {
        prolongationDTO.setId(prolongationId);
        return prolongationService.update(prolongationDTO);
    }

    @Operation(summary = "Read the prolongation", description = "This endpoint is used to read prolongation, it take input id prolongation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the prolongation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{prolongationId}")
    @ResponseStatus(HttpStatus.OK)
    public ProlongationDTO read(@Parameter(name = "prolongationId", description = "the type prolongation id to read") @PathVariable Long prolongationId) {
        return prolongationService.read(prolongationId);
    }

    @Operation(summary = "delete the prolongation", description = "Delete prolongation, it take input id prolongation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the prolongation was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{prolongationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "prolongationId", description = "the prolongation id deleted") @PathVariable Long prolongationId) {
        prolongationService.delete(prolongationId);
    }

    @Operation(summary = "Read all prolongation", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProlongationDTO> readAll(
            Pageable pageable,
            @Parameter(name = "justification", description = "value of justification used to filter list prolongation") @RequestParam(value = "justification", required = false) String justification,
            @Parameter(name = "duration", description = "value of duration used to filter list prolongation") @RequestParam(value = "duration", required = false) Integer duration,
            @Parameter(name = "fundingId", description = "value of fundingId used to filter list prolongation") @RequestParam(value = "fundingId", required = false) Long fundingId
    ) {
        return prolongationService.readAll(pageable, justification, duration, fundingId);
    }

}
