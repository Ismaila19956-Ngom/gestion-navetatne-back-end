package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.services.CandidatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @RequestParam(required = false) Boolean preselectionneEntretien,
            @RequestParam(required = false) Boolean selectionne,
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
                preselectionneEntretien,
                selectionne,
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
    public CandidatDTO saveOrUpdateCandidat(@RequestBody CandidatDTO dto) {
        return candidatService.saveOrUpdateCandidat(dto);
    }


    @DeleteMapping("/{id}")
    public void deleteCandidat(@PathVariable Long id) {
        candidatService.deleteCandidat(id);
    }
}
