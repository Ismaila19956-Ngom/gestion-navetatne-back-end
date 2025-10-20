package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.JournalDTO;
import com.webgram.dgpsn.services.JournalService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/journals")
@Tag(name = "journal-controller", description = "journal controller")
@RequiredArgsConstructor
@Slf4j
public class JournalController {

    private final JournalService journalService;


    @Operation(summary = "search all journal", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<JournalDTO> readAll(
            Pageable pageable,
            @Parameter(name = "user", description = "value of user used to filter list journal") @RequestParam(value = "user", required = false) String user,
            @Parameter(name = "login", description = "value of login used to filter list journal") @RequestParam(value = "login", required = false) String login,
            @Parameter(name = "actionType", description = "value of actionType used to filter list journal") @RequestParam(value = "actionType", required = false) String actionType,
            @Parameter(name = "creationDate", description = "value of date used to filter list journal") @RequestParam(value = "creationDate", required = false) String date,
            @Parameter(name = "critere", description = "value of critere used to filter list alerte") @RequestParam(value = "critere", required = false) String critere,
            @Parameter(name = "endDate", description = "value of date used to filter list alerte") @RequestParam(value = "endDate", required = false) String endDate
    ) {
        var date1 = LocalDateTime.now();
        var date2 = LocalDateTime.now();
        if(StringUtils.isNotEmpty(date)) {
                date1 = LocalDate.parse(date,  DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
                if("Egal".equals(critere)){
                    date2 = date1.plus(Duration.ofDays(1));
                }else if("Inferieure".equals(critere)) {
                    date1 = date1.plus(Duration.ofDays(1));
                }
        }else
            date1 = null;

        if(StringUtils.isNotEmpty(endDate)) {
                date2 = LocalDate.parse(endDate,  DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
                date2 = date2.plus(Duration.ofDays(1));
        }else if(!"Egal".equals(critere))
            date2 = null;
        log.info("date1  {}", date1);
        log.info("date2  {}", date2);
        System.out.println(date1);
      /*  LocalDate convertedDate = LocalDate.parse(date,  DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Instant instantDate = convertedDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
        date1.plus(Duration.ofDays(1); */
        return journalService.readAll(pageable, user, login, actionType, date1, critere, date2);
    }


}
