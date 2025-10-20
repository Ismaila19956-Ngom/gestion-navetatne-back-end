package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.models.ReunionDto;
import com.webgram.dgpsn.services.Impl.ReunionServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reunions")
@RequiredArgsConstructor
public class ReunionController {

    private final ReunionServiceImpl reunionService;

    @Operation(summary = "Create Reunion", description = "this endpoint takes input Reunion and saves it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> create(@RequestBody ReunionDto reunion) {
        try {
            var dto = reunionService.create(reunion);
            return Response.ok().setPayload(dto).setMessage("Reunion créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Read the Reunion", description = "This endpoint is used to read Reunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping("/{reunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> read(@Parameter(name = "reunionId", description = "the type Reunion id to valid") @PathVariable Long reunionId) {
        try {
            var dto = reunionService.read(reunionId);
            return Response.ok().setPayload(dto).setMessage("Reunion trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{reunionId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> update(@Parameter(name = "reunionId", description = "the Reunion id to updated") @PathVariable Long reunionId, @RequestBody ReunionDto reunion) {
        reunion.setId(reunionId);
        try {
            var dto = reunionService.update(reunion);
            return Response.ok().setPayload(dto).setMessage("Reunion modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "delete the Reunion", description = "Delete Reunion, it takes input id affectation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "No content"),
        @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
        @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{reunionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(name = "reunionId", description = "the Reunion id to be deleted") @PathVariable Long reunionId) {
        reunionService.delete(reunionId);
    }

    @Operation(summary = "Read all Reunion", description = "It takes input param of the page and returns this list related")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAll(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = reunionService.readAll(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
            .number(page.getNumber())
            .totalElements(page.getTotalElements())
            .size(page.getSize())
            .totalPages(page.getTotalPages())
            .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @Operation(summary = "Change status Reunion", description = "this endpoint takes input reunion id and new statut and updates it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{reunionId}/statut")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> changeStatusReunion(
        @Parameter(name = "reunionId", description = "the reunionId id to update") @PathVariable Long reunionId,
        @Parameter(name = "statut", description = "the new statut for the reunion")  @RequestParam Statut statut) {
    reunionService.changeStatus(reunionId, statut);
        return Response.ok();
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
        @Parameter(name = "conseiladministratifId", description = "the Id of the conseiladministratif to retrieve reunion") @PathVariable Long conseiladministratifId){
     List<ReunionDto> entites = reunionService.readByConseiladministratifId(conseiladministratifId);
        return Response.ok().setPayload(entites);
    }

    @Operation(summary = "Export Reunion to CSV", description = "Exports the list of Reunion to a CSV file")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping(value = "/export", produces = "text/csv")
    public void exportReunion(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        String fileName = String.format("Reunions.csv", LocalDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        reunionService.exportReunion(response.getWriter());
    }
}
