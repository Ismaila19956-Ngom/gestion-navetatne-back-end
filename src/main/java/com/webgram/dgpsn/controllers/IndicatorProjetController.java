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
import com.webgram.dgpsn.models.IndicatorProjetDTO;
import com.webgram.dgpsn.services.IndicatorProjetService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/indicator-project")
@Tag(name = "indicator-project", description = "indicator project")
@RequiredArgsConstructor
public class IndicatorProjetController {
    private final IndicatorProjetService indicatorProjetService;

    @Operation(summary = "Create indicator project", description = "this endpoint take input indicator project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type indicator projet was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IndicatorProjetDTO addIndicatorToProject(@RequestBody IndicatorProjetDTO indicatorProjetDTO) {
        return indicatorProjetService.create(indicatorProjetDTO);
    }

    @PutMapping("/{indicatorProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public IndicatorProjetDTO updateindicatorProject(@Parameter(name = "indicatorProjectId", description = "the indicatorProjectId updated") @PathVariable Long indicatorProjectId, @RequestBody IndicatorProjetDTO indicatorProjetDTO) {
        indicatorProjetDTO.setId(indicatorProjectId);
        return indicatorProjetService.create(indicatorProjetDTO);
    }

    @Operation(summary = "Read the indicatorProject", description = "This endpoint is used to read partner project it take input id indicator project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{indicatorProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public IndicatorProjetDTO readIndicatorProject(@Parameter(name = "indicatorProjectId", description = "the type indicatorProject id to read") @PathVariable Long indicatorProjectId) {
        return indicatorProjetService.read(indicatorProjectId);
    }

    @Operation(summary = "delete the indicatorProjectId", description = "Delete indicatorProjectId, it take input id partnerProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{indicatorProjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndicatorProject(@Parameter(name = "indicatorProjectId", description = "the indicatorProjectId id deleted") @PathVariable Long indicatorProjectId) {
        indicatorProjetService.delete(indicatorProjectId);
    }

    @Operation(summary = "Read all indicatorProjectId", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<IndicatorProjetDTO> readAllIndicatorByProject(
            Pageable pageable,
            @Parameter(name = "targetValue", description = "value of targetValue used to filter list partnerProject") @RequestParam(value = "targetValue", required = false) Double targetValue,
            @Parameter(name = "indicatorId", description = "value of indicatorId used to filter list partnerProject") @RequestParam(value = "indicatorId", required = false) Long indicatorId,
            @Parameter(name = "projetId", description = "value of projetId used to filter list partnerProject") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "periodicityId", description = "value of periodicityId used to filter list partnerProject") @RequestParam(value = "periodicityId", required = false) Long periodicityId
    ) {
        return indicatorProjetService.readAll(pageable, targetValue, indicatorId, projetId, periodicityId);
    }

    @Operation(summary = "Import IndicatorProjet", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importIndicatorProjet(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        indicatorProjetService.importIndicator(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Indicator%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        indicatorProjetService.exportIndicator(response.getWriter());
    }
}
