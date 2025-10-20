package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.models.StartUpDTO;
import com.webgram.dgpsn.services.StartUpService;

import java.util.Map;

@RestController
@RequestMapping("/startUp")
@Tag(name = "startUp", description = "startUp")
@RequiredArgsConstructor
public class StartUpController {
    private final StartUpService startUpService;

    @Operation(summary = "Create actor project", description = "this endpoint take input actor project and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type actor project was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StartUpDTO createstartUp(@RequestBody StartUpDTO startUpDTO) {
        return startUpService.create(startUpDTO);
    }

    @PutMapping("/{startUpId}")
    @ResponseStatus(HttpStatus.OK)
    public StartUpDTO updatestartUp(@Parameter(name = "startUpId", description = "the startUpId updated") @PathVariable Long startUpId, @RequestBody StartUpDTO startUpDTO) {
        startUpDTO.setId(startUpId);
        return startUpService.update(startUpDTO);
    }

    @Operation(summary = "Read the startUp", description = "This endpoint is used to read partner project it take input id actor project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{startUpId}")
    @ResponseStatus(HttpStatus.OK)
    public StartUpDTO readstartUp(@Parameter(name = "startUpId", description = "the type startUp id to read") @PathVariable Long startUpId) {
        return startUpService.read(startUpId);
    }

    @Operation(summary = "delete the startUpId", description = "Delete startUpId, it take input id startUp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{startUpId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletestartUp(@Parameter(name = "startUpId", description = "the startUpId id deleted") @PathVariable Long startUpId) {
        startUpService.delete(startUpId);
    }

    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/allStartup")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllStartUp(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = startUpService.readAllStartUp(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

}
