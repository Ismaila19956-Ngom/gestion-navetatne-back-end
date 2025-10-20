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
import com.webgram.dgpsn.models.SpecificNatureDTO;
import com.webgram.dgpsn.services.SpecificNatureService;

@RestController
@RequestMapping("/specificNatures")
@Tag(name = "specificNature-controller", description = "SpecificNature controller")
@RequiredArgsConstructor
public class SpecificNatureController {
    private final SpecificNatureService specificNatureService;

    @Operation(summary = "Create specificNature", description = "this endpoint take input specificNature and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the specificNature was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecificNatureDTO createSpecificNature(@RequestBody SpecificNatureDTO specificNatureDTO) {
        return specificNatureService.createSpecificNature(specificNatureDTO);
    }

    @PutMapping("/{specificNatureId}")
    @ResponseStatus(HttpStatus.OK)
    public SpecificNatureDTO updateSpecificNature(@Parameter(name = "specificNatureId", description = "the specificNature id updated") @PathVariable Long specificNatureId
            , @RequestBody SpecificNatureDTO specificNatureDTO) {
        specificNatureDTO.setId(specificNatureId);
        return specificNatureService.updateSpecificNature(specificNatureDTO);
    }

    @Operation(summary = "Read the specificNature", description = "This endpoint is used to read specificNature  it take input id specificNature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the specificNature was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{specificNatureId}")
    @ResponseStatus(HttpStatus.OK)
    public SpecificNatureDTO readSpecificNature(@Parameter(name = "specificNatureId", description = "the specificNature id to read") @PathVariable Long specificNatureId) {
        return specificNatureService.readSpecificNature(specificNatureId);
    }

    @Operation(summary = "delete the specificNature", description = "Delete specificNature , it take input   id specificNature")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the specificNature was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{specificNatureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSpecificNature(@Parameter(name = "specificNatureId", description = "the specificNature id deleted") @PathVariable Long specificNatureId) {
        specificNatureService.deleteSpecificNature(specificNatureId);
    }

    @Operation(summary = "Read all specificNature", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SpecificNatureDTO> readAllSpecificNatures(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list specificNature ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list specificNature ") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "natureId", description = "value of label used to filter list specificNature ") @RequestParam(value = "natureId", required = false) Long natureId
    ) {

        return specificNatureService.readAllSpecificNature(pageable, code, libelle, natureId);

    }

}
