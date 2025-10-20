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
import com.webgram.dgpsn.models.SubSectorDTO;
import com.webgram.dgpsn.services.SubSectorService;

@RestController
@RequestMapping("/subSectors")
@Tag(name = "subSector-controller", description = "SubSector controller")
@RequiredArgsConstructor
public class SubSectorController {
    private final SubSectorService subSectorService;

    @Operation(summary = "Create subSector", description = "this endpoint take input subSector and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the subSector was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubSectorDTO createSubSector(@RequestBody SubSectorDTO subSectorDTO) {
        return subSectorService.createSubSector(subSectorDTO);
    }

    @PutMapping("/{subSectorId}")
    @ResponseStatus(HttpStatus.OK)
    public SubSectorDTO updateSubSector(@Parameter(name = "subSectorId", description = "the subSector id updated") @PathVariable Long subSectorId
            , @RequestBody SubSectorDTO subSectorDTO) {
        subSectorDTO.setId(subSectorId);
        return subSectorService.updateSubSector(subSectorDTO);
    }

    @Operation(summary = "Read the subSector", description = "This endpoint is used to read subSector  it take input id subSector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{subSectorId}")
    @ResponseStatus(HttpStatus.OK)
    public SubSectorDTO readSubSector(@Parameter(name = "subSectorId", description = "the subSector id to read") @PathVariable Long subSectorId) {
        return subSectorService.readSubSector(subSectorId);
    }

    @Operation(summary = "delete the subSector", description = "Delete subSector , it take input   id subSector")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the subSector was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{subSectorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubSector(@Parameter(name = "subSectorId", description = "the subSector id deleted") @PathVariable Long subSectorId) {
        subSectorService.deleteSubSector(subSectorId);
    }

    @Operation(summary = "Read all subSector", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SubSectorDTO> readAllSubSectors(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list subSector ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list subSector ") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "sectorId", description = "value of sectorId used to filter list subSector ") @RequestParam(value = "sectorId", required = false) Long sectorId
    ) {

        return subSectorService.readAllSubSector(pageable, code, libelle, sectorId);

    }

}
