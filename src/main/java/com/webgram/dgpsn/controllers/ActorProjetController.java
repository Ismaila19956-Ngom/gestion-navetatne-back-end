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
import com.webgram.dgpsn.models.ActorProjetDTO;
import com.webgram.dgpsn.services.ActorProjetService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/actor-project")
@Tag(name = "actor-project", description = "actor project")
@RequiredArgsConstructor
public class ActorProjetController {
    private final ActorProjetService actorProjetService;

    @Operation(summary = "Create actor project", description = "this endpoint take input actor project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type actor project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorProjetDTO createActorProject(@RequestBody ActorProjetDTO actorProjet) {
        return actorProjetService.create(actorProjet);
    }

    @PutMapping("/{actorProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public ActorProjetDTO updateActorProject(@Parameter(name = "actorProjectId", description = "the actorProjectId updated") @PathVariable Long actorProjectId, @RequestBody ActorProjetDTO actorProjet) {
        actorProjet.setId(actorProjectId);
        return actorProjetService.update(actorProjet);
    }

    @Operation(summary = "Read the actorProject", description = "This endpoint is used to read partner project it take input id actor project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{actorProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public ActorProjetDTO readActorProject(@Parameter(name = "actorProjectId", description = "the type actorProject id to read") @PathVariable Long actorProjectId) {
        return actorProjetService.read(actorProjectId);
    }

    @Operation(summary = "delete the actorProjectId", description = "Delete actorProjectId, it take input id actorProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{actorProjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActorProject(@Parameter(name = "actorProjectId", description = "the actorProjectId id deleted") @PathVariable Long actorProjectId) {
        actorProjetService.delete(actorProjectId);
    }

    @Operation(summary = "Read all actorProject", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ActorProjetDTO> readAllActorByProject(
            Pageable pageable,
            @Parameter(name = "projectId", description = "value of projectId used to filter list actorProject") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "agentId", description = "value of agentId used to filter list actorProject") @RequestParam(value = "agentId", required = false) Long agentId,
            @Parameter(name = "roleId", description = "value of roleId used to filter list actorProject") @RequestParam(value = "roleId", required = false) Long roleId
    ) {
        return actorProjetService.readAll(pageable, projectId, agentId, roleId);
    }

    @Operation(summary = "Import actor", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importActor(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        actorProjetService.importActor(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Actor%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        actorProjetService.exportActor(response.getWriter());
    }
}
