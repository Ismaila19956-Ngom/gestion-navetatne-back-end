package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.FolderDto;
import com.webgram.dgpsn.services.FolderService;

import java.util.List;

@RestController
@RequestMapping("/folders")
@Tag(name = "folder-controller", description = "folder controller")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @Operation(summary = "Create folder", description = "this endpoint take input folder and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FolderDto createFolder(@RequestBody FolderDto folderDTO) {
        return folderService.create(folderDTO);
    }

    @Operation(summary = "Create sub folder", description = "this endpoint take input folder and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the type cadre logique was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping("/{folderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FolderDto createSubFolder(@PathVariable Long folderId, @RequestBody FolderDto subFolder) {
        return folderService.createSubFolder(folderId, subFolder);
    }


    @PutMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public FolderDto updateFolder(@Parameter(name = "folderId", description = "the folder id to updated") @PathVariable Long folderId, @RequestBody FolderDto folderDTO) {
        return folderService.update(folderId, folderDTO);
    }

    @Operation(summary = "Read the folder", description = "This endpoint is used to read folder, it take input id folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public FolderDto readFolder(@Parameter(name = "folderId", description = "the type folder id to read") @PathVariable Long folderId) {
        return folderService.read(folderId);
    }

    @Operation(summary = "delete the folder", description = "Delete folder, it take input id folder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the action was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFolder(@Parameter(name = "folderId", description = "the folder id deleted") @PathVariable Long folderId) {
        folderService.delete(folderId);
    }

    @Operation(summary = "Read all parents folders", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<FolderDto> readParentsOnly() {
        return folderService.readParentsOnly();
    }

    @Operation(summary = "Read all folders", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<FolderDto> readAllFolder() {
        return folderService.readAll();
    }

}
