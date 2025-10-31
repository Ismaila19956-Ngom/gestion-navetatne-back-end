package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.entities.enums.StatutType;
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
@Tag(name = "recrutement-controller", description = "Recrutement controller")
@RequiredArgsConstructor
public class RecrutementController {

    private final RecrutementService recrutementService;

    @Operation(summary = "Create recrutement", description = "this endpoint takes input recrutement and saves it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> createRecrutement(@RequestBody RecrutementDTO recrutementDTO) {
        try {
            var dto = recrutementService.createRecrutement(recrutementDTO);
          //    dto.setStatutType(StatutType.EN_COURS);
            return Response.ok().setPayload(dto).setMessage("Recrutement créé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> updateRecrutement(@PathVariable("id") Long id, @RequestBody RecrutementDTO recrutementDTO) {
        recrutementDTO.setId(id);
        try {
            var dto = recrutementService.updateRecrutement(recrutementDTO);
            return Response.ok().setPayload(dto).setMessage("Recrutement modifié");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }

    }

    @Operation(summary = "Read the recrutement", description = "This endpoint is used to read recrutement, it takes input id recrutement")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"), @ApiResponse(responseCode = "404", description = "Resource access does not exist"), @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readRecrutement(@PathVariable Long id) {
        try {
            var dto = recrutementService.readRecrutement(id);
            return Response.ok().setPayload(dto).setMessage("Recrutement trouvé");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> readAllRecrutement(@RequestParam Map<String, String> searchParams, Pageable pageable) {
        var page = recrutementService.readAllRecrutement(searchParams, pageable);
        Response.PageMetadata metadata = Response.PageMetadata.builder()
                .number(page.getNumber())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .build();
        return Response.ok().setPayload(page.getContent()).setMetadata(metadata);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecrutement(@PathVariable("id") Long id) {
        recrutementService.deleteRecrutement(id);
    }

    // ============================================================
    // 🔹 NOUVELLES MÉTHODES AJOUTÉES
    // ============================================================

    @Operation(summary = "Ajouter un candidat à un recrutement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Candidat ajouté au recrutement"),
            @ApiResponse(responseCode = "404", description = "Recrutement non trouvé")
    })
    @PostMapping("/{id}/candidats")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Object> addCandidatToRecrutement(
            @Parameter(description = "ID du recrutement") @PathVariable("id") Long idRecrutement,
            @Valid @RequestBody CandidatDTO dto
    ) {
        try {
            var saved = recrutementService.addCandidat(idRecrutement, dto);
            return Response.ok().setPayload(saved).setMessage("Candidat ajouté au recrutement");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @Operation(summary = "Lister les candidats d’un recrutement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidats récupérés avec succès"),
            @ApiResponse(responseCode = "404", description = "Recrutement non trouvé")
    })
    @GetMapping("/{id}/candidats")
    @ResponseStatus(HttpStatus.OK)
    public Response<Object> getCandidatsByRecrutement(
            @Parameter(description = "ID du recrutement") @PathVariable("id") Long idRecrutement
    ) {
        try {
            List<CandidatDTO> candidats = recrutementService.getCandidatsRecrutements(idRecrutement);
            return Response.ok().setPayload(candidats).setMessage("Liste des candidats du recrutement");
        } catch (Exception ex) {
            return Response.badRequest().setMessage(ex.getMessage());
        }
    }

    @PutMapping("/{idRecrutement}/candidats/{idCandidat}")
    public ResponseEntity<CandidatDTO> updateCandidatOfRecrutement(
            @PathVariable Long idRecrutement,
            @PathVariable Long idCandidat,
            @RequestBody CandidatDTO dto
    ) {
        var updated = recrutementService.updateCandidat(idRecrutement, idCandidat, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<RecrutementDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutType statutType) {
        return ResponseEntity.ok(recrutementService.updateStatut(id, statutType));
    }
}
