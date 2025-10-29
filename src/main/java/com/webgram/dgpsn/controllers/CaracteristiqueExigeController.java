package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.dgpsn.models.CaracteristiqueExigeDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CaracteristiqueExigeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/caracteristiqueExige")
@Tag(name = "caracteristiqueExige-controller", description = "caracteristiqueExige controller")
@RequiredArgsConstructor
public class CaracteristiqueExigeController {
    private final CaracteristiqueExigeService caracteristiqueExigeService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create caracteristiqueExige", description = "this endpoint take input caracteristiqueExige and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the caracteristiqueExige was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)

    public CaracteristiqueExigeDTO createCaracteristiqueExige(@RequestBody CaracteristiqueExigeDTO caracteristiqueExigeDTO) {;
        return caracteristiqueExigeService.create(caracteristiqueExigeDTO);
    }



    @Operation(summary = "delete the caracteristiqueExige", description = "Delete caracteristiqueExige, it take input id caracteristiqueExige")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{caracteristiqueExigeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCaracteristiqueExige(@Parameter(name = "caracteristiqueExigeId", description = "the caracteristiqueExigeId id deleted") @PathVariable Long caracteristiqueExigeId) {
        caracteristiqueExigeService.delete(caracteristiqueExigeId);
    }

//    @Operation(summary = "Read all caracteristiqueExige", description = "It take input param of the page and return this list related")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public Page<CaracteristiqueExigeDTO> readAllCaracteristiqueExige(
//
//    ) {
//        return (Page<CaracteristiqueExigeDTO>) Response.ok().setPayload(caracteristiqueExigeService.readAll());
//
//    }

    @Operation(summary = "Read cessations per page", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Response<Object> readPageCcaracteristiqueExige(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) throws ParseException {
        var pageCessation = caracteristiqueExigeService.readPageCaracteristiqueExige(searchParams, page, size);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(pageCessation.getNumber())
                .totalElements(pageCessation.getTotalElements())
                .size(pageCessation.getSize())
                .build();
        return Response
                .ok()
                .setPayload(pageCessation.getContent())
                .setMetadata(metadata);
    }
}
