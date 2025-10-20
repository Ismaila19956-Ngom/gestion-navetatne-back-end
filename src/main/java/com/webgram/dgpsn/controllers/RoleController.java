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
import com.webgram.dgpsn.models.RoleDTO;
import com.webgram.dgpsn.services.RoleService;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@Tag(name = "role-controller", description = "role controller")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "Create role", description = "this endpoint take input role and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDTO createRole(@RequestBody RoleDTO role) {
        return roleService.createRole(role);
    }

    @PutMapping("/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO updateRole(@Parameter(name = "roleId", description = "the role id updated") @PathVariable Long roleId,
                              @RequestBody RoleDTO role) {
        role.setId(roleId);
        return roleService.updateRole(role);
    }

    @Operation(summary = "Read role", description = "This endpoint is used to read cadre Logique it take input id cadre logique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public RoleDTO readRole(@Parameter(name = "roleId", description = "the type role id to read") @PathVariable Long roleId) {
        return roleService.readRole(roleId);
    }

    @Operation(summary = "delete the role", description = "Delete role, it take input id role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@Parameter(name = "roleId", description = "the cadre roleId deleted") @PathVariable Long roleId) {
        roleService.deleteRole(roleId);
    }

    @Operation(summary = "Read all role", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<RoleDTO> readAllRole(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list role") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of libelle used to filter list role") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "ugp", description = "value of ugp used to filter list role") @RequestParam(value = "ugp", required = false) Boolean ugp,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list indicator") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list indicator") @RequestParam(value = "ascending", required = false) Boolean ascending

    ) {
        return roleService.readAllRole(pageable, code, libelle, ugp,sortBy,ascending);
    }

    @GetMapping("/roleBy")
    public Set<RoleDTO> readAllRoleUgp(
            @Parameter(name = "projectId", description = "value of projectId used to filter list role") @RequestParam(value = "projectId", required = false) Long projectId

    ) {
        return roleService.readAllRoleUgp(projectId);
    }

}
