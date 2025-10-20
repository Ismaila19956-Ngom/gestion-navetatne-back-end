package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.FundingDTO;
import com.webgram.dgpsn.services.FundingService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/fundings")
@Tag(name = "fundings-controller", description = "fundings controller")
@RequiredArgsConstructor
public class FundingController {
    private final FundingService fundingService;

    @Operation(summary = "Create funding", description = "this endpoint take input funding and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FundingDTO createFunding(@RequestBody FundingDTO fundingDTO) {
        return fundingService.create(fundingDTO);
    }

    @Operation(summary = "Update funding", description = "this endpoint take input funding and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{fundingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FundingDTO updateFunding(@PathVariable Long fundingId, @RequestBody FundingDTO fundingDTO) {
        fundingDTO.setId(fundingId);
        return fundingService.update(fundingDTO);
    }

    @Operation(summary = "Delete funding", description = "this endpoint take input funding and delete it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{fundingId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteFunding(@PathVariable Long fundingId) {
        fundingService.delete(fundingId);
    }

    @Operation(summary = "Read all fundings", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<FundingDTO> readAllFundings(
            Pageable pageable,
            @Parameter(name = "financingAgreement", description = "value of financingAgreement used to filter list fundings") @RequestParam(value = "financingAgreement", required = false) String financingAgreement,
            @Parameter(name = "amount", description = "value of amount used to filter list fundings") @RequestParam(value = "amount", required = false) Double amount,
            @Parameter(name = "cash", description = "value of cash used to filter list fundings") @RequestParam(value = "cash", required = false) String cash,
            @Parameter(name = "rate", description = "value of rate used to filter list fundings") @RequestParam(value = "rate", required = false) Double rate,
            @Parameter(name = "equivalence", description = "value of equivalence used to filter list fundings") @RequestParam(value = "equivalence", required = false) Double equivalence,
            @Parameter(name = "approvalDate", description = "value of approvalDate used to filter list fundings") @RequestParam(value = "approvalDate", required = false) String approvalDate,
            @Parameter(name = "closingDate", description = "value of closingDate used to filter list fundings") @RequestParam(value = "closingDate", required = false) String closingDate,
            @Parameter(name = "extentionDate", description = "value of extentionDate used to filter list fundings") @RequestParam(value = "extentionDate", required = false) String extentionDate,
            @Parameter(name = "fundingTypeId", description = "value of fundingTypeId used to filter list fundings") @RequestParam(value = "fundingTypeId", required = false) Long fundingTypeId,
            @Parameter(name = "projectId", description = "value of projetId used to filter list fundings") @RequestParam(value = "projectId", required = false) Long projetId,
            @Parameter(name = "partnerProjetId", description = "value of structureId used to filter list fundings") @RequestParam(value = "partnerProjetId", required = false) Long structureId,
            @Parameter(name = "sortBy", description = "value of projetId used to filter list fundings") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "value of structureId used to filter list fundings") @RequestParam(value = "ascending", required = false) Boolean ascending
    ) throws ParseException {
        return fundingService.readAll(pageable, financingAgreement, amount, cash, rate, equivalence, approvalDate, closingDate, extentionDate, fundingTypeId, projetId, structureId,sortBy,ascending);
    }

    @Operation(summary = "Import value funding", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importFunding(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Long projectId) {
       fundingService.importFunding(file, projectId);
    }

    @GetMapping(value = "/export/excel", produces = "text/csv")
    public void export(HttpServletResponse response, @RequestParam("projectId") Long projectId) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/csv; utf-8");
        String fileName = String.format("Funding_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        fundingService.export(response.getWriter(), projectId);
    }

}
