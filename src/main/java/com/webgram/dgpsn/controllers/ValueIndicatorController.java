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
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.ValueIndicatorDTO;
import com.webgram.dgpsn.services.ValueIndicatorService;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/value-indicators")
@Tag(name = "value-indicator-controller", description = "value Indicator controller")
@RequiredArgsConstructor
public class ValueIndicatorController {
    private final ValueIndicatorService valueIndicatorService;

    @Operation(summary = "Create value indicator", description = "this endpoint take input value indicator and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the value indicator was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ValueIndicatorDTO createValueIndicator(@RequestBody ValueIndicatorDTO valueIndicator) {
        return valueIndicatorService.create(valueIndicator);
    }

    @Operation(summary = "Update value indicator", description = "this endpoint take input value indicator and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the value indicator was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/{valueIndicatorId}")
    @ResponseStatus(HttpStatus.OK)
    public ValueIndicatorDTO updateValueIndicator(@Parameter(name = "valueIndicatorId", description = "the valueIndicator id updated") @PathVariable Long valueIndicatorId,@RequestBody ValueIndicatorDTO valueIndicator) {
        valueIndicator.setId(valueIndicatorId);
        return valueIndicatorService.update(valueIndicator);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the valueIndicator was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{valueIndicatorId}")
    @ResponseStatus(HttpStatus.OK)
    public ValueIndicatorDTO readValueIndicator(@Parameter(name = "valueIndicatorId", description = "the valueIndicator id to read") @PathVariable Long valueIndicatorId) {
        return valueIndicatorService.read(valueIndicatorId);
    }

    @Operation(summary = "delete the valueIndicator", description = "Delete valueIndicator, it take input   id valueIndicator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the valueIndicator was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{valueIndicatorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteValueIndicator(@Parameter(name = "valueIndicatorId", description = "the valueIndicator id deleted") @PathVariable Long valueIndicatorId) {
        valueIndicatorService.delete(valueIndicatorId);
    }

    @Operation(summary = "Read all value indicator", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ValueIndicatorDTO> readAllValueIndicators(
            Pageable pageable,
            @Parameter(name = "projectId", description = "value of projectId used to filter list partnerProject") @RequestParam(value = "projectId", required = false) Long projectId,
            @Parameter(name = "activityId", description = "value of activityId used to filter list actorProject") @RequestParam(value = "activityId", required = false) Long activityId,
            @Parameter(name = "indicatorId", description = "value of indicatorId used to filter list actorProject") @RequestParam(value = "indicatorId", required = false) Long indicatorId,
            @Parameter(name = "period", description = "value of period used to filter list actorProject") @RequestParam(value = "period", required = false) String period,
            @Parameter(name = "targetValue", description = "value of targetValue used to filter list actorProject") @RequestParam(value = "targetValue", required = false) Double targetValue,
            @Parameter(name = "valueReched", description = "value of valueReched used to filter list actorProject") @RequestParam(value = "valueReched", required = false) Double valueReched,
            @Parameter(name = "startDate", description = "value of startDate used to filter list actorProject") @RequestParam(value = "startDate", required = false) Date startDate,
            @Parameter(name = "endDate", description = "value of period used to filter list actorProject") @RequestParam(value = "endDate", required = false) Date endDate
    ) {
        return valueIndicatorService.readAll(pageable, projectId, activityId, indicatorId, period, targetValue, valueReched, startDate, endDate);
    }

    @Operation(summary = "Import value indicator", description = "this endpoint take input excel file and import it on database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping(path = "/import" ,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void importValueIndicator(@RequestParam("file") MultipartFile file, @RequestParam("projectId") Long projectId) {
        valueIndicatorService.importIndicator(file, projectId);
    }

    @GetMapping(value = "/export/excel", produces = "text/csv")
    public void export(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Valeur_indicateur_%s.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        valueIndicatorService.export(response.getWriter());
    }

    @Operation(summary = "Read last value indicator by project", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/last")
    public List<ValueIndicatorDTO> readValueIndicators(
            @Parameter(name = "projectId", description = "value of projectId used to filter list partnerProject") @RequestParam(value = "projectId", required = false) ManagementUnitEntity project

    ) {
        return valueIndicatorService.findLast(project);
    }

}
