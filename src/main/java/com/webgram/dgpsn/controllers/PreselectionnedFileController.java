package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.webgram.dgpsn.models.PreselectionnedFileDTO;
import com.webgram.dgpsn.services.PreselectionnedFileService;

import java.util.List;

@RestController
@RequestMapping("/preselectiondfile")
@Tag(name = "Preselectionned-file-controller", description ="Preselectionned file controller")
@RequiredArgsConstructor
public class PreselectionnedFileController {
    private final PreselectionnedFileService preselectionnedFileService;

    private final ObjectMapper objectMapper;

    @Operation(summary = "Create preselectionned-file", description = "this endpoint take input preselectionned-file and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<PreselectionnedFileDTO> createPassationPlan(@RequestBody List<PreselectionnedFileDTO> preselectionnedFileDTO) {
        for (PreselectionnedFileDTO preselectionnedFileDTO1:preselectionnedFileDTO) {
            preselectionnedFileDTO1.setWinner(false);

        }
        return preselectionnedFileService.createPreselectionnedFile(preselectionnedFileDTO);
    }

    @PutMapping("/{preselectiondId}")
    @ResponseStatus(HttpStatus.OK)
    public PreselectionnedFileDTO updatePassationplan(@Parameter(name = "preselectiondId", description = "the preselectionned file id updated") @PathVariable Long preselectiondId,
                                        @RequestBody PreselectionnedFileDTO preselectionnedFileDTO) {
        preselectionnedFileDTO.setId(preselectiondId);
        return preselectionnedFileService.updatePreselectionnedFile(preselectionnedFileDTO);
    }

    @Operation(summary = "Read the preselectionned file", description = "This endpoint is used to read preselectionned file it take input id preselectionnedFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{preselectiondId}")
    @ResponseStatus(HttpStatus.OK)
    public PreselectionnedFileDTO readPassationPlan(@Parameter(name = "preselectiondId", description = "the passation id to read") @PathVariable Long preselectiondId) {
        return preselectionnedFileService.readPreselectionnedFile(preselectiondId);
    }

    @Operation(summary = "delete the preselectionned File", description = "Delete the preselectionned file, it take input id the preselctionned file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{preselectiondId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePassationplan(@Parameter(name = "preselectiondId", description = "the passation-plan id deleted") @PathVariable Long preselectiondId) {
        preselectionnedFileService.deletePreselectionnedFile(preselectiondId);
    }

    @Operation(summary = "Read all preselectionned File", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<PreselectionnedFileDTO> readAllPassationplan(
            Pageable pageable,
            @Parameter(name = "passationMarketId", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "passationMarketId", required = false) Long passationMarketId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list passation-plan") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return preselectionnedFileService.readAllPreselectionnedFile(pageable,passationMarketId,sortBy, ascending);
    }




}
