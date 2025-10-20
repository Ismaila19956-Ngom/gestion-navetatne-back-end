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
import com.webgram.dgpsn.models.UpdatePasswordDTO;
import com.webgram.dgpsn.models.UserDTO;
import com.webgram.dgpsn.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "user controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create user", description = "this endpoint take input user and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type user was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody UserDTO user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@Parameter(name = "userId", description = "the user id updated") @PathVariable Long userId,
                              @RequestBody UserDTO user) {
        user.setId(userId);
        return userService.updateUser(user);
    }
    @Operation(summary = "Read the user", description = "This endpoint is used to read user  it take input id user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO readUser(@Parameter(name = "userId", description = "the user id to read") @PathVariable Long userId) {
        return userService.readUser(userId);
    }

    @Operation(summary = "delete the user", description = "Delete user , it take input   id user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the user was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@Parameter(name = "userId", description = "the user id deleted") @PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @Operation(summary = "Read all users", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<UserDTO> readAllUser(
            Pageable pageable,
            @Parameter(name = "structureId", description = "value of structure used to filter list user") @RequestParam(value = "structureId", required = false) Long structureId,
            @Parameter(name = "profileId", description = "value of structure used to filter list user") @RequestParam(value = "profileId", required = false) Long profileId,
            @Parameter(name = "login", description = "value of structure used to filter list user") @RequestParam(value = "login", required = false) String login,
            @Parameter(name = "agentId", description = "value of structure used to filter list user") @RequestParam(value = "agentId", required = false)  Long agentId,
            @Parameter(name = "agentId", description = "value of status used to filter list user") @RequestParam(value = "status", required = false)  Boolean status,
            @Parameter(name = "loginToExcludes", description = "value of loginToExcludes used to filter list user") @RequestParam(value = "loginToExcludes", required = false) List<String> loginToExcludes
    )

    {
        return userService.readAllUsers(pageable, login, agentId, status, structureId, profileId, loginToExcludes);
    }

    @Operation(summary = "Active or desactive user", description = "this endpoint take input user id and active or desactive it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type user was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping("/activeOrDesactive/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void activeOrDesactive(@PathVariable Long userId) {
        userService.activeOrDesactiveUser(userId);
    }

    @Operation(summary = "Active or desactive user", description = "this endpoint take input user id and active or desactive it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type user was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@RequestBody UpdatePasswordDTO user) {
      //  System.out.println("password");
        userService.updatePassWordUser(user);
    }
}
