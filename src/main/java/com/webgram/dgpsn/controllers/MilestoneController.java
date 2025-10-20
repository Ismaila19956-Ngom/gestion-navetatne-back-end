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
import com.webgram.dgpsn.models.MilestoneDTO;
import com.webgram.dgpsn.services.MilestoneService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/milestones")
@Tag(name = "milestones", description = "milestone")
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;

    @Operation(summary = "Create milestone", description = "this endpoint take input milestone project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the milestone was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilestoneDTO MilestoneDTOaddMilestoneToProject(@RequestBody MilestoneDTO milestoneDTO) {
        return milestoneService.create(milestoneDTO);
    }

    @PutMapping("/{milestoneId}")
    @ResponseStatus(HttpStatus.OK)
    public MilestoneDTO updateMilestone(@Parameter(name = "milestoneId", description = "the milestone id updated") @PathVariable Long milestoneId, @RequestBody MilestoneDTO milestoneDTO) {
        milestoneDTO.setId(milestoneId);
        return milestoneService.update(milestoneDTO);
    }

    @Operation(summary = "delete the milestonId", description = "Delete milestone, it take input id milestone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{milestoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMilestone(@Parameter(name = "milestoneId", description = "the milestone id deleted") @PathVariable Long milestoneId) {
        milestoneService.delete(milestoneId);
    }

    @Operation(summary = "Read all milestone", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MilestoneDTO> readAllMilestoneByProject(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list milestone") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "predicatedDate", description = "value of predicatedDate used to filter list milestone") @RequestParam(value = "predicatedDate", required = false) String  predicatedDate,
            @Parameter(name = "readDate", description = "value of readDate used to filter list milestone") @RequestParam(value = "readDate", required = false) String  readDate,
            @Parameter(name = "projectId", description = "value of projectId used to filter list milestone") @RequestParam(value = "projectId", required = false) Long projectId
    ) throws ParseException {
        return milestoneService.readAll(pageable, libelle, predicatedDate, readDate, projectId);
    }

    @Operation(summary = "Import milestone", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importMilestone(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        milestoneService.importMilestone(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Preparation_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        milestoneService.exportMilsstone(response.getWriter());
    }
}
