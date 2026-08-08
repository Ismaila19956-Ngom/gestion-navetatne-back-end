package sn.naavetane.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.models.UserDTO;
import sn.naavetane.backend.models.requests.LoginFormDTO;
import sn.naavetane.backend.models.requests.TemporaryCodeLoginRequest;
import sn.naavetane.backend.models.responses.SignInAuthentication;
import sn.naavetane.backend.security.SecurityPermissions;
import sn.naavetane.backend.services.AuthenticationService;
import sn.naavetane.backend.services.TemporaryAccessCodeService;
import sn.naavetane.backend.services.UserService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@Tag(name = "authentification-controller", description = "authentification controller")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;


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

    @Operation(summary = "sign up a new user", description = "Create a new user and log them in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public SignInAuthentication signUp(@RequestBody sn.naavetane.backend.models.requests.SignUpDTO signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    /**
     * Retourne les permissions actuelles de l'utilisateur connecté, depuis la BDD (pas le JWT).
     * Permet au frontend de rafraîchir la sidebar sans se reconnecter après une modification de profil.
     */
    @Operation(summary = "Get current user permissions", description = "Returns up-to-date permissions from DB for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public Map<String, Object> getCurrentUserPermissions(Principal principal) {
        Map<String, Object> result = new HashMap<>();

        if (principal == null) {
            result.put("permissions", Collections.emptyList());
            return result;
        }

        try {
            UserDTO user = userService.readUserByLogin(principal.getName());
            List<String> permissions = Collections.emptyList();

            if (user != null && user.getProfile() != null && user.getProfile().getPermissions() != null) {
                permissions = user.getProfile().getPermissions()
                        .stream()
                        .map(SecurityPermissions::name)
                        .collect(Collectors.toList());
            }

            result.put("login", principal.getName());
            result.put("permissions", permissions);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des permissions pour {}", principal.getName(), e);
            result.put("permissions", Collections.emptyList());
        }

        return result;
    }
}

