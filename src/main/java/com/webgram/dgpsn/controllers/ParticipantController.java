package com.webgram.dgpsn.controllers;
import com.webgram.dgpsn.models.ParticipantDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;

@RestController
@RequestMapping("/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @Operation(summary = "Create Participant", description = "Endpoint to create a new Participant")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createParticipant(@RequestBody ParticipantDTO dto) {
        try {
            var savedDto = participantService.create(dto);
            return Response.ok().setPayload(savedDto).setMessage("Participant created successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update Participant", description = "Endpoint to update an existing Participant")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateParticipant(@PathVariable Long id, @RequestBody ParticipantDTO dto) {
        dto.setId(id);
        try {
            var updatedDto = participantService.update(dto);
            return Response.ok().setPayload(updatedDto).setMessage("Participant updated successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete Participant", description = "Endpoint to delete a Participant by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParticipant(@PathVariable Long id) {
        participantService.delete(id);
    }

    @Operation(summary = "Get Participant by ID", description = "Endpoint to retrieve a Participant by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getParticipantById(@PathVariable Long id) {
        try {
            var dto = participantService.read(id);
            return Response.ok().setPayload(dto).setMessage("Participant found successfully.");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all Participants", description = "Endpoint to retrieve all Participants with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getAllParticipants(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = participantService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }
}