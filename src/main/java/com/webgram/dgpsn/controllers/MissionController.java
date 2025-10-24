package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.models.MissionDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/-missions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MissionController {

    private final MissionService missionService;

    @Operation(summary = "Create Mission", description = "Endpoint to create a new Mission")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success")})
    @PostMapping()
    public ResponseEntity<Response<Object>> createMission(@RequestBody MissionDTO dto) {
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var savedDto = missionService.createMission(dto, files);
            return new ResponseEntity<>(Response.ok().setPayload(savedDto).setMessage("Created Successfully"), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(Response.badRequest().setMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Mission", description = "Endpoint to update an existing Mission")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PutMapping(value = "/{id}")
    public Response<Object> updateMission(@PathVariable Long id, @RequestBody MissionDTO dto) {
        dto.setId(id);
        try {
            Map<String, MultipartFile> files = new HashMap<>();

            var updatedDto = missionService.updateMission(dto, files);
            return Response.ok().setPayload(updatedDto).setMessage("Updated Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Delete Mission", description = "Endpoint to delete a Mission by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No Content")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMission(@PathVariable Long id) {
        missionService.deleteMission(id);
    }

    @Operation(summary = "Get Mission by ID", description = "Endpoint to retrieve a Mission by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/{id}")
    public Response<Object> getMissionById(@PathVariable Long id) {
        try {
            var dto = missionService.getMissionById(id);
            return Response.ok().setPayload(dto).setMessage("Found Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Get all Missions", description = "Endpoint to retrieve all Missions with pagination and search")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/all")
    public Response<Object> getAllMissions(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = missionService.getAllMissions(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Export Missions to CSV", description = "Endpoint to export Missions to a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @GetMapping("/export")
    public void exportMissions(HttpServletResponse response) {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"missions.csv\"");
        try (PrintWriter writer = response.getWriter()) {
            missionService.exportMissions(writer);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Import Missions from CSV", description = "Endpoint to import Missions from a CSV file")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success")})
    @PostMapping("/import")
    public Response<Object> importMissions(@RequestBody List<MissionDTO> dtos) {
        try {
            List<MissionDTO> imported = missionService.importMissions(dtos);
            return Response.ok().setPayload(imported).setMessage("Imported Successfully");
        } catch (Exception ex) {
            return Response.badRequest().setMessage("Error during import: " + ex.getMessage());
        }
    }

    @PutMapping("/{id}/statut")
    @Operation(summary = "Update status")
    public ResponseEntity<MissionDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam Statut statut) {
        return ResponseEntity.ok(missionService.updateStatut(id, statut));
    }
}