package sn.naavetane.backend.controllers;

import sn.naavetane.backend.models.GestionContratDTO;
import sn.naavetane.backend.services.GestionContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agents/{agentId}/gestion-contrats")
@RequiredArgsConstructor
public class GestionContratController {

    private final GestionContratService contratService;

    // ------------------- GET ALL -------------------
    @Operation(summary = "Read all contracts of an agent", description = "Returns all contracts associated with a given agent by agentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved contracts"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Agent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    public ResponseEntity<List<GestionContratDTO>> getContratsByAgent(
            @Parameter(name = "agentId", description = "The ID of the agent to retrieve contracts for")
            @PathVariable Long agentId) {
        List<GestionContratDTO> contrats = contratService.findByAgentId(agentId);
        return ResponseEntity.ok(contrats);
    }

    // ------------------- CREATE -------------------
    @Operation(summary = "Create a new contract for an agent", description = "Creates a new contract for the given agentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contract successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "404", description = "Agent not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    public ResponseEntity<GestionContratDTO> createContratAgent(
            @Parameter(name = "agentId", description = "The ID of the agent to create a contract for")
            @PathVariable Long agentId,
            @Valid @RequestBody GestionContratDTO contratDTO) {

        contratDTO.setAgentId(agentId);
        GestionContratDTO created = contratService.create(contratDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ------------------- PATCH STATUS -------------------
    @Operation(summary = "Update contract status", description = "Activate or deactivate a contract. Activating a contract will deactivate other active contracts for the same agent.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Contract not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{contratId}/status")
    public ResponseEntity<GestionContratDTO> updateStatusContrat(
            @Parameter(name = "contratId", description = "The ID of the contract to update")
            @PathVariable Long contratId,
            @Parameter(name = "isActive", description = "The new active status")
            @RequestParam boolean isActive) {

        GestionContratDTO updated = contratService.updateStatusContrat(contratId, isActive);
        return ResponseEntity.ok(updated);
    }


    // Dans GestionContratController
    @PutMapping("/{contratId}")
    public ResponseEntity<GestionContratDTO> updateContrat(
            @PathVariable Long agentId,
            @PathVariable Long contratId,
            @Valid @RequestBody GestionContratDTO contratDTO) {
        contratDTO.setAgentId(agentId);
        GestionContratDTO updated = contratService.update(contratId, contratDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{contratId}")
    public ResponseEntity<Void> deleteContrat(
            @PathVariable Long agentId,
            @PathVariable Long contratId) {
        contratService.delete(contratId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<GestionContratDTO>> searchContrats(
            @PathVariable Long agentId,
            @RequestParam(required = false) String typeContratId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<GestionContratDTO> contrats = contratService.searchByAgentId(agentId, typeContratId, dateDebut, dateFin);
        return ResponseEntity.ok(contrats);
    }
}
