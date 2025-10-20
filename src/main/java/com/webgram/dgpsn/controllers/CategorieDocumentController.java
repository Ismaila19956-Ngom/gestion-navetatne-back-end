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
import com.webgram.dgpsn.entities.enums.CategoryDocument;
import com.webgram.dgpsn.models.CategorieDocumentDTO;
import com.webgram.dgpsn.services.CategorieDocumentService;

import java.util.List;

@RestController
@RequestMapping("/categorieDocument")
@Tag(name = "Categorie-Document-controller", description = "Categorie-Document controller")
@RequiredArgsConstructor
public class CategorieDocumentController {
    private final CategorieDocumentService categorieDocumentService;

    @Operation(summary = "Create label", description = "this endpoint take input label and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the label was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategorieDocumentDTO createCategorieDocument(@RequestBody CategorieDocumentDTO categorieDocumentDTO) {
        return categorieDocumentService.create(categorieDocumentDTO);
    }

    @PutMapping("/{docId}")
    @ResponseStatus(HttpStatus.OK)
    public CategorieDocumentDTO updateCategorieDocument(@Parameter(name = "docId", description = "the label id to updated") @PathVariable Long docId, @RequestBody CategorieDocumentDTO categorieDocumentDTO) {
        categorieDocumentDTO.setId(docId);
        return categorieDocumentService.update(categorieDocumentDTO);
    }

    @Operation(summary = "Read the label", description = "This endpoint is used to read label, it take input id label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{docId}")
    @ResponseStatus(HttpStatus.OK)
    public CategorieDocumentDTO readLabel(@Parameter(name = "docId", description = "the type label id to read") @PathVariable Long docId) {
        return categorieDocumentService.read(docId);
    }

    @Operation(summary = "delete the label", description = "Delete label, it take input id label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the label was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{docId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@Parameter(name = "docId", description = "the label id deleted") @PathVariable Long docId) {
        categorieDocumentService.delete(docId);
    }

    @Operation(summary = "Read all label", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CategorieDocumentDTO> readAllLabel(
            Pageable pageable,
            @Parameter(name = "categoryDocument", description = "value of categoryDocument used to filter list label") @RequestParam(value = "categoryDocument", required = false)CategoryDocument categoryDocument
    ) {
        return categorieDocumentService.readAll(pageable, categoryDocument);
    }

    @Operation(summary = "Read all categorieDocument type", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categoryDocument")
    public List<CategoryDocument> readAllCategorieDocumentType() {
        return List.of(CategoryDocument.values());
    }

}
