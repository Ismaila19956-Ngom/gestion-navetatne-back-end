package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.CessationPerduService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;


@RestController
@RequestMapping("/cessationPerdu")
@Tag(name = "cessation-controller", description = "cessation perdu  controller")
@RequiredArgsConstructor
public class CessationPerduController {
    private final CessationPerduService cessationService;

    @Operation(summary = "Read the cessation", description = "This endpoint is used to read a single cessation. it take input cessation id")
    @ApiResponses(value = {@ApiResponse(responseCode = "400", description = "Request sent by the cessation was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{cessationPerduId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readPerduCessation(@Parameter(name ="cessationPerduId", description = "the cessation id to read") @PathVariable Long cessationPerduId) {
        return Response.ok().setPayload(cessationService.readPerdu(cessationPerduId));
    }

    @Operation(summary = "Read all cessations", description = "It return list of cessations")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/listPerdu")
    public Response<Object> readListPerduCessation() {
        return Response.ok().setPayload(cessationService.readAll());
    }

    @Operation(summary = "Read cessations per page", description = "It take input param of the page and turn this list related")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pagePerdu")
    public Response<Object> readPagePerduCessation(@RequestParam Map<String, String> searchParams, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size) throws ParseException {
        var pageCessation = cessationService.readPage(searchParams, page, size);
        Response.PageMetadata metadata = Response.PageMetadata.builder().number(pageCessation.getNumber()).totalElements(pageCessation.getTotalElements()).size(pageCessation.getSize()).build();
        return Response.ok().setPayload(pageCessation.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "delete the cessation", description = "Delete cessation, it take input cessation id")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the cessation was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @DeleteMapping("/{cessationPerduId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response<Object> deletePerduCessation(@Parameter(name = "cessationPerduId", description = "the cessation id deleted") @PathVariable Long cessationPerduId) {
        cessationService.delete(cessationPerduId);
        return Response.deleted();
    }

}

