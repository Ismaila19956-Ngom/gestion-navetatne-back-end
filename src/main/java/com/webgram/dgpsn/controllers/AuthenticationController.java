package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.requests.LoginFormDTO;
import com.webgram.dgpsn.models.requests.TemporaryCodeLoginRequest;
import com.webgram.dgpsn.models.responses.SignInAuthentication;
import com.webgram.dgpsn.services.AuthenticationService;
import com.webgram.dgpsn.services.TemporaryAccessCodeService;


@RestController
@RequestMapping("/auth")
@Tag(name = "authentification-controller", description = "authentification controller")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @Operation(summary = "sign in by login and password", description = "dhdg")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signIn")
    public SignInAuthentication signIn(@RequestBody LoginFormDTO loginRequest) {
        return authenticationService.signIn(loginRequest.getLogin(), loginRequest.getPassword());
    }
}
