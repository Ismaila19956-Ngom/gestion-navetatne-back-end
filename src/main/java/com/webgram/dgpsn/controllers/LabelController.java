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
import com.webgram.dgpsn.entities.enums.ReferentielType;
import com.webgram.dgpsn.models.LabelDTO;
import com.webgram.dgpsn.services.LabelService;

@RestController
@RequestMapping("/labels")
@Tag(name = "label-controller", description = "label controller")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @Operation(summary = "Create label", description = "this endpoint take input label and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the label was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO createLabel(@RequestBody LabelDTO label) {
        return labelService.create(label);
    }

    @PutMapping("/{labelId}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO updateLabel(@Parameter(name = "labelId", description = "the label id to updated") @PathVariable Long labelId, @RequestBody LabelDTO label) {
        label.setId(labelId);
        return labelService.update(label);
    }

    @Operation(summary = "Read the label", description = "This endpoint is used to read label, it take input id label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{labelId}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO readLabel(@Parameter(name = "labelId", description = "the type label id to read") @PathVariable Long labelId) {
        return labelService.read(labelId);
    }

    @Operation(summary = "delete the label", description = "Delete label, it take input id label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the label was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{labelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@Parameter(name = "labelId", description = "the label id deleted") @PathVariable Long labelId) {
        labelService.delete(labelId);
    }

    @Operation(summary = "Read all label", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<LabelDTO> readAllLabel(
            Pageable pageable,
            @Parameter(name = "referentielType", description = "value of referentielType used to filter list label") @RequestParam(value = "referentielType", required = false) ReferentielType referentielType,
            @Parameter(name = "label", description = "value of label used to filter list label") @RequestParam(value = "label", required = false) String label
    ) {
        return labelService.readAll(pageable, referentielType, label);
    }

//    @Operation(summary = "Read all referentiel type", description = "It take input param of the page and return this list related")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/referentielTypes")
//    public List<ReferentielType> readAllReferentielType() {
//        return List.of(ReferentielType.values());
//    }

    @Operation(summary = "Read all referentiel type", description = "It takes input parameters for pagination and optional filters for label and description, and returns a paginated list of ReferentielType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/referentielTypes")
    public Page<ReferentielType> readAllReferentielType(
            Pageable pageable,
            @Parameter(name = "label", description = "value of label used to filter ReferentielType list") @RequestParam(value = "label", required = false) String label,
            @Parameter(name = "description", description = "value of description used to filter ReferentielType list") @RequestParam(value = "description", required = false) String description
    ) {
        return labelService.readAllReferentielType(pageable, label, description);
    }

}
