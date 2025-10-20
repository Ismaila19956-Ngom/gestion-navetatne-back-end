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
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.FundingSourceDTO;
import com.webgram.dgpsn.services.FundingSourceService;

import java.text.ParseException;

@RestController
@RequestMapping("/source")
@Tag(name = "Source-Financement :", description = "Enpoint pour gerer le source de financement dans programme, projet et activite")
@RequiredArgsConstructor
public class FundingSourceController {
    private final FundingSourceService fundingSourceService;

    @Operation(summary = "Create source", description = "this endpoint take input source activity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the budget was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FundingSourceDTO AddSource(@RequestBody FundingSourceDTO fundingSourceDTO) {
        return fundingSourceService.create(fundingSourceDTO);
    }

    @PutMapping("/{fundingSourceId}")
    @ResponseStatus(HttpStatus.OK)
    public FundingSourceDTO updateSourcefinancement(@Parameter(name = "fundingSourceId", description = "the budget id updated") @PathVariable Long fundingSourceId, @RequestBody FundingSourceDTO fundingSourceDTO) {
        fundingSourceDTO.setId(fundingSourceId);
        return fundingSourceService.update(fundingSourceDTO);
    }

    @Operation(summary = "delete the sourceId", description = "Delete budget, it take input id budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{fundingSourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSource(@Parameter(name = "fundingSourceId", description = "the source id deleted") @PathVariable Long fundingSourceId) {
        fundingSourceService.delete(fundingSourceId);
    }

    @Operation(summary = "Read all source", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<FundingSourceDTO> readAllSourceByActivity(
            Pageable pageable,
            @Parameter(name = "montant", description = "value of montant used to filter list source") @RequestParam(value = "montant", required = false) String montant,
            @Parameter(name = "managementUnitId", description = "value of managementUnitId used to filter list source") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId ,
            @Parameter(name = "structureId", description = "value of structureId used to filter list source") @RequestParam(value = "structureId", required = false) Long structureId,
            @Parameter(name = "budgetId", description = "value of budgetId used to filter list source") @RequestParam(value = "budgetId", required = false)Long budgetId
    ) throws ParseException {
        return fundingSourceService.readAll(pageable, montant, managementUnitId, structureId,budgetId);
    }

//    @Operation(summary = "Import milestone", description = "this endpoint take input excel file and import it on database")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @ResponseStatus(HttpStatus.CREATED)
//    public void importMilestone(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam("projectId") Long projectId) {
//        milestoneService.importMilestone(file, projectId);
//    }
//
//    @GetMapping(value = "/export", produces = "text/csv")
//    public void export(HttpServletResponse response) throws IOException {
//        response.setContentType("text/csv");
//        response.setCharacterEncoding("UTF-8");
//        String fileName = String.format("Preparation_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
//        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//        milestoneService.exportMilsstone(response.getWriter());
//    }
}
