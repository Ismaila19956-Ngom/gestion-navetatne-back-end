package com.webgram.dgpsn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.webgram.dgpsn.models.NoteFileDTO;
import com.webgram.dgpsn.services.NoteFileService;
import com.webgram.dgpsn.services.PassationMarketCritereService;

import java.util.List;

@RestController
@RequestMapping("/notefile")
@Tag(name = "note-file-controller", description ="les notes des dossiers du marche controller")
@RequiredArgsConstructor
public class NoteFileController {

    private final NoteFileService noteFileService;
    private final PassationMarketCritereService passationMarketCritereService;
    @Operation(summary = "Create note-file", description = "this endpoint take input note-file and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<NoteFileDTO> createNoteFile(@RequestBody List<NoteFileDTO> noteFileDTO) {
//        System.out.println("fariControlleur"+noteFileDTO);
//        return null;
        return noteFileService.createNoteFile(noteFileDTO);
    }


    @PutMapping("{noteId}/{marketId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteFileDTO> updateMarketFari(@Parameter(name = "noteId", description = "the management unit id updated") @PathVariable List<Long> noteId,@PathVariable Long marketId, @RequestBody List<NoteFileDTO> noteFileDTO) {
       var listCriterMarke= passationMarketCritereService.readAllCriterePerMarket(marketId);
        if(listCriterMarke.size()==noteFileDTO.size()){
            System.out.println("elles sont egaux");
            noteFileService.updateNoteFile(noteFileDTO,noteId);
        }else if(listCriterMarke.size() != noteFileDTO.size()) {
            System.out.println("pas egal");
        }

        return null;
    }

    @Operation(summary = "Read ", description = "This endpoint is used to read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("folder/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public List<NoteFileDTO> readFileTst(@Parameter(name = "folderId", description = "the passation market id to read") @PathVariable Long folderId) {


         return noteFileService.readNoteFileperdossier(folderId);
    }

    @Operation(summary = "Read the market-file", description = "This endpoint is used to read passation-plan it take input id passationPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public NoteFileDTO readNoteFile(@Parameter(name = "noteId", description = "the passation market id to read") @PathVariable Long noteId) {
        return noteFileService.readNoteFile(noteId);
    }

    @Operation(summary = "delete the marketFile", description = "Delete the passation-plan, it take input id the passation-plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNoteFile(@Parameter(name = "noteId", description = "the passation-plan-market id deleted") @PathVariable Long noteId) {
        noteFileService.deleteNoteFile(noteId);
    }

    @Operation(summary = "Read all passation-plan-market", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<NoteFileDTO> readAllNoteFile(
            Pageable pageable,
            @Parameter(name = "note", description = "value of label used to filter list passation-plan") @RequestParam(value = "note", required = false) Double note,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list passation-plan") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return noteFileService.readAllNoteFile(pageable, note ,sortBy, ascending);
    }




}
