package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.models.InstructionDTO;
import com.webgram.dgpsn.models.requests.UpdateEtapeDTO;
import com.webgram.dgpsn.services.InstructionService;


@RestController
@RequestMapping("/instructions")
@Tag(name = "instructions-controller", description = "API de gestion des instructions")
@RequiredArgsConstructor
public class InstructionController {

    private final InstructionService instructionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructionDTO createInstruction(@RequestBody InstructionDTO instructionDTO) {
        return instructionService.create(instructionDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InstructionDTO updateInstruction(@PathVariable Long id, @RequestBody InstructionDTO instructionDTO) {
        return instructionService.update(id, instructionDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInstruction(@PathVariable Long id) {
        instructionService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<InstructionDTO> getAllInstructions(
            @RequestParam(required = false) Long promoteurId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) String intitule,
            Pageable pageable) {
        return instructionService.readAll(promoteurId, regionId, intitule, pageable);
    }

    @PatchMapping("/{id}/etape")
    @ResponseStatus(HttpStatus.OK)
    public InstructionDTO updateEtape(@PathVariable Long id, @Valid @RequestBody UpdateEtapeDTO etapeDTO) {
        return instructionService.updateEtape(id, etapeDTO);
    }
}