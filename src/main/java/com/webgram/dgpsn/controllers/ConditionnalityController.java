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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.ConditionnalityDTO;
import com.webgram.dgpsn.services.ConditionnalityService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/conditionnalities")
@Tag(name = "conditionnality-controller", description = "Conditionnality controller")
@RequiredArgsConstructor
public class ConditionnalityController {
    private final ConditionnalityService conditionnalityService;


    @Operation(summary = "Create conditionnality", description = "this endpoint take input conditionnality and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConditionnalityDTO createStatus(@RequestBody ConditionnalityDTO conditionnality) {
        return conditionnalityService.create(conditionnality);
    }

    @PutMapping("/{conditionnalityId}")
    @ResponseStatus(HttpStatus.OK)
    public ConditionnalityDTO updateConditionnality(
            @Parameter(name = "conditionnalityId", description = "the conditionnality type id updated") @PathVariable Long conditionnalityId
            , @RequestBody ConditionnalityDTO conditionnality) {
        conditionnality.setId(conditionnalityId);

        return conditionnalityService.update(conditionnality);
    }

    @Operation(summary = "Read the conditionnality", description = "This endpoint is used to read conditionnality  it take input id conditionnality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{conditionnalityId}")
    @ResponseStatus(HttpStatus.OK)
    public ConditionnalityDTO readcConditionnality(
            @Parameter(name = "conditionnalityId", description = "the conditionnality id to read") @PathVariable Long conditionnalityId) {
        return  conditionnalityService.read(conditionnalityId);
    }

    @Operation(summary = "delete the conditionnality", description = "Delete conditionnality, it take input   id conditionnality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{conditionnalityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConditionnality(@Parameter(name = "conditionnalityId", description = "the conditionnality id deleted") @PathVariable Long conditionnalityId) {
        conditionnalityService.delete(conditionnalityId);
    }

    @Operation(summary = "Read all conditionnality", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ConditionnalityDTO> readAllConditionnalities(
            Pageable pageable,
            @Parameter(name = "projetId", description = "value of projet used to filter list conditionnality") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "libelle", description = "value of libelle used to filter list conditionnality") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "conditionnalityTypeId", description = "value of conditionnalityTypeId used to filter list conditionnality") @RequestParam(value = "conditionnalityTypeId", required = false) Long conditionnalityTypeId,
            @Parameter(name = "stateProgressId", description = "value of stateProgressId used to filter list conditionnality") @RequestParam(value = "stateProgressId", required = false) Long stateProgressId,
            @Parameter(name = "agentId", description = "value of agentId used to filter list conditionnality") @RequestParam(value = "agentId", required = false) Long agentId,
            @Parameter(name = "deadline", description = "value of deadline used to filter list conditionnality") @RequestParam(value = "deadline", required = false) Date deadline
    ) {
        return conditionnalityService.readAll(pageable, libelle, conditionnalityTypeId,stateProgressId,agentId, projetId,deadline);
    }

    @Operation(summary = "Import conditionnality", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importConditionnality(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        conditionnalityService.importConditionnalite(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Conditionnalite%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        conditionnalityService.exportConditionnalite(response.getWriter());
    }
}
