package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.Criticity;
import com.webgram.dgpsn.entities.enums.Period;
import com.webgram.dgpsn.entities.enums.Periodicity;
import com.webgram.dgpsn.entities.enums.ReferentielType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enums")
@Tag(name = "enums-controller", description = "Controller pour recuperer les valeurs des enumerations")
@RequiredArgsConstructor
public class EnumController {

    @Operation(summary = "Read all referentiel type", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/referentielTypes")
    public List<ReferentielType> readAllReferentielType() {
        return List.of(ReferentielType.values());
    }

    @Operation(summary = "Read all referentiel type", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/criticities")
    public List<Criticity> readAllCriticities() {
        return List.of(Criticity.values());
    }

    @Operation(summary = "Read periodes by periodicity", description = "It take input param of periodicity and return periodes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/periodes/byPeriodicity")
    public List<Period> readPeriodByPeriodicity(@Parameter(name = "periodicity", description = "The periodicity to find it's periods")
                                                @RequestParam(name = "periodicity") Periodicity periodicity) {
        return Arrays.stream(Period.values())
                .filter(period -> period.getPeriodicity().equals(periodicity))
                .collect(Collectors.toList());
    }

}
