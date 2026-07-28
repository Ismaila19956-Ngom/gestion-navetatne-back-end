package sn.naavetane.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.models.DirectionDTO;
import sn.naavetane.backend.services.DirectionService;

import java.util.Map;

@RestController
@RequestMapping("/directions")
@Tag(name = "direction-controller", description = "direction controller")
@RequiredArgsConstructor
public class DirectionController {
    private final DirectionService directionService;

    @Operation(summary = "Create direction", description = "this endpoint take input direction and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the direction was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DirectionDTO createDirection(@RequestBody DirectionDTO direction) {
        return directionService.create(direction);
    }

    @PutMapping("/{directionId}")
    @ResponseStatus(HttpStatus.OK)
    public DirectionDTO updateDirection(@Parameter(name = "directionId", description = "the direction id to updated") @PathVariable Long directionId, @RequestBody DirectionDTO direction) {
        direction.setId(directionId);
        return directionService.update(direction);
    }

    @Operation(summary = "Read the direction", description = "This endpoint is used to read direction, it take input id direction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{directionId}")
    @ResponseStatus(HttpStatus.OK)
    public DirectionDTO readDirection(@Parameter(name = "directionId", description = "the type direction id to read") @PathVariable Long directionId) {
        return directionService.read(directionId);
    }

    @Operation(summary = "delete the direction", description = "Delete direction, it take input id direction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the direction was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{directionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDirection(@Parameter(name = "directionId", description = "the direction id deleted") @PathVariable Long directionId) {
        directionService.delete(directionId);
    }

    @Operation(summary = "Read all direction", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<DirectionDTO> readAllDirection(
            Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return directionService.readAll(searchParams, page, size);
    }

    @Operation(summary = "Load Organigrammme", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/organigramme")
    public Page<DirectionDTO> LoadOrganigramme(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size) {
        return directionService.loadOrganigramme(page, size);
    }

}
