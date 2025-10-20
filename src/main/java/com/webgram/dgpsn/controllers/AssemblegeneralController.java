package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.AssemblegeneralDto;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.Impl.AssemblegeneralServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assemblegenerals")
@RequiredArgsConstructor
public class AssemblegeneralController {

    private final AssemblegeneralServiceImpl assemblegeneralService;

    @Operation(summary = "Create Assemblegeneral", description = "this endpoint takes input Assemblegeneral and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createAssemblegeneral(@RequestBody AssemblegeneralDto assemblegeneral) {
        try {
            var dto = assemblegeneralService.create(assemblegeneral);
            return Response.ok().setPayload(dto).setMessage("Assemblegeneral créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Assemblegeneral", description = "This endpoint is used to read Assemblegeneral, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{assemblegeneralId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAssemblegeneral(@Parameter(name = "assemblegeneralId", description = "the type Assemblegeneral id to valid") @PathVariable Long assemblegeneralId) {
        try {
            var dto = assemblegeneralService.read(assemblegeneralId);
            return Response.ok().setPayload(dto).setMessage("Assemblegeneral trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{assemblegeneralId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateAssemblegeneral(@Parameter(name = "assemblegeneralId", description = "the Assemblegeneral id to updated") @PathVariable Long assemblegeneralId, @RequestBody AssemblegeneralDto assemblegeneral) {
        assemblegeneral.setId(assemblegeneralId);
        try {
            var dto = assemblegeneralService.update(assemblegeneral);
            return Response.ok().setPayload(dto).setMessage("Assemblegeneral modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Assemblegeneral", description = "Delete Assemblegeneral, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{assemblegeneralId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssemblegeneral(@Parameter(name = "assemblegeneralId", description = "the Assemblegeneral id to be deleted") @PathVariable Long assemblegeneralId) {
        assemblegeneralService.delete(assemblegeneralId);
    }

    @Operation(summary = "Read all Assemblegeneral", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllAssemblegenerals(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = assemblegeneralService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Read By  Entreprise", description = "this endpoint takes input ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/entreprise/{entrepriseId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readByEntrepriseId(
        @Parameter(name = "entrepriseId", description = "the Id of the entreprise to retrieve assemblegeneral") @PathVariable Long entrepriseId){
     List<AssemblegeneralDto> entites = assemblegeneralService.readByEntrepriseId(entrepriseId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Assemblegeneral to CSV", description = "Exports the list of Assemblegeneral to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportAssemblegeneral(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Assemblegenerals.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        assemblegeneralService.exportAssemblegeneral(response.getWriter());
    }
}
