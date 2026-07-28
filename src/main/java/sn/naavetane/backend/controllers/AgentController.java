package sn.naavetane.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import sn.naavetane.backend.entities.enums.SituationMatrimoniale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.models.AgentDTO;
import sn.naavetane.backend.models.DownloadFile;
import sn.naavetane.backend.services.AgentService;


import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/agents")
@Tag(name = "agent-controller", description = "agent controller")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create agent", description = "this endpoint take input agent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the agent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public AgentDTO createAgent(@RequestPart(name = "file", required = false) MultipartFile file
            , @RequestPart(name = "AgentDTO", required = true) String agent) throws IOException {
        return agentService.create(file,agent);
    }

    @PutMapping("/{agentId}")
    @ResponseStatus(HttpStatus.OK)
    public AgentDTO updateAgent(@Parameter(name = "agentId", description = "the agent id updated") @PathVariable Long agentId
            , @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "AgentDTO", required = true) String agent) throws IOException {
        var agentDTO = objectMapper.readValue(agent, AgentDTO.class);
        agentDTO.setId(agentId);
        return agentService.update(file,agentDTO);
    }

    @Operation(summary = "Read the agent", description = "This endpoint is used to read agent it take input id agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{agentId}")
    @ResponseStatus(HttpStatus.OK)
    public AgentDTO readAgent(@Parameter(name = "agentId", description = "the agent id to read") @PathVariable Long agentId) {
        return agentService.read(agentId);
    }

    @Operation(summary = "delete the agent", description = "Delete agent, it take input id agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{agentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgent(@Parameter(name = "agentId", description = "the agentId id deleted") @PathVariable Long agentId) {
        agentService.delete(agentId);
    }

    @Operation(summary = "Read all agent", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<AgentDTO> readAllAgent(
            Pageable pageable,
            @Parameter(name = "idsToIgnore", description = "value of idsToIgnore used to filter list agent") @RequestParam(value = "idsToIgnore", required = false) List<Long> idsToIgnore,
            @Parameter(name = "typeStructure", description = "value of typeStructure used to filter list agent") @RequestParam(value = "typeStructure", required = false) TypeStructure typeStructure,
            @Parameter(name = "prenom", description = "value of prenom used to filter list agent") @RequestParam(value = "prenom", required = false) String prenom,
            @Parameter(name = "nom", description = "value of nom used to filter list agent") @RequestParam(value = "nom", required = false) String nom,
            @Parameter(name = "adresse", description = "value of adresse used to filter list agent") @RequestParam(value = "adresse", required = false) String adresse,
            @Parameter(name = "email", description = "value of email used to filter list agent") @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "telephone", description = "value of telephone used to filter list agent") @RequestParam(value = "telephone", required = false) String telephone,
            @Parameter(name = "dateCreation", description = "value of dateCreation used to filter list agent") @RequestParam(value = "dateCreation", required = false) Date dateCreation,
            @Parameter(name = "structureId", description = "value of structureId used to filter list agent") @RequestParam(value = "structureId", required = false) Long structureId,
            @Parameter(name = "fonctionId", description = "value of fonctionId used to filter list agent") @RequestParam(value = "fonctionId", required = false) Long fonctionId,
            @Parameter(name = "directionId", description = "value of directionId used to filter list agent") @RequestParam(value = "directionId", required = false) Long directionId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list agent") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list agent") @RequestParam(value = "ascending", required = false) Boolean ascending,
            @Parameter(name = "situationMatrimoniale", description = "filtre sur la situation matrimoniale") @RequestParam(value = "situationMatrimoniale", required = false) SituationMatrimoniale situationMatrimoniale


            ) {
        return agentService.readAll(pageable, idsToIgnore, typeStructure, nom, prenom, adresse, email, telephone, dateCreation, structureId , fonctionId, directionId, sortBy, ascending, situationMatrimoniale);
    }

    @Operation(
            summary = "Download file ageng",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") Long id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = agentService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }

    // Import and export have been removed.


    @Operation(summary = "Read the agent not in user", description = "This endpoint is used to read agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/notInUser")
    @ResponseStatus(HttpStatus.OK)
    public List<AgentDTO> readAgentNotInUser() {
        return agentService.getAgentNotInUsers();
    }
}


