package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.landingPage.KeysDataDTO;
import com.webgram.dgpsn.models.landingPage.ProjectFundingDTO;
import com.webgram.dgpsn.services.LandingPageService;
import com.webgram.dgpsn.services.ManagementUnitService;

import java.util.List;

@RestController
@RequestMapping("/landingPage")
@Tag(name = "landingPage-controller", description = "landing page controller")
@RequiredArgsConstructor
public class LandingPageController {
    private final LandingPageService landingPageService;
    private final ManagementUnitService managementUnitService;

    private static final String HEADER_PREFIX = "attachment; filename=\"";
    private static final String HEADER_SUFFIX = "\"";
    private static final String MEDIA_TYPE = "application/octet-stream";

    @Operation(summary = "Read keys data", description = "It return all keys data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/keysData")
    public KeysDataDTO readKeysData() {
        return landingPageService.getKeysData();
    }

    @Operation(summary = "Read average last 3 project funding", description = "It return average last 3 project funding")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fundingPerProject")
    public List<ProjectFundingDTO> readAllFundingPerProject() {
        return landingPageService.getAverageFundingByProject();
    }

    @Operation(
            summary = "Download file document",
            description = "this endpoint is used to read a  file and let the system import into the repository.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the file was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping(value = "/_download/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InputStreamResource> readFile(@Parameter(description = "Path of document, must be unique", required = true) @PathVariable Long id) {

        /* Getting downloadFile */
        DownloadFile downloadFile = managementUnitService.redFile(id);

        /* Initializing headerValues */
        String headerValues = HEADER_PREFIX + downloadFile.getFileName() + HEADER_SUFFIX;

        /* RETURN download file */
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MEDIA_TYPE)).header(HttpHeaders.CONTENT_DISPOSITION, headerValues).body(new InputStreamResource(downloadFile.getInputStream()));
    }
}
