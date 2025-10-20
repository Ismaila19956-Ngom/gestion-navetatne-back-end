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
import com.webgram.dgpsn.entities.enums.FundingTypeConfig;
import com.webgram.dgpsn.models.FundingConfigDTO;
import com.webgram.dgpsn.services.FundingConfigService;

import java.text.ParseException;

@RestController
@RequestMapping("/fundingConfig")
@Tag(name = "Financement:", description = "Enpoint pour gerer les Besoins Financement,Mobilisations et Executions dans programme, projet et activite")
@RequiredArgsConstructor
public class FundingConfigController {
    private final FundingConfigService fundingConfigService;

    @Operation(summary = "Create findingConfig", description = "this endpoint take input findingConfig activity and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the fundingConfig was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FundingConfigDTO AddFindingConfig(@RequestBody FundingConfigDTO fundingConfigDTO) {
        return fundingConfigService.create(fundingConfigDTO);
    }

    @PutMapping("/{fundingConfigId}")
    @ResponseStatus(HttpStatus.OK)
    public FundingConfigDTO updateFundingConfig(@Parameter(name = "fundingConfigId", description = "the fundingConfig id updated") @PathVariable Long fundingConfigId, @RequestBody FundingConfigDTO fundingConfigDTO) {
        fundingConfigDTO.setId(fundingConfigId);
        return fundingConfigService.update(fundingConfigDTO);
    }

    @Operation(summary = "delete the fundingConfigIdId", description = "Delete fundingConfigId, it take input id budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @DeleteMapping("/{fundingConfigId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFundingConfig(@Parameter(name = "fundingConfigId", description = "the fundingConfig id deleted") @PathVariable Long fundingConfigId) {
        fundingConfigService.delete(fundingConfigId);
    }

//    @GetMapping("/{fundingConfigId}")
//    @ResponseStatus(HttpStatus.OK)
//    public FundingConfigDTO readFundingConf(
//            @Parameter(name = "fundingConfigId", description = "the FundingConfigid to read") @PathVariable Long budgetId
//    ) {
//        return FundingConfigService.read(FundingConfigId);
//    }

    @Operation(summary = "Read all fundingConfig", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<FundingConfigDTO> readAllfundingConfigByActivity(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of libelle used to filter list fundingConfig") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "annee", description = "value of annee used to filter list fundingConfig") @RequestParam(value = "annee", required = false) String  annee,
            @Parameter(name = "fundingTypeConfig", description = "value of fundingTypeConfig used to filter list fundingConfig") @RequestParam(value = "fundingTypeConfig", required = false) FundingTypeConfig fundingTypeConfig,
            @Parameter(name = "startingDate", description = "value of startingDate used to filter list fundingConfig") @RequestParam(value = "startingDate", required = false) String  startingDate,
            @Parameter(name = "endingDate", description = "value of endingDate used to filter list fundingConfig") @RequestParam(value = "endingDate", required = false) String  endingDate,
            @Parameter(name = "estimatedAmount", description = "value of estimatedAmount used to filter list fundingConfig") @RequestParam(value = "estimatedAmount", required = false) String  estimatedAmount,
            @Parameter(name = "actualAmount", description = "value of actualAmount used to filter list fundingConfig") @RequestParam(value = "actualAmount", required = false) String  actualAmount,
            @Parameter(name = "managementUnitId", description = "value of managementUnitId used to filter list fundingConfig") @RequestParam(value = "managementUnitId", required = false) Long managementUnitId
    ) throws ParseException {
        return fundingConfigService.readAll(pageable,libelle,  annee,fundingTypeConfig,startingDate,endingDate, estimatedAmount, actualAmount,managementUnitId);
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
