package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import com.webgram.dgpsn.services.Impl.MembreServiceImpl;
import com.webgram.dgpsn.models.MembreDto;
import com.webgram.dgpsn.models.Response;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/membres")
@RequiredArgsConstructor
public class MembreController {

    private final MembreServiceImpl membreService;

    @Operation(summary = "Create Membre", description = "this endpoint takes input Membre and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody MembreDto membre) {
        try {
            var dto = membreService.create(membre);
            return Response.ok().setPayload(dto).setMessage("Membre créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Membre", description = "This endpoint is used to read Membre, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{membreId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "membreId", description = "the type Membre id to valid") @PathVariable Long membreId) {
        try {
            var dto = membreService.read(membreId);
            return Response.ok().setPayload(dto).setMessage("Membre trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{membreId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "membreId", description = "the Membre id to updated") @PathVariable Long membreId, @RequestBody MembreDto membre) {
        membre.setId(membreId);
        try {
            var dto = membreService.update(membre);
            return Response.ok().setPayload(dto).setMessage("Membre modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Membre", description = "Delete Membre, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{membreId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "membreId", description = "the Membre id to be deleted") @PathVariable Long membreId) {
        membreService.delete(membreId);
    }

    @Operation(summary = "Read all Membre", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = membreService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  ConseilAdministratif", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/conseiladministratif/{conseiladministratifId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByConseiladministratifId(
        @Parameter(name = "conseiladministratifId", description = "the Id of the conseiladministratif to retrieve membre") @PathVariable Long conseiladministratifId){
     List<MembreDto> entites = membreService.readByConseiladministratifId(conseiladministratifId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Membre to CSV", description = "Exports the list of Membre to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportMembre(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Membres.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        membreService.exportMembre(response.getWriter());
    }
}
