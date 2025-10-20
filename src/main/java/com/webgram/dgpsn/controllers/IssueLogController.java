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
import com.webgram.dgpsn.models.IssueLogDTO;
import com.webgram.dgpsn.services.IssueLogService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/issuelogs")
@Tag(name = "issuelog-controller", description = "Issuelog controller")
@RequiredArgsConstructor
public class IssueLogController {
    private final IssueLogService issueLogService;

    @Operation(summary = "Create issuelog", description = "this endpoint take input issuelog and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the issuelog was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueLogDTO createIssueLog(@RequestBody IssueLogDTO issueLogDTO) {
        return issueLogService.create(issueLogDTO);
    }

    @PutMapping("/{issueLogId}")
    @ResponseStatus(HttpStatus.OK)
    public IssueLogDTO updateIssueLog(
            @Parameter(name = "issueLogId", description = "the issueLog type id updated") @PathVariable Long issueLogId,
            @RequestBody IssueLogDTO issueLogDTO) {
        issueLogDTO.setId(issueLogId);
        return issueLogService.update(issueLogDTO);
    }

    @Operation(summary = "Read the issueLog", description = "This endpoint is used to read issueLog  it take input id issueLog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{issueLogId}")
    @ResponseStatus(HttpStatus.OK)
    public IssueLogDTO readIssueLog(@Parameter(name = "issueLogId", description = "the issueLog id to read") @PathVariable Long issueLogId) {
        return issueLogService.read(issueLogId);
    }

    @Operation(summary = "delete the issueLog", description = "Delete issueLog, it take input   id issueLog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the issueLog was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{issueLogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssueLog(@Parameter(name = "issueLogId", description = "the issueLog id deleted") @PathVariable Long issueLogId) {
        issueLogService.delete(issueLogId);
    }

    @Operation(summary = "Read all issueLog", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<IssueLogDTO> readAllIssueLogs(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list issueLog") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "description", description = "value of description used to filter list issueLog") @RequestParam(value = "description", required = false) String description,
            @Parameter(name = "author", description = "value of author used to filter list issueLog") @RequestParam(value = "author", required = false) String author,
            @Parameter(name = "identificationDate", description = "value of identificationDate used to filter list issueLog") @RequestParam(value = "identificationDate", required = false) String identificationDate,
            @Parameter(name = "deadline", description = "value of deadline used to filter list issueLog") @RequestParam(value = "deadline", required = false) String deadline,
            @Parameter(name = "resolutionDate", description = "value of resolutionDate used to filter list issueLog") @RequestParam(value = "resolutionDate", required = false) String resolutionDate,
            @Parameter(name = "projetId", description = "value of projet used to filter list issueLog") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "assignmentId,", description = "value of  assignmentId, used to filter list issueLog") @RequestParam(value = "assignmentId", required = false) Long assignmentId,
            @Parameter(name = "criticityId", description = "value of criticityId used to filter list issueLog") @RequestParam(value = "criticityId", required = false) Long criticityId,
            @Parameter(name = "delayImpactId", description = "value of delayImpactId used to filter list issueLog") @RequestParam(value = "delayImpactId", required = false) Long delayImpactId,
            @Parameter(name = "financialImpactId", description = "value of financialImpactId used to filter list issueLog") @RequestParam(value = "financialImpactId", required = false) Long financialImpactId,
            @Parameter(name = "statusId", description = "value of statusId used to filter list issueLog") @RequestParam(value = "statusId", required = false) Long statusId,
            @Parameter(name = "natureId", description = "value of natureId used to filter list issueLog") @RequestParam(value = "natureId", required = false) Long natureId

    ) throws ParseException {
        Date identificationDate1 = null;
        if(StringUtils.isNotEmpty(identificationDate)) {
            identificationDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(identificationDate);
        }
        Date deadline1 = null;
        if(StringUtils.isNotEmpty(deadline)) {
            deadline1 = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
        }
        Date resolutionDate1 = null;
        if(StringUtils.isNotEmpty(resolutionDate)) {
            resolutionDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(resolutionDate);
        }
        return issueLogService.readAll(pageable, libelle, description, author, identificationDate1, deadline1, resolutionDate1, projetId, assignmentId, criticityId, delayImpactId, financialImpactId, statusId, natureId);
    }

    @Operation(summary = "Import risk", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the risk was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importIssuelog(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Long projectId) {
        issueLogService.importIssuelog(file, projectId);
    }

    @GetMapping(value = "/export/excel", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Risque_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        issueLogService.export(response.getWriter());
    }

    @Operation(summary = "Read the issueLog", description = "This endpoint is used to read issueLog  it take input id issueLog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("list/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public List<IssueLogDTO> readListIssueLog(@Parameter(name = "projectId", description = "the project id to read issueLog") @PathVariable Long projectId) {
        return issueLogService.readListIssueLog(projectId);
    }
    @PutMapping("valid/{issueLogId}")
    @ResponseStatus(HttpStatus.OK)
    public void validIssueLog(
            @Parameter(name = "issueLogId", description = "the issueLog type id valid") @PathVariable Long issueLogId,
            @RequestBody IssueLogDTO issueLogDTO) {
        issueLogDTO.setId(issueLogId);
        issueLogService.valid(issueLogDTO);
    }

}
