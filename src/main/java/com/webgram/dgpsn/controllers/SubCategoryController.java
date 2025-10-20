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
import com.webgram.dgpsn.models.SubCategoryDTO;
import com.webgram.dgpsn.services.SubCategoryService;

@RestController
@RequestMapping("/subCategories")
@Tag(name = "subCategory-controller", description = "subCategory controller")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @Operation(summary = "Create subCategory", description = "this endpoint take input subCategory and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the subCategory was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubCategoryDTO createSubCategory(@RequestBody SubCategoryDTO subCategoryDTO) {
        return subCategoryService.createSubCategory(subCategoryDTO);
    }

    @PutMapping("/{subCategoryId}")
    @ResponseStatus(HttpStatus.OK)
    public SubCategoryDTO updateSubCategory(@Parameter(name = "subCategoryId", description = "the subCategory id updated") @PathVariable Long subCategoryId
            , @RequestBody SubCategoryDTO subCategoryDTO) {
        subCategoryDTO.setId(subCategoryId);
        return subCategoryService.updateSubCategory(subCategoryDTO);
    }

    @Operation(summary = "Read the subCategory", description = "This endpoint is used to read subCategory  it take input id subCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{subCategoryId}")
    @ResponseStatus(HttpStatus.OK)
    public SubCategoryDTO readSubCategory(@Parameter(name = "subCategoryId", description = "the subCategory id to read") @PathVariable Long subCategoryId) {
        return subCategoryService.readSubCategory(subCategoryId);
    }

    @Operation(summary = "delete the subCategory", description = "Delete subCategory , it take input   id subCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the subCategory was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{subCategoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubCategory(@Parameter(name = "subCategoryId", description = "the subCategory id deleted") @PathVariable Long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
    }

    @Operation(summary = "Read all subCategory", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<SubCategoryDTO> readAllSubCategories(
            Pageable pageable,
            @Parameter(name = "code", description = "value of code used to filter list subCategory ") @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "value of label used to filter list subCategory ") @RequestParam(value = "libelle", required = false) String libelle
    ) {

        return subCategoryService.readAllSubCategory(pageable, code, libelle);

    }

}
