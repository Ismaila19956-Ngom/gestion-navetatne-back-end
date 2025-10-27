package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.RecrutementDTO;
import com.webgram.dgpsn.models.Response;
import com.webgram.dgpsn.services.RecrutementService;
import com.webgram.dgpsn.services.RecrutementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recrutements")
@Tag(name = "role-controller", description = "role controller")
@RequiredArgsConstructor
public class RecrutementController {
    private final RecrutementService recrutementService;

//
//    @Operation(summary = "Create recrutement", description = "this endpoint takes input recrutement and saves it")
//    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @PostMapping()
//    @ResponseStatus(HttpStatus.CREATED)
//    public Response<Object> createRecrutement(@RequestBody RecrutementDTO recrutementDTO) {
//        try {
//            var dto = recrutementService.createRecrutement(recrutementDTO);
//            return Response.ok().setPayload(dto).setMessage("Recrutement créé");
//        } catch (Exception ex) {
//            return Response.badRequest().setMessage(ex.getMessage());
//        }
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRecrutement(@Parameter(name = "id", description = "the recrutement id to updated") @PathVariable("id") Long id, @RequestBody RecrutementDTO recrutementDTO) {
        recrutementDTO.setId(id);
        try {
            var dto = recrutementService.updateRecrutement(recrutementDTO);
            return Response.ok().setPayload(dto).setMessage("recrutement modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

//    @Operation(summary = "Read the recrutement", description = "This endpoint is used to read recrutement, it takes input id recrutement")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Response<Object> getRecrutement(@Parameter(name = "id", description = "the type recrutement id to valid") @PathVariable Long id) {
//        try {
//            var dto = recrutementService.getRecrutement(id);
//            return Response.ok().setPayload(dto).setMessage("recrutement trouvé");
//        } catch (Exception ex) {
//            return Response.badRequest().setMessage(ex.getMessage());
//        }
//    }
//
//    @Operation(summary = "Read all Budget", description = "It takes input param of the page and returns this list related")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public Response<Object> getAllRecrutements(@RequestParam Map<String, String> searchParams, Pageable pageable) {
//        var page = recrutementService.getAllRecrutements(searchParams, pageable);
//        Response.PageMetadata metadata = Response.PageMetadata.builder().number(page.getNumber()).totalElements(page.getTotalElements()).size(page.getSize()).totalPages(page.getTotalPages()).build();
//        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
//    }


//    @Operation(summary = "delete the recrutement", description = "Delete recrutement, it takes input id recrutement")
//    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "No content"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteRecrutement(@PathVariable("id") Long id) {
//        try {
//            recrutementService.deleteRecrutement(id);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
