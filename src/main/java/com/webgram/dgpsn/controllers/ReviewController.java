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
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.ReviewDTO;
import com.webgram.dgpsn.services.ReviewService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/reviews")
@Tag(name = "review-controller", description = "Review controller")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    private final ObjectMapper objectMapper;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Create review", description = "this endpoint take input review and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(@RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "ReviewDTO", required = true) String review) throws IOException {
        return reviewService.createReview(file,review);
    }

    @PutMapping(path = "/{reviewId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO updateReview(
            @Parameter(name = "reviewId", description = "the review type id updated") @PathVariable Long reviewId
            , @RequestPart(name = "file", required = false) MultipartFile file, @RequestPart(name = "ReviewDTO", required = true) String review) throws IOException {
        var reviewDTO = objectMapper.readValue(review, ReviewDTO.class);
        reviewDTO.setId(reviewId);
        return reviewService.updateReview(file,reviewDTO);
    }

    @Operation(summary = "Read the review", description = "This endpoint is used to read review  it take input id review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO readcReview(
            @Parameter(name = "reviewId", description = "the review id to read") @PathVariable Long reviewId) {
       return reviewService.readReview(reviewId);
    }

    @Operation(summary = "delete the review", description = "Delete review, it take input   id review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@Parameter(name = "reviewId", description = "the review id deleted") @PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @Operation(summary = "Read all review", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ReviewDTO> readAllReviews(
            Pageable pageable,
            @Parameter(name = "projetId", description = "value of projet used to filter list review") @RequestParam(value = "projetId", required = false) Long projetId,
            @Parameter(name = "keyPoint", description = "value of keyPoint used to filter list review") @RequestParam(value = "keyPoint", required = false) String keyPoint,
            @Parameter(name = "date", description = "value of date used to filter list review") @RequestParam(value = "date", required = false) String date

    ) throws ParseException {

        return reviewService.readAllReview(pageable, projetId, keyPoint, date);

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
        DownloadFile downloadFile = reviewService.readFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }

    @Operation(summary = "Import review", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importReview(
            @RequestParam("file") MultipartFile file,
            @RequestParam("projectId") Long projectId) {
        reviewService.importReview(file, projectId);
    }

    @GetMapping(value = "/export", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Review%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        reviewService.exportReview(response.getWriter());
    }
}
