package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.models.CadreLogiqueDTO;
import com.webgram.dgpsn.models.CaracteristiqueRecrutementDTO;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CaracteristiqueRecrutementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/caracteristiqueRecrutements")
@Tag(name = "caracteristiqueRecrutement-controller", description = "caracteristiqueRecrutement controller")
@RequiredArgsConstructor
public class CaracteristiqueRecrutementController {
    private final CaracteristiqueRecrutementService caracteristiqueRecrutementService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create caracteristiqueRecrutement", description = "this endpoint take input caracteristiqueRecrutement and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the caracteristiqueRecrutement was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)

    public CaracteristiqueRecrutementDTO createCaracteristiqueRecrutement(@RequestBody CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO) {;
        return caracteristiqueRecrutementService.create(caracteristiqueRecrutementDTO);
    }

    @PutMapping("/{caracteristiqueRecrutementId}")
    @ResponseStatus(HttpStatus.OK)

    public CaracteristiqueRecrutementDTO updateCaracteristiqueRecrutement(@PathVariable Long caracteristiqueRecrutementId, @Parameter(name = "cadreLogique", description = "the cadre logique updated") @RequestBody CaracteristiqueRecrutementDTO caracteristiqueRecrutementDTO) {
        caracteristiqueRecrutementDTO.setId(caracteristiqueRecrutementId);
        return caracteristiqueRecrutementService.update(caracteristiqueRecrutementDTO);
    }

    @Operation(summary = "Read the caracteristiqueRecrutement", description = "This endpoint is used to read caracteristiqueRecrutement it take input id caracteristiqueRecrutement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{caracteristiqueRecrutementId}")
    @ResponseStatus(HttpStatus.OK)
    public CaracteristiqueRecrutementDTO readCaracteristiqueRecrutement(@Parameter(name = "caracteristiqueRecrutementId", description = "the caracteristiqueRecrutement id to read") @PathVariable Long caracteristiqueRecrutementId) {
        return caracteristiqueRecrutementService.read(caracteristiqueRecrutementId);
    }

    @Operation(summary = "delete the caracteristiqueRecrutement", description = "Delete caracteristiqueRecrutement, it take input id caracteristiqueRecrutement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{caracteristiqueRecrutementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCaracteristiqueRecrutement(@Parameter(name = "caracteristiqueRecrutementId", description = "the caracteristiqueRecrutementId id deleted") @PathVariable Long caracteristiqueRecrutementId) {
        caracteristiqueRecrutementService.delete(caracteristiqueRecrutementId);
    }

//    @Operation(summary = "Read all caracteristiqueRecrutement", description = "It take input param of the page and return this list related")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public Page<CaracteristiqueRecrutementDTO> readAllCaracteristiqueRecrutement(
//
//    ) {
//        return (Page<CaracteristiqueRecrutementDTO>) Response.ok().setPayload(caracteristiqueRecrutementService.readAll());
//
//    }

    @Operation(summary = "Read cessations per page", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public Response<Object> readPageCcaracteristiqueRecrutement(
            @RequestParam Map<String, String> searchParams,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) throws ParseException {
        var pageCessation = caracteristiqueRecrutementService.readPageCcaracteristiqueRecrutement(searchParams, page, size);
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
