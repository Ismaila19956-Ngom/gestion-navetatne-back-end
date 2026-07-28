package sn.naavetane.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.models.StructureDTO;
import sn.naavetane.backend.services.StructureService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/structures")
@Tag(name = "structure-controller", description = "structure controller")
@RequiredArgsConstructor
public class StructureController {
    private final StructureService structureService;

    private final ObjectMapper objectMapper;

    @Operation(summary = "Create structure", description = "this endpoint take input structure and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StructureDTO createStructure(@RequestBody StructureDTO structure) {
        return structureService.createStructure(structure);
    }

    @PutMapping("/{structureId}")
    @ResponseStatus(HttpStatus.OK)
    public StructureDTO updateStructure(@Parameter(name = "structureId", description = "the structure id updated") @PathVariable Long structureId,
                                        @RequestBody StructureDTO structure) {
        structure.setId(structureId);
        return structureService.updateStructure(structure);
    }

    @Operation(summary = "Read the structure", description = "This endpoint is used to read structure it take input id structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{structureId}")
    @ResponseStatus(HttpStatus.OK)
    public StructureDTO readStructure(@Parameter(name = "StructureId", description = "the structure id to read") @PathVariable Long structureId) {
        return structureService.readStructure(structureId);
    }

    @Operation(summary = "delete the structure", description = "Delete structure, it take input id cadre logique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the structure was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{structureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStructure(@Parameter(name = "structureId", description = "the structure id deleted") @PathVariable Long structureId) {
        structureService.deleteStructure(structureId);
    }

    @Operation(summary = "Read all structure", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<StructureDTO> readAllStrcuture(
            Pageable pageable,
            @Parameter(name = "code", description = "value of label used to filter list structure") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list structure") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "type structure", description = "value of type structure used to filter list structure") @RequestParam(value = "typeStructure", required = false) TypeStructure typeStructure,
            @Parameter(name = "idsToIgnore", description = "value of idsToIgnore used to filter list structure")@RequestParam(value = "idsToIgnore", required = false) List<Long> idsToIgnore,
            @Parameter(name = "structureTypeList", description = "list of structureType used to filter list Structure") @RequestParam(value = "structureTypeList", required = false) List<TypeStructure> structureTypeList,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list Structure") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list Structure") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return structureService.readAllStructure(pageable, code, libelle, typeStructure, idsToIgnore, structureTypeList, sortBy, ascending);
    }

    // Import and export have been removed.
}


