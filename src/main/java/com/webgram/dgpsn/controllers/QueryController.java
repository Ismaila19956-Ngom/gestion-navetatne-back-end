package com.webgram.dgpsn.controllers;

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
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.TypeApplicant;
import com.webgram.dgpsn.models.QueryDTO;
import com.webgram.dgpsn.services.QueryService;

import java.util.Date;

@RestController
@RequestMapping("/queries")
@Tag(name = "query-controller", description = "Query controller")
@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create query", description = "this endpoint take input query and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping(consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public QueryDTO createQuery(@RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "QueryDTO", required = true) String query) throws IOException {
//        return queryService.create(file, query);
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QueryDTO createQuery(@RequestBody QueryDTO queryDTO) {
        return queryService.create(queryDTO);
    }
    @PutMapping("/{queryId}")
    @ResponseStatus(HttpStatus.OK)
    public QueryDTO updateQuery(@Parameter(name = "queryId", description = "the queryId id updated") @PathVariable Long queryId, @RequestBody QueryDTO queryDTO) {
        queryDTO.setId(queryId);
        return queryService.update(queryDTO);
    }

//    @PutMapping(path = "/{queryId}",
//            consumes = {
//                    MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.OK)
//    public QueryDTO updateQuery(
//            @Parameter(name = "queryId", description = "the query type id updated") @PathVariable Long queryId
//            , @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "QueryDTO", required = true) String query) throws IOException {
//       var queryDTO = objectMapper.readValue(query, QueryDTO.class);
//        queryDTO.setId(queryId);
//        return queryService.update(file,queryDTO);
//    }

    @Operation(summary = "Read the query", description = "This endpoint is used to read query  it take input id query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{queryId}")
    @ResponseStatus(HttpStatus.OK)
    public QueryDTO readQuery(
            @Parameter(name = "queryId", description = "the query id to read") @PathVariable Long queryId) {
        return queryService.read(queryId);
    }

    @Operation(summary = "delete the query", description = "Delete query, it take input   id query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{queryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuery(@Parameter(name = "queryId", description = "the query id deleted") @PathVariable Long queryId) {
        queryService.delete(queryId);
    }

    @Operation(summary = "Read all query", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<QueryDTO> readAllQueries(
            Pageable pageable,
            @Parameter(name = "date", description = "value of date used to filter list query") @RequestParam(value = "date", required = false) Date date,
            @Parameter(name = "categorieRequeteId", description = "value of categorieRequeteId used to filter list query") @RequestParam(value = "categorieRequeteId", required = false) Long categorieRequeteId,
            @Parameter(name = "typeRequeteId", description = "value of typeRequeteId used to filter list query") @RequestParam(value = "typeRequeteId", required = false) Long typeRequeteId,
            @Parameter(name = "typeDemandeur", description = "value of typeDemandeur used to filter list query") @RequestParam(value = "typeDemandeurId", required = false) TypeApplicant typeDemandeur,
            @Parameter(name = "typeDestinataire", description = "value of typeDestinataire used to filter list query") @RequestParam(value = "typeDestinataire", required = false) TypeApplicant typeDestinataire,
            @Parameter(name = "demandeurActorId", description = "value of demandeurActorId used to filter list query") @RequestParam(value = "demandeurActorId", required = false) Long demandeurActorId,
            @Parameter(name = "demandeurStructureId", description = "value of demandeurStructureId used to filter list query") @RequestParam(value = "demandeurStructureId", required = false) Long demandeurStructureId,
            @Parameter(name = "destinataireActorId", description = "value of destinataireActorId used to filter list query") @RequestParam(value = "destinataireActorId", required = false) Long destinataireActorId,
            @Parameter(name = "destinataireStructureId", description = "value of destinataireStructureId used to filter list query") @RequestParam(value = "destinataireStructureId", required = false) Long destinataireStructureId,
            @Parameter(name = "projetId", description = "value of projet used to filter list query") @RequestParam(value = "projetId", required = false) Long projetId
    ) {
        return queryService.readAll(pageable,

                date,
                typeDemandeur,
                typeDestinataire,
                categorieRequeteId,
                typeRequeteId,
                demandeurActorId,
                demandeurStructureId,
                destinataireActorId,
                destinataireStructureId,
                projetId
        );
    }

//    @Operation(
//            summary = "Download file assignment",
//            description = "this endpoint is used to read a  file and let the system import into the repository.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping(value = "/{id}/_download", produces = {"application/json"})
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Identifiant of document, must be unique", required = true) @PathVariable("id") Long id) {
//
//        /* Getting downloadFile */
//        DownloadFile downloadFile = queryService.readFile(id);
//
//        /* Initializing headerValues */
//        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;
//
//        /* RETURN download file */
//        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
//    }
//
//    @Operation(summary = "Import query", description = "this endpoint take input excel file and import it on database")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public void importQuery(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("projectId") Long projectId) {
//        queryService.importQuery(file, projectId);
//    }
//
//    @GetMapping(value = "/export", produces = "text/csv")
//    public void export(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        response.setCharacterEncoding("UTF-8");
//        String fileName = String.format("Requete%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//        queryService.exportQuery(response.getWriter());
//    }
}
