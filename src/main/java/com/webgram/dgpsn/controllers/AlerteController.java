package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.models.AlerteDTO;
import com.webgram.dgpsn.services.AlerteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/alertes")
@Tag(name = "alerte-controller", description = "alerte controller")
@RequiredArgsConstructor
public class AlerteController {

    private final AlerteService alerteService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Read all alert not read", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{alertId}")
    public void setAlertTotRead(@PathVariable Long alertId) {
       alerteService.setAlertToRead(alertId);
    }
    @Operation(summary = "search all alerte", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<AlerteDTO> readAll(
            Pageable pageable,
            @Parameter(name = "message", description = "value of message used to filter list alerte") @RequestParam(value = "message", required = false) String message,
            @Parameter(name = "date", description = "value of date used to filter list alerte") @RequestParam(value = "date", required = false) String date,
            @Parameter(name = "critere", description = "value of critere used to filter list alerte") @RequestParam(value = "critere", required = false) String critere,
            @Parameter(name = "endDate", description = "value of date used to filter list alerte") @RequestParam(value = "endDate", required = false) String endDate,
            @Parameter(name = "read", description = "value of read used to filter list alerte") @RequestParam(value = "read", required = false) Boolean read,
            @Parameter(name = "priority", description = "value of priority used to filter list alerte") @RequestParam(value = "priority", required = false) Priority priority,
            @Parameter(name = "username", description = "value of user used to filter list alerte") @RequestParam(value = "username", required = false) String username
    ) {
        var date1 = new Date();
        var date2 = new Date();
        if(StringUtils.isNotEmpty(date)) {
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                if("Egal".equals(critere)){
                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    c.add(Calendar.DATE, 1);
                    date2 = c.getTime();

                }else if("Inferieure".equals(critere)) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    c.add(Calendar.DATE, 1);
                    date1 = c.getTime();
                }
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }else
            date1 = null;


        if(StringUtils.isNotEmpty(endDate)) {
            try {
                date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                Calendar c = Calendar.getInstance();
                c.setTime(date2);
                c.add(Calendar.DATE, 1);
                date2 = c.getTime();
            } catch (ParseException ex) {
                System.out.println(ex);
            }
        }else if(!"Egal".equals(critere))
            date2 = null;

      /*  LocalDate convertedDate = LocalDate.parse(date,  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Instant instantDate = convertedDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
        date1.plus(Duration.ofDays(1); */
        return alerteService.readAll(pageable, message, date1, critere, date2, read, priority, username);
    }


}
