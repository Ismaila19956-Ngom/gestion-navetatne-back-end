package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.RiskDTO;
import com.webgram.dgpsn.services.RiskService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/risks")
@Tag(name = "risk-controller", description = "Risk controller")
@RequiredArgsConstructor
public class RiskController {
    private final RiskService riskService;

    @Operation(summary = "Create risk", description = "this endpoint take input risk and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the risk was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RiskDTO createRisk(@RequestBody RiskDTO risk) {
        return riskService.create(risk);
    }

    @PutMapping("/{riskId}")
    @ResponseStatus(HttpStatus.OK)
    public RiskDTO updateRisk(
            @Parameter(name = "riskId", description = "the risk type id updated") @PathVariable Long riskId
            , @RequestBody RiskDTO risk) {
        risk.setId(riskId);
        return riskService.update(risk);
    }

    @Operation(summary = "Read the risk", description = "This endpoint is used to read risk  it take input id risk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{riskId}")
    @ResponseStatus(HttpStatus.OK)
    public RiskDTO readcRisk(
            @Parameter(name = "riskId", description = "the risk id to read") @PathVariable Long riskId) {
        return riskService.read(riskId);
    }

    @Operation(summary = "delete the risk", description = "Delete risk, it take input   id risk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the risk was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{riskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRisk(@Parameter(name = "riskId", description = "the risk id deleted") @PathVariable Long riskId) {
        riskService.delete(riskId);
    }

    @Operation(summary = "Read all risk", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RiskDTO> readAllRisks(
            Pageable pageable,
            @Parameter(name = "projetId", description = "value of projet used to filter list risk") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "libelle", description = "value of libelle used to filter list risk") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "author", description = "value of author used to filter list risk") @RequestParam(value = "author", required = false) String author,
            @Parameter(name = "criticity", description = "value of criticity used to filter list risk") @RequestParam(value = "criticity", required = false) String criticity,
            @Parameter(name = "natureId", description = "value of natureId used to filter list risk") @RequestParam(value = "natureId", required = false) Long natureId,
            @Parameter(name = "statusId", description = "value of statusId used to filter list risk") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "identificationDate", description = "value of identificationDate used to filter list risk") @RequestParam(value = "identificationDate", required = false) String identificationDate,
            @Parameter(name = "resolutionDate", description = "value of resolutionDate used to filter list risk") @RequestParam(value = "resolutionDate", required = false) Date resolutionDate
    ) throws ParseException {
        Date identificationDate1 = null;
        if(StringUtils.isNotEmpty(identificationDate)) {
            identificationDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(identificationDate);
        }
        return riskService.readAll(pageable, libelle, author,criticity,null, projetId,null, statusId, identificationDate1, resolutionDate,null, natureId);

    }

    @Operation(summary = "Import risk", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the risk was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importValueIndicator(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Long projectId) {
        riskService.importRisk(file, projectId);
    }

    @GetMapping(value = "/export/excel", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Risque_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        riskService.export(response.getWriter());
    }


}
