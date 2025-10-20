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
import com.webgram.dgpsn.models.RespondentDTO;
import com.webgram.dgpsn.services.RespondentService;

import java.util.Date;


@RestController
@RequestMapping("/respondents")
@Tag(name = "respondent-controller", description = "répondant controller")
@RequiredArgsConstructor
public class RespondentController {
    private final RespondentService respondentService;

    @Operation(summary = "Create respondent", description = "this endpoint take input respondent and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type respondent was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespondentDTO create(@RequestBody RespondentDTO respondentDTO) {
        return respondentService.create(respondentDTO);
    }

    @PutMapping("/{respondentId}")
    @ResponseStatus(HttpStatus.OK)
    public RespondentDTO update(@Parameter(name = "respondentId", description = "the respondent id to updated") @PathVariable Long respondentId, @RequestBody RespondentDTO respondentDTO) {
        respondentDTO.setId(respondentId);
        return respondentService.update(respondentDTO);
    }

    @Operation(summary = "Read the respondent", description = "This endpoint is used to read respondent, it take input id respondent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the respondent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{respondentId}")
    @ResponseStatus(HttpStatus.OK)
    public RespondentDTO read(@Parameter(name = "respondentId", description = "the type respondent id to read") @PathVariable Long respondentId) {
        return respondentService.read(respondentId);
    }

    @Operation(summary = "delete the respondent", description = "Delete respondent, it take input id respondent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the respondent was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{respondentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "respondentId", description = "the respondent id deleted") @PathVariable Long respondentId) {
        respondentService.delete(respondentId);
    }

    @Operation(summary = "Read all respondent", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RespondentDTO> readAll(
            Pageable pageable,
            @Parameter(name = "firstName", description = "value of firstName used to filter list respondent") @RequestParam(value = "firstName", required = false) String firstName,
            @Parameter(name = "lastName", description = "value of lastName used to filter list respondent") @RequestParam(value = "lastName", required = false) String lastName,
            @Parameter(name = "phone", description = "value of phone used to filter list respondent") @RequestParam(value = "phone", required = false) String phone,
            @Parameter(name = "startDate", description = "value of startDate used to filter list respondent") @RequestParam(value = "startDate", required = false) Date startDate,
            @Parameter(name = "endDate", description = "value of endDate used to filter list respondent") @RequestParam(value = "endDate", required = false) Date endDate,
            @Parameter(name = "fundingId", description = "value of fundingId used to filter list respondent") @RequestParam(value = "fundingId", required = false) Long fundingId
    ) {
        return respondentService.readAll(pageable, firstName, lastName, phone, startDate, endDate, fundingId);
    }

}
