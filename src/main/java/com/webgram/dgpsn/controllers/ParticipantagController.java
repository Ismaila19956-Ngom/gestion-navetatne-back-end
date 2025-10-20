package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.ParticipantagDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.ParticipantagServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/participantags")
@RequiredArgsConstructor
public class ParticipantagController {

    private final ParticipantagServiceImpl participantagService;

    @Operation(summary = "Create Participantag", description = "this endpoint takes input Participantag and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody ParticipantagDto participantag) {
        try {
            var dto = participantagService.create(participantag);
            return Response.ok().setPayload(dto).setMessage("Participantag créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Participantag", description = "This endpoint is used to read Participantag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{participantagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "participantagId", description = "the type Participantag id to valid") @PathVariable Long participantagId) {
        try {
            var dto = participantagService.read(participantagId);
            return Response.ok().setPayload(dto).setMessage("Participantag trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{participantagId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "participantagId", description = "the Participantag id to updated") @PathVariable Long participantagId, @RequestBody ParticipantagDto participantag) {
        participantag.setId(participantagId);
        try {
            var dto = participantagService.update(participantag);
            return Response.ok().setPayload(dto).setMessage("Participantag modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Participantag", description = "Delete Participantag, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{participantagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "participantagId", description = "the Participantag id to be deleted") @PathVariable Long participantagId) {
        participantagService.delete(participantagId);
    }

    @Operation(summary = "Read all Participantag", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = participantagService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  AssembleGeneral", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/assemblegeneral/{assemblegeneralId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByAssemblegeneralId(
        @Parameter(name = "assemblegeneralId", description = "the Id of the assemblegeneral to retrieve participantag") @PathVariable Long assemblegeneralId){
     List<ParticipantagDto> entites = participantagService.readByAssemblegeneralId(assemblegeneralId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Participantag to CSV", description = "Exports the list of Participantag to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportParticipantag(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Participantags.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        participantagService.exportParticipantag(response.getWriter());
    }
}
