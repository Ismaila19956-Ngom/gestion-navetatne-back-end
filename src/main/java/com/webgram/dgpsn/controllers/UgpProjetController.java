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
import com.webgram.dgpsn.models.UgpProjetDTO;
import com.webgram.dgpsn.services.UgpProjetService;

@RestController
@RequestMapping("/ugp-project")
@Tag(name = "ugp-project", description = "ugp project")
@RequiredArgsConstructor
public class UgpProjetController {
    private final UgpProjetService ugpProjetService;

    @Operation(summary = "Create ugp project", description = "this endpoint take input ugp project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type ugp project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UgpProjetDTO createUgpProject(@RequestBody UgpProjetDTO ugpProjetDTO) {
        return ugpProjetService.create(ugpProjetDTO);
    }

    @PutMapping("/{ugpProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public UgpProjetDTO updateUgpProject(@Parameter(name = "ugpProjectId", description = "the ugpProjectId updated") @PathVariable Long ugpProjectId, @RequestBody UgpProjetDTO ugpProjetDTO) {
        ugpProjetDTO.setId(ugpProjectId);
        return ugpProjetService.update(ugpProjetDTO);
    }

    @Operation(summary = "Read the ugpProject", description = "This endpoint is used to read ugp project it take input id ugp project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{ugpProjectId}")
    @ResponseStatus(HttpStatus.OK)
    public UgpProjetDTO readUgpProject(@Parameter(name = "ugpProjectId", description = "the ugpProject id to read") @PathVariable Long ugpProjectId) {
        return ugpProjetService.read(ugpProjectId);
    }

    @Operation(summary = "delete the ugpProjectId", description = "Delete ugpProjectId, it take input id ugpProject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{ugpProjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUgpProject(@Parameter(name = "ugpProjectId", description = "the ugpProjectId id deleted") @PathVariable Long ugpProjectId) {
        ugpProjetService.delete(ugpProjectId);
    }

    @Operation(summary = "Read all ugpProject", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<UgpProjetDTO> readAllActorByProject(
            Pageable pageable,
            @Parameter(name = "existed", description = "value of existed used to filter list ugpProject") @RequestParam(value = "existed", required = false) Boolean existed,
            @Parameter(name = "occupied", description = "value of occupied used to filter list ugpProject") @RequestParam(value = "occupied", required = false) Boolean occupied,
            @Parameter(name = "status", description = "value of status used to filter list ugpProject") @RequestParam(value = "status", required = false) String status,
            @Parameter(name = "projectId", description = "value of projectId used to filter list ugpProject") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "ugpRoleId", description = "value of roleId used to filter list ugpProject") @RequestParam(value = "ugpRoleId", required = false) Long ugpRoleId
    ) {
        return ugpProjetService.readAll(pageable, existed, occupied, status, projectId, ugpRoleId);
    }
}
