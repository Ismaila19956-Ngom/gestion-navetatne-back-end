package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.webgram.dgpsn.entities.enums.MediathequeType;
import com.webgram.dgpsn.models.*;
import com.webgram.dgpsn.services.MediathequeService;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/mediatheques")
@Tag(name = "mediatheques-controller", description = "mediatheques controller")
@RequiredArgsConstructor
public class MediathequeController {
    private final MediathequeService mediathequeService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create mediatheque", description = "this endpoint take input mediatheque and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public MediathequeDTO createMediatheque(@RequestPart(name = "file", required = false) MultipartFile file
            , @RequestPart(name = "MediathequeDTO", required = true) String mediatheque) throws IOException {
        return mediathequeService.create(file,mediatheque);
    }

    @Operation(summary = "Read all mediatheques", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MediathequeDTO> readAllMdiatheques(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list mediatheque") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "date", description = "value of date used to filter list mediatheque") @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "mediathequeType", description = "value of mediathequeType used to filter list mediatheque") @RequestParam(value = "mediathequeType", required = false) MediathequeType mediathequeType,
            @Parameter(name = "projetId", description = "value of projetId used to filter list mediatheque") @RequestParam(value = "projectId", required = false) Long projetId,
            @Parameter(name = "entrepriseId", description = "value of entrepriseId used to filter list mediatheque") @RequestParam(value = "entrepriseId", required = false) Long entrepriseId
    ) throws ParseException {
        return mediathequeService.readAll(pageable, libelle, date, mediathequeType, projetId,entrepriseId);
    }

    @PutMapping("/{mediathequeId}")
    @ResponseStatus(HttpStatus.OK)
    public MediathequeDTO update(@Parameter(name = "mediatheque id", description = "the mediatheque id updated") @PathVariable Long mediathequeId
            , @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "MediathequeDTO", required = true) String mediatheque) throws IOException {
        var mediathequeDTO = objectMapper.readValue(mediatheque, MediathequeDTO.class);
        mediathequeDTO.setId(mediathequeId);
        return mediathequeService.update(file,mediathequeDTO);
    }

    @Operation(summary = "delete the mediatheque", description = "Delete mediatheque, it take input   id mediatheque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{mediathequeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNature(@Parameter(name = "mediathequeId", description = "the nature id deleted") @PathVariable Long mediathequeId) {
        mediathequeService.delete(mediathequeId);
    }

    @Operation(
            summary = "Download file review",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") Long id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = mediathequeService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }
}
