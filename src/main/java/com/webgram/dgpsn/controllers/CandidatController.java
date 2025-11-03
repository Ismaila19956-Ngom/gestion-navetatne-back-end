package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.entities.enums.StatusCadidature;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.services.CandidatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidats")
@RequiredArgsConstructor
public class CandidatController {

    private final CandidatService candidatService;

    @GetMapping()
    public Page<CandidatDTO> getCandidatsFiltered(
            @RequestParam(required = false) List<Long> idsToIgnore,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String prenom,
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) NiveauEtude niveauEtude,
            @RequestParam(required = false) ExperienceProfessionnelle experience,
            @RequestParam(required = false) StatusCadidature statusCadidature,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") Boolean ascending
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return candidatService.getCandidatsFiltered(
                pageable,
                idsToIgnore,
                nom,
                prenom,
                adresse,
                niveauEtude,
                experience,
                statusCadidature,
                sortBy,
                ascending
        );
    }


    @GetMapping("/{matricule}")
    public CandidatDTO getCandidatByMatricule(@PathVariable String matricule) {
        return candidatService.getCandidatByMatricule(matricule);
    }

    @GetMapping("/id/{id}")
    public CandidatDTO getCandidatById(@PathVariable Long id) {
        return candidatService.getCandidatById(id);
    }

    @PostMapping
    public CandidatDTO saveCandidat(@RequestBody CandidatDTO dto) {
        return candidatService.saveCandidat(dto);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<CandidatDTO> partialUpdate(
            @PathVariable Long id,
            @RequestBody CandidatDTO dto) {
        return ResponseEntity.ok(candidatService.updateCandidat(id, dto));
    }

    @DeleteMapping("/{id}")
    public void deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
    }
}
