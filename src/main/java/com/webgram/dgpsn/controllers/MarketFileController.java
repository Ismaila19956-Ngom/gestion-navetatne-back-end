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
import com.webgram.dgpsn.models.MarketFileDTO;
import com.webgram.dgpsn.services.MarketFileService;

import java.util.List;

@RestController
@RequestMapping("/marketfile")
@Tag(name = "market-file-controller", description ="les dossiers du marches controller")
@RequiredArgsConstructor
public class MarketFileController {

    private final MarketFileService marketFileService;

    @Operation(summary = "Create market-file", description = "this endpoint take input market-file and save it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarketFileDTO createMarketFile(@RequestBody MarketFileDTO marketFileDTO) {
        return marketFileService.createMarketFile(marketFileDTO);
    }

//    @PutMapping("/{marketFileId}")
//    @ResponseStatus(HttpStatus.OK)
//    public MarketFileDTO updateMarketFile(@Parameter(name = "MarketFileId", description = "the structure id updated") @PathVariable Long marketFileId,
//                                        @RequestBody MarketFileDTO marketFileDTO) {
//        System.out.println("id:"+marketFileId);
//        return  null;
////        marketFileDTO.setId(marketFileId);
////        return marketFileService.updateMarketFile(marketFileDTO);
//    }

    @PutMapping("/{fileMarketId}")
    @ResponseStatus(HttpStatus.OK)
    public MarketFileDTO updateMarketFari(@Parameter(name = "fileMarketId", description = "the management unit id updated") @PathVariable Long fileMarketId, @RequestBody MarketFileDTO marketFileDTO) {
        marketFileDTO.setId(fileMarketId);
        return marketFileService.updateMarketFile(marketFileDTO);
    }
    @Operation(summary = "Read the market-file", description = "This endpoint is used to read passation-plan it take input id passationPlan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{fileMarketId}")
    @ResponseStatus(HttpStatus.OK)
    public MarketFileDTO readMarketFile(@Parameter(name = "fileMarketId", description = "the passation market id to read") @PathVariable Long fileMarketId) {
        return marketFileService.readMarketFile(fileMarketId);
    }

    @Operation(summary = "delete the marketFile", description = "Delete the passation-plan, it take input id the passation-plan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the the passation-plan was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{fileMarketId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarketFile(@Parameter(name = "fileMarketId", description = "the passation-plan-market id deleted") @PathVariable Long fileMarketId) {
        marketFileService.deleteMarketFile(fileMarketId);
    }

    @Operation(summary = "Read all passation-plan-market", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<MarketFileDTO> readAllMarketFile(
            Pageable pageable,
            @Parameter(name = "fileNumber", description = "value of label used to filter list passation-plan") @RequestParam(value = "fileNumber", required = false) String fileNumber,
            @Parameter(name = "email", description = "value of label used to filter list passation-plan") @RequestParam(value = "email", required = false) String email,
            @Parameter(name = "passationMarketId", description = "value of label used to filter list passation-plan") @RequestParam(value = "passationMarketId", required = false) Long  passationMarketId,
            @Parameter(name = "sortBy", description = "list of sortRequest used to filter list passation-plan") @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "list of ascending used to filter list passation-plan") @RequestParam(value = "ascending", required = false) Boolean ascending
            ) throws JsonProcessingException {
        return marketFileService.readAllMarketFile(pageable, fileNumber, email ,passationMarketId,sortBy, ascending);
    }

    @Operation(summary = "Read all passation-plan-market", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})

    @GetMapping("/listNotes")
    @ResponseStatus(HttpStatus.OK)
    public List<MarketFileDTO> getAllMarket() {
   return marketFileService.readAllMarketWithNtes();
    }

}
