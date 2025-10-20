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
import com.webgram.dgpsn.models.MeetingAgentDTO;
import com.webgram.dgpsn.models.MeetingDTO;
import com.webgram.dgpsn.services.MeetingService;

import java.sql.Time;
import java.util.Date;

//@CrossOrigin("*")
@RestController
@RequestMapping("/meetings")
@Tag(name = "meeting", description = "meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;

    @Operation(summary = "Create meeting", description = "this endpoint take input meeting and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the meeting was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingDTO createMeeting(@RequestBody MeetingAgentDTO meetingAgentDTO) {
        return meetingService.create(meetingAgentDTO);
    }

    @PutMapping("/{meetingId}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDTO updateMeeting(@Parameter(name = "meetingId", description = "the meeting id updated") @PathVariable Long meetingId, @RequestBody MeetingDTO meetingDTO) {
        meetingDTO.setId(meetingId);
        return meetingService.update(meetingDTO);
    }
    @PutMapping("/meetingAgent/{meetingId}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDTO updateMeetingAgent(
            @Parameter(name = "meetingId", description = "the meeting type id updated") @PathVariable Long meetingId,
            @RequestBody MeetingAgentDTO meetingAgentDTO) {
        meetingAgentDTO.getMeeting().setId(meetingId);
        return meetingService.updateMeetingAgent(meetingAgentDTO);
    }
    @Operation(summary = "delete the meetingId", description = "Delete meeting, it take input id meeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the meeting was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{meetingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeeting(@Parameter(name = "meetingId", description = "the meeting id deleted") @PathVariable Long meetingId) {
      meetingService.delete(meetingId);
    }
    @Operation(summary = "delete the meetingAgent", description = "Delete meeting, it take input   id issueLog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the issueLog was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/meetingAgent/{meetingAgentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeetingAgent(@Parameter(name = "meetingId", description = "the issueLog id deleted") @PathVariable Long meetingAgentId) {
        meetingService.delete(meetingAgentId);
    }
    @Operation(summary = "Read all meeting", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MeetingDTO> readAllMeeting(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list meeting") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "predicatedDate", description = "value of predicatedDate used to filter list meeting") @RequestParam(value = "predicatedDate", required = false) Date predicatedDate,
            @Parameter(name = "readDate", description = "value of readDate used to filter list meeting") @RequestParam(value = "readDate", required = false) Date readDate,
            @Parameter(name = "comment", description = "value of heureDebutPrevue used to filter list meeting") @RequestParam(value = "comment", required = false) String comment,
            @Parameter(name = "heureDebutPrevue", description = "value of heureDebutPrevue used to filter list meeting") @RequestParam(value = "heureDebutPrevue", required = false) Time heureDebutPrevue,
            @Parameter(name = "heureFinPrevue", description = "value of heureFinPrevue used to filter list meeting") @RequestParam(value = "heureFinPrevue", required = false) Time heureFinPrevue,
            @Parameter(name = "heureDebutReelle", description = "value of heureDebutReelle used to filter list meeting") @RequestParam(value = "heureDebutReelle", required = false) Time heureDebutReelle,
            @Parameter(name = "heureFinReelle", description = "value of heureFinReelle used to filter list meeting") @RequestParam(value = "heureFinReelle", required = false) Time heureFinReelle,
            @Parameter(name = "meetingTypeId", description = "value of meetingTypeId used to filter list meeting") @RequestParam(value = "meetingTypeId", required = false) Long meetingTypeId,
            @Parameter(name = "projetId", description = "value of projectId used to filter list meeting") @RequestParam(value = "projetId", required = false) Long projetId
//            @Parameter(name = "meetingId", description = "value of meetingId used to filter list meeting") @RequestParam(value = "meetingId", required = false) Long meetingId,
//            @Parameter(name = "agentId", description = "value of agentId used to filter list meeting") @RequestParam(value = "agentIds", required = false) List<Long> agentIds
    ) {
        return meetingService.readAll(
                pageable,
                libelle,
                predicatedDate,
                readDate,
                comment,
                heureDebutPrevue,
                heureFinPrevue,
                heureDebutReelle,
                heureFinReelle,
                meetingTypeId,
                projetId
        );
    }

}
