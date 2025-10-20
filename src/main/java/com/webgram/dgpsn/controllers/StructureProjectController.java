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
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.StructureProjectDTO;
import com.webgram.dgpsn.models.responses.ReportingByStructureDTO;
import com.webgram.dgpsn.services.StructureProjectService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/partner-project")
@Tag(name = "structure-project-controller", description = "structure-project controller")
@RequiredArgsConstructor
public class StructureProjectController {
    private final StructureProjectService partnerProjectService;

    @Operation(summary = "Create partners of project", description = "this endpoint take input partners and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type partner project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureProjectDTO addPartnerToProject(@RequestBody StructureProjectDTO partnerProjet) {
        return partnerProjectService.create(partnerProjet);
    }

    @Operation(summary = "Create many structure of project", description = "this endpoint take input many structure and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type partner project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/many")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStructureToProject(@RequestBody List<StructureProjectDTO> partnerProjet) {
        partnerProjectService.linkManyStructure(partnerProjet);
    }

    @PutMapping("/{partnerProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public StructureProjectDTO updatePartnerProject(@Parameter(name = "partnerProjectId", description = "the partnerProjectId updated") @PathVariable Long partnerProjectId, @RequestBody StructureProjectDTO partnerProjetDTO) {
        partnerProjetDTO.setId(partnerProjectId);
        return partnerProjectService.update(partnerProjetDTO);
    }

    @Operation(summary = "Read the partnerProjet", description = "This endpoint is used to read partner project it take input id partner project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{partnerProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public StructureProjectDTO readPartnerProject(@Parameter(name = "partnerProjectId", description = "the type partnerProject id to read") @PathVariable Long partnerProjectId) {
        return partnerProjectService.read(partnerProjectId);
    }

    @Operation(summary = "delete the partnerProject", description = "Delete partnerProjectId, it take input id partnerProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{partnerProjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartnerProject(@Parameter(name = "partnerProjectId", description = "the partnerProjectId id deleted") @PathVariable Long partnerProjectId) {
        partnerProjectService.delete(partnerProjectId);
    }

    @Operation(summary = "Read all partnerProject", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<StructureProjectDTO> readAllPartnerByProject(
            Pageable pageable,
            @Parameter(name = "montant", description = "value of montant used to filter list partnerProject") @RequestParam(value = "montant", required = false) Double montant,
            @Parameter(name = "projectId", description = "value of projectId used to filter list partnerProject") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "structureId", description = "value of structureId used to filter list partnerProject") @RequestParam(value = "structureId", required = false) Long structureId,
            @Parameter(name = "startDate", description = "value of startDate used to filter list StructureProject") @RequestParam(value = "startDate", required = false) String startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list StructureProject") @RequestParam(value = "endDate", required = false) String endDate,
            @Parameter(name = "structureType", description = "value of structureType used to filter list StructureProject") @RequestParam(value = "structureType", required = false) TypeStructure structureType,
            @Parameter(name = "structureProjectType", description = "value of structureProjectType used to filter list StructureProject") @RequestParam(value = "structureProjectType", required = false) StructureProjectType structureProjectType,
            @Parameter(name = "structureProjectTypeList", description = "list of structureProjectType used to filter list StructureProject") @RequestParam(value = "structureProjectTypeList", required = false) List<StructureProjectType> structureProjectTypeList,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
    )
    throws ParseException {
        return partnerProjectService.readAll(pageable, montant, projectId, structureId,structureType,structureProjectType,structureProjectTypeList,sortBy, ascending, startDate ,endDate);

    }

    @Operation(summary = "Import partnerProjet", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importPartnerProjet(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        partnerProjectService.importPartener(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Partenaires%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        partnerProjectService.exportPartener(response.getWriter());
    }

    @Operation(summary = "Read the partnerProjet", description = "This endpoint is used to read reporting by structure it take input id structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/reportingByStructure/{structureId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReportingByStructureDTO> getReportingByStructrucure(@Parameter(name = "structureId", description = "the structure id to read") @PathVariable Long structureId) {
        return partnerProjectService.getReportingByStructrucure(structureId);
    }

}
