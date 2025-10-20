package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.models.CadreLogiqueDTO;
import com.webgram.dgpsn.models.responses.TreeNode;
import com.webgram.dgpsn.services.CadreLogiqueService;

import java.util.List;

@RestController
@RequestMapping("/cadreLogique")
@Tag(name = "cadre-logique-controller", description = "cadre logique controller")
@RequiredArgsConstructor
@Slf4j
public class CadreLogiqueController {
//    private final CadreLogiqueService cadreLogiqueService;
    private final CadreLogiqueService cadreLogiqueService;

    @Operation(summary = "Create cadre logique", description = "this endpoint take input cadre logique and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CadreLogiqueDTO createCadreLogique(@RequestBody CadreLogiqueDTO cadreLogique) {;
        return cadreLogiqueService.createCadreLogique(cadreLogique);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{cadreLogiqueId}")
    public CadreLogiqueDTO updateCadreLogique(@PathVariable Long cadreLogiqueId, @Parameter(name = "cadreLogique", description = "the cadre logique updated") @RequestBody CadreLogiqueDTO cadreLogique) {
        cadreLogique.setId(cadreLogiqueId);
        return cadreLogiqueService.updateCadreLogique(cadreLogique);
    }

    @Operation(summary = "Read the cadre logique", description = "This endpoint is used to read cadre Logique it take input id cadre logique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{cadreLogiqueId}")
    @ResponseStatus(HttpStatus.OK)
    public CadreLogiqueDTO readCadreLogique(@Parameter(name = "CadreLogiqueId", description = "the type cadre logique id to read") @PathVariable Long cadreLogiqueId) {
        return cadreLogiqueService.readCadreLogique(cadreLogiqueId);
    }

    @Operation(summary = "delete the cadre logique", description = "Delete cadre logique, it take input id cadre logique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{cadreLogiqueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCadreLogique(@Parameter(name = "cadreLogiqueId", description = "the cadre logique id deleted") @PathVariable Long cadreLogiqueId) {
        cadreLogiqueService.deleteCadreLogique(cadreLogiqueId);
    }

    @Operation(summary = "Read all cadre logique", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CadreLogiqueDTO> readAllCadreLogique(
            Pageable pageable,
    @Parameter(name = "codeType", description = "value of Code type cadre logique used to filter list cadre logique") @RequestParam(value = "codeType", required = false) CadreLogiqueType codeType,
            @Parameter(name = "code", description = "value of label used to filter list cadre logique") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list cadre logique") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "region", description = "value of region used to filter list cadre logique") @RequestParam(value = "regionId", required = false) Long regionId,
            @Parameter(name = "departementId", description = "value of department used to filter list cadre logique") @RequestParam(value = "departementId", required = false) Long departementId,
            @Parameter(name = "arrondissementId", description = "value of arrondissement used to filter list cadre logique") @RequestParam(value = "arrondissementId", required = false) Long arrondissementId
    ) {
        return cadreLogiqueService.readAllCadreLogique(pageable,code,libelle, codeType, regionId, departementId, arrondissementId);
    }

    @Operation(summary = "Read tree cadre logique", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/tree/{projectId}")
    public List<TreeNode> readTreeCadreLogique(@Parameter(name = "projectId", description = "the project id") @PathVariable Long projectId) {
        log.info("cadrelogique ids {}", projectId);
       return cadreLogiqueService.readTreeCadreLogique(projectId);
    }

    @Operation(summary = "Read all cadre logique", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/regions")
    public List<CadreLogiqueDTO> readRegions(
            Pageable pageable,
            @Parameter(name = "projectId", description = "the project id") @RequestParam(value = "projectId", required = false) Long projectId
    ) {
        return cadreLogiqueService.readRegions(pageable,projectId);
    }

    @Operation(summary = "Read tree cadre logique", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/byParent/{cadreLogiqueId}")
    public List<CadreLogiqueDTO> readByParent(
            @Parameter(name = "cadreLogiqueId", description = "the cadre logique id") @PathVariable Long cadreLogiqueId,
            @Parameter(name = "projectId", description = "the project id")  @RequestParam(value = "projectId", required = false) Long projectId
            ) {
        log.info("cadrelogique id avant {}", cadreLogiqueId);
        var cadres = cadreLogiqueService.readByParent(cadreLogiqueId, projectId);
        log.info("cadrelogique id après {}", projectId);
        return cadres;
    }


}
