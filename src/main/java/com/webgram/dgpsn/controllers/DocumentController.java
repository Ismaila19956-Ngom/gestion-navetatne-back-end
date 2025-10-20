package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.DocumentDto;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.DocumentService;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/documents")
@Tag(name = "documents-controller", description = "documents controller")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

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
    public Response<Object> createDocument(@RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "DocumentDTO", required = true) String document)   {
        try {
            var dto = documentService.createDocument(file, document);
            return Response.ok().setPayload(dto).setMessage("document créé");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Update document", description = "this endpoint take input document and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping(path = "/{documentId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateDocument(@Parameter(name = "documentId", description = "the document id updated") @PathVariable Long documentId, @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "DocumentDTO", required = true) String document) {
        try {
            var documentDTO = objectMapper.readValue(document, DocumentDto.class);
            documentDTO.setId(documentId);
            var dto = documentService.updateDocument(file, documentDTO);
            return Response.ok().setPayload(dto).setMessage("document modifié");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(
            summary = "Download file document",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") Long id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = documentService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the document was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDocument(@Parameter(name = "id", description = "the document id deleted") @PathVariable Long id) {
        documentService.deleteDocument(id);
    }

    @Operation(summary = "Read the document", description = "This endpoint is used to read document  it take input name document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readDocument(@Parameter(name = "id", description = "the document id to read") @PathVariable Long id) {
        try {
            var dto = documentService.readDocument(id);;
            return Response.ok().setPayload(dto).setMessage("document trouvé");
        }
        catch (Exception ex){
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read all documents", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAllDocument(
            @RequestParam Map<String,String> searchParams, Pageable pageable) throws ParseException {
        var page = documentService.readAllDocument(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        Response<Object> response = Response
                .ok().setPayload(page.getContent())
                .setMetadata(metadata);
        return response;

    }

}
