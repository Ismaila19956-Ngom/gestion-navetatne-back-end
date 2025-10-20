package com.webgram.dgpsn.controllers;

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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.models.AssignmentDTO;
import com.webgram.dgpsn.services.AssignmentService;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/assignments")
@Tag(name = "assignment-controller", description = "Assignment controller")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create assignment", description = "this endpoint take input assignment and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the assignment was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDTO createAssignment(@RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "AssignmentDTO", required = true) String assignment) throws IOException {
        return assignmentService.create(file,assignment);
    }
@Operation(summary = "Create Assignment Activity", description = "this endpoint take input Assignment Activity and save it")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the assignment was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
@PostMapping("/assignmentActivity")
@ResponseStatus(HttpStatus.CREATED)
public AssignmentDTO createAssignmentActivity(@RequestBody AssignmentDTO assignmentDTO) {
    return assignmentService.create(assignmentDTO);
}

    @PutMapping(path = "/{assignmentId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public AssignmentDTO updateAssignment(
            @Parameter(name = "assignmentId", description = "the assignment id updated") @PathVariable Long assignmentId
            , @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "AssignmentDTO", required = true) String assignment) throws IOException
    {
        var assignmentDTO = objectMapper.readValue(assignment, AssignmentDTO.class);
        assignmentDTO.setId(assignmentId);
        return assignmentService.update(file,assignmentDTO);
    }
@PutMapping("/assignmentActivity/{assignmentId}")
@ResponseStatus(HttpStatus.OK)
public AssignmentDTO updateAssignmentActivity(@Parameter(name = "assignmentId", description = "the assignment id updated") @PathVariable Long assignmentId, @RequestBody AssignmentDTO assignmentDTO) {
    assignmentDTO.setId(assignmentId);
    return assignmentService.update(assignmentDTO);
}

    @Operation(summary = "Read the assignment", description = "This endpoint is used to read assignment  it take input id assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public AssignmentDTO readAssignment(
            @Parameter(name = "assignmentId", description = "the assignment id to read") @PathVariable Long assignmentId
    ) {
        return assignmentService.read(assignmentId);
    }

    @Operation(summary = "delete the assignment", description = "Delete assignment, it take input   id assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the assignment was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{assignmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssignment(
            @Parameter(name = "assignmentId", description = "the assignment id deleted") @PathVariable Long assignmentId
    ) {
        assignmentService.delete(assignmentId);
    }

    @Operation(summary = "Read all assignment", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<AssignmentDTO> readAllAssignments(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list assignment") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "startDate", description = "value of startDate used to filter list assignment") @RequestParam(value = "startDate", required = false) String startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list assignment") @RequestParam(value = "endDate", required = false) String endDate,
            @Parameter(name = "assignmentTypeId", description = "value of assignmentTypeId used to filter list assignment") @RequestParam(value = "assignmentTypeId", required = false) Long assignmentTypeId,
            @Parameter(name = "structureProjectType", description = "value of structureProjectType used to filter list assignment") @RequestParam(value = "structureProjectType", required = false) StructureProjectType structureProjectType,
            @Parameter(name = "structureId", description = "value of structureId used to filter list assignment") @RequestParam(value = "structureId", required = false) Long structureId,
            @Parameter(name = "projetId", description = "value of projet used to filter list assignment") @RequestParam(value = "projetId", required = false) Long projetId
    ) throws ParseException {
        return assignmentService.readAll(pageable,
                libelle,
                startDate,
                endDate,
                structureProjectType,
                assignmentTypeId,
                structureId,
                projetId);
    }

//    @Operation(
//            summary = "Download file assignment",
//            description = "this endpoint is used to read a  file and let the system import into the repository.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") Long id) {
//
//        /* Getting downloadFile */
//        DownloadFile downloadFile = assignmentService.readFile(id);
//
//        /* Initializing headerValues */
//        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;
//
//        /* RETURN download file */
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
//    }
//
//    @Operation(summary = "Import assignment", description = "this endpoint take input excel file and import it on database")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public void importAssignement(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("projectId") Long projectId) {
//        assignmentService.importAssignment(file, projectId);
//    }
//
//    @GetMapping(value = "/export", produces = "text/csv")
//    public void export(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        response.setCharacterEncoding("UTF-8");
//        String fileName = String.format("Mission%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//        assignmentService.exportAssignment(response.getWriter());
//    }
}
