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
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.ParticipantMissionDTO;
import com.webgram.dgpsn.services.ParticipantMissionService;

@RestController
@RequestMapping("/participant")
@Tag(name = "Participant-Contoller", description = "Participant Mission Activite")
@RequiredArgsConstructor
public class ParticipantMissionController {
    private final ParticipantMissionService participantMissionService;

    @Operation(summary = "Create participant activite", description = "this endpoint take input participant activite and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the participant activite was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantMissionDTO createParticipantMission(@RequestBody ParticipantMissionDTO participantMission) {
        return participantMissionService.create(participantMission);
    }

    @PutMapping("/{participantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantMissionDTO updateParticipantMission(@Parameter(name = "participantId", description = "the participantId updated") @PathVariable Long participantId, @RequestBody ParticipantMissionDTO participantMission) {
        participantMission.setId(participantId);
        return participantMissionService.update(participantMission);
    }

    @Operation(summary = "Read the participant", description = "This endpoint is used to read participant mission activite it take input id participant activite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{participantId}")
    @ResponseStatus(HttpStatus.OK)
    public ParticipantMissionDTO readParticipantMission(@Parameter(name = "participantId", description = "the type participant id to read") @PathVariable Long participantId) {
        return participantMissionService.read(participantId);
    }

    @Operation(summary = "delete the participantId", description = "Delete participantId, it take input id participant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{participantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipantMissionActivity(@Parameter(name = "participantId", description = "the participantId id deleted") @PathVariable Long participantId) {
        participantMissionService.delete(participantId);
    }

    @Operation(summary = "Read all participantMissionActivity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ParticipantMissionDTO> readAllParticipantMissionActivity(
            Pageable pageable,
            @Parameter(name = "assignmentId", description = "value of projectId used to filter list assignmentId") @RequestParam(value = "assignmentId", required = false) Long assignmentId,
            @Parameter(name = "actorId", description = "value of actorId used to filter list actorId") @RequestParam(value = "actorId", required = false) Long actorId,
            @Parameter(name = "roleId", description = "value of roleId used to filter list actorProject") @RequestParam(value = "roleId", required = false) Long roleId
    ) {
        return participantMissionService.readAll(pageable,assignmentId,actorId,roleId);
    }

//    @Operation(summary = "Import actor", description = "this endpoint take input excel file and import it on database")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public void importActor(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("projectId") Long projectId) {
//        actorProjetService.importActor(file, projectId);
//    }

//    @GetMapping(value = "/export", produces = "text/csv")
//    public void export(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        response.setCharacterEncoding("UTF-8");
//        String fileName = String.format("Actor%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//        actorProjetService.exportActor(response.getWriter());
//    }
}
