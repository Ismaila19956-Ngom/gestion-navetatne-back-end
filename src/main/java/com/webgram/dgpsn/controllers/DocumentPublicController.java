package com.webgram.dgpsn.controllers;

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
import com.webgram.dgpsn.models.DocumentPublicDTO;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.services.DocumentPublicService;

import java.io.IOException;

@RestController
@RequestMapping("/documents-public")
@Tag(name = "documents-public-controller", description = "documents-public controller")
@RequiredArgsConstructor
public class DocumentPublicController {
    private final DocumentPublicService documentService;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create document", description = "this endpoint take input document and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentPublicDTO createDocument(
            @RequestPart(name = "DocumentPublicDTO", required = true) String documentPublicDTO,
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
        return documentService.create(documentPublicDTO, file);
    }

    @PutMapping("/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentPublicDTO updateDocument(@Parameter(name = "documentId", description = "the document id to updated") @PathVariable Long documentId, @RequestBody DocumentPublicDTO documentPublicDTO) {
        return documentService.update(documentId, documentPublicDTO);
    }

    @Operation(summary = "Read the document", description = "This endpoint is used to read document, it take input id document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentPublicDTO readDocument(@Parameter(name = "documentId", description = "the type document id to read") @PathVariable Long documentId) {
        return documentService.read(documentId);
    }

    @Operation(summary = "delete the document", description = "Delete document, it take input id document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the action was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@Parameter(name = "documentId", description = "the document id deleted") @PathVariable Long documentId) {
        documentService.delete(documentId);
    }

    @Operation(summary = "Read all documents", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<DocumentPublicDTO> readAllFolder(
            Pageable pageable,
            @Parameter(name = "titre", description = "value of titre used to filter list document") @RequestParam(value = "titre", required = false) String titre,
            @Parameter(name = "date", description = "value of date used to filter list document") @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "authors", description = "value of authors used to filter list document") @RequestParam(value = "authors", required = false) String authors,
            @Parameter(name = "themes", description = "value of themes used to filter list document") @RequestParam(value = "themes", required = false) String themes,
            @Parameter(name = "documentTypeId", description = "value of documentTypeId used to filter list document") @RequestParam(value = "documentTypeId", required = false) Long documentTypeId,
            @Parameter(name = "folderId", description = "value of folderId used to filter list document") @RequestParam(value = "folderId", required = false) Long folderId,
            @Parameter(name = "publish", description = "value of publish used to filter list document") @RequestParam(value = "publish", required = false) Boolean publish,
            @Parameter(name = "partnerId", description = "value of partnerId used to filter list document") @RequestParam(value = "partnerIds", required = false) Long partnerId,
            @Parameter(name = "sectorId", description = "value of sectorId used to filter list document") @RequestParam(value = "sectorIds", required = false) Long sectorId
    ) {
        return documentService.readAll(pageable, titre, date, authors, themes, documentTypeId, folderId, publish, partnerId, sectorId);
    }

    @Operation(summary = "Read all published documents", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/publishedDocuments")
    public Page<DocumentPublicDTO> readPublishedDocument(
            Pageable pageable,
            @Parameter(name = "titre", description = "value of titre used to filter list document") @RequestParam(value = "titre", required = false) String titre,
            @Parameter(name = "date", description = "value of date used to filter list document") @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "authors", description = "value of authors used to filter list document") @RequestParam(value = "authors", required = false) String authors,
            @Parameter(name = "themes", description = "value of themes used to filter list document") @RequestParam(value = "themes", required = false) String themes,
            @Parameter(name = "documentType", description = "value of documentType used to filter list document") @RequestParam(value = "documentType", required = false) String documentType,
            @Parameter(name = "partner", description = "value of partnerId used to filter list document") @RequestParam(value = "partner", required = false) String partner,
            @Parameter(name = "sector", description = "value of sectorId used to filter list document") @RequestParam(value = "sector", required = false) String sector
    ) {
        return documentService.readPublishedDocument(pageable, titre, date, authors, themes, documentType, partner, sector);
    }

    @Operation(
            summary = "Download file document",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/{documentId}/_download", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("documentId") Long id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = documentService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }

    @Operation(
            summary = "Publish or unpublish a document",
            description = "this endpoint is used to publish or unpublished a document .")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/publishUnpublish/{documentId}")
    @ResponseStatus(HttpStatus.OK)
    public void publishOrUnpublish(@Parameter(name = "documentId", description = "the document id to updated") @PathVariable Long documentId) {
        documentService.publishOrUnpublish(documentId);
    }
}
