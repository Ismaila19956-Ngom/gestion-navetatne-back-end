package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issuelogComponent")
@Tag(name = "issuelogComponent-controller", description = "issuelogComponent controller")
@RequiredArgsConstructor
public class IssueLogComponentController {
//    private final IssueLogComponentService issueLogComponentService;

//    @Operation(summary = "Create issuelogComponent", description = "this endpoint take input issuelogComponent and save it")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the type country was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public IssueLogComponentDTO createIssuelogComponent(@RequestBody IssueLogComponentDTO issueLogComponentDTO) {
//        return issueLogComponentService.create(issueLogComponentDTO);
//    }
//
//    @Operation(summary = "Link many issuelog to component", description = "this endpoint take input many issuelogComponent and save it")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the type country was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping("/link")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createManyIssuelogComponent(@RequestBody List<IssueLogComponentDTO> issueLogComponentDTO) {
//        issueLogComponentService.linkManyIssueLog(issueLogComponentDTO);
//    }
//
//    @PutMapping("/{issuelogComponentId}")
//    @ResponseStatus(HttpStatus.OK)
//    public IssueLogComponentDTO updateIssuelogComponent(@Parameter(name = "issuelogComponentId", description = "the issuelogComponent id to updated") @PathVariable Long issuelogComponentId, @RequestBody IssueLogComponentDTO issueLogComponentDTO) {
//        issueLogComponentDTO.setId(issuelogComponentId);
//        return issueLogComponentService.update(issueLogComponentDTO);
//    }
//
//    @Operation(summary = "Read the issuelogComponent", description = "This endpoint is used to read country, it take input id issuelogComponent")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
//            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("/{issuelogComponentId}")
//    @ResponseStatus(HttpStatus.OK)
//    public IssueLogComponentDTO readIssuelogComponent(@Parameter(name = "issuelogComponentId", description = "the type issuelogComponent id to read") @PathVariable Long issuelogComponentId) {
//        return issueLogComponentService.read(issuelogComponentId);
//    }
//
//    @Operation(summary = "delete the issuelogComponent", description = "Delete issuelogComponent, it take input id issuelogComponent")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No content"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the action was syntactically incorrect"),
//            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @DeleteMapping("/{issuelogComponentId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteIssuelogComponent(@Parameter(name = "issuelogComponent", description = "the issuelogComponent id deleted") @PathVariable Long issuelogComponentId) {
//        issueLogComponentService.delete(issuelogComponentId);
//    }
//
//    @Operation(summary = "Read the issuelogComponent", description = "This endpoint is used to read issuelogComponent, it take input id issuelogComponent")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the issuelogComponent was syntactically incorrect"),
//            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("byComponent/{componentId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<IssueLogComponentDTO> readIssuelogComponentByComponent(@Parameter(name = "componentId", description = "the component id to read") @PathVariable Long componentId) {
//        return issueLogComponentService.getByComponent(componentId);
//    }
//
//    @Operation(summary = "Read all issueLogComponent", description = "It take input param of the page and turn this list related")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Success"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping
//    public Page<IssueLogComponentDTO> readAllIssueLogs(
//            Pageable pageable,
//            @Parameter(name = "projectId", description = "value of project used to filter list issueLogComponent") @RequestParam(value = "projectId", required = false) Long projectId
//    ) {
//        return issueLogComponentService.readAll(pageable, projectId);
//    }
}
