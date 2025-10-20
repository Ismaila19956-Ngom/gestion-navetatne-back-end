package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.CategorieAlerte;
import com.webgram.dgpsn.mappers.TemplateMapper;

import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.models.TemplateDto;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.entities.enums.TypeAlerte;
import com.webgram.dgpsn.services.TemplateService;
import java.util.Set;


@RestController
@RequestMapping("/templates")
@Tag(name = "template-controller", description = "template controller")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    private final TemplateMapper templateMapper;

    @Operation(summary = "Create status", description = "this endpoint take input status and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateDto createTemplate(@Valid @RequestBody TemplateDto templateDto) {
        templateDto.setId(null);
        return templateService.create(templateDto);
    }

    @PutMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateDto updateTemplate(
            @Parameter(name = "templateId", description = "the template id to read") @PathVariable Long templateId,
            @RequestBody TemplateDto templateDto) {
        templateDto.setId(templateId);

        return templateService.update(templateDto);

    }

    @Operation(summary = "Read the template", description = "This endpoint is used to read template  it take input id status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the template was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{templateId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateDto read(@Parameter(name = "templateId", description = "the template id to read") @PathVariable Long templateId) {
        return templateService.read(templateId);
    }

    @Operation(summary = "delete the template", description = "Delete template, it take input   id template")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the shelf was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{templateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTemplate(@Parameter(name = "templateId", description = "the template id deleted") @PathVariable Long templateId) {
        templateService.delete(templateId);
    }

    @Operation(summary = "Read all template", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Response<Object> readAll(
            Pageable pageable,
            @Parameter(name = "libelle", description = "value of label used to filter list template") @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "typeAlerte", description = "value of alertType used to filter list template") @RequestParam(value = "typeAlerte", required = false) TypeAlerte typeAlerte,
            @Parameter(name = "priority", description = "value of alertType used to filter list template") @RequestParam(value = "priority", required = false) Priority priority,
            @Parameter(name = "categorieAlerte", description = "value of categorieAlerte used to filter list template") @RequestParam(value = "categorieAlerte", required = false) CategorieAlerte categorieAlerte
    ) {
        var pageTemplate = templateService.readAll(pageable, libelle,categorieAlerte,typeAlerte,priority);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(pageTemplate.getNumber())
                .totalElements(pageTemplate.getTotalElements())
                .size(pageTemplate.getSize())
                .build();
        Response<Object> response = Response
                .ok().setPayload(pageTemplate.getContent())
                .setMetadata(metadata);
        return response;

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAllTypeAlerte")
    public Set<TypeAlerte> readAllTypeAlerte(@RequestParam CategorieAlerte categorieAlerte) {
        return templateService.readAlertTypes(categorieAlerte);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAllPriorities")
    public Set<Priority> readAllPriorities() {
        return Priority.getAllPriorities();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/readAllCategorieAlerte")
    public Set<CategorieAlerte> readAllCategorieAlerte() {
        return CategorieAlerte.getAllCategorieAlerte();
    }

}
