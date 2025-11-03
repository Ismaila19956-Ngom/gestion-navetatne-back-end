package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.NotationDTO;
import com.webgram.dgpsn.services.NotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notations")
@RequiredArgsConstructor
public class NotationController {

    private final NotationService notationService;

    /**
     * Créer une nouvelle notation pour un candidat
     */
    @PostMapping
    public ResponseEntity<NotationDTO> createNotation(@RequestBody NotationDTO dto) {
        return ResponseEntity.ok(notationService.createNotation(dto));
    }

    /**
     * Mettre à jour une notation existante
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotationDTO> updateNotation(@PathVariable Long id, @RequestBody NotationDTO dto) {
        return ResponseEntity.ok(notationService.updateNotation(id, dto));
    }

    /**
     * Supprimer une notation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotation(@PathVariable Long id) {
        notationService.deleteNotation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupérer une notation par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotationDTO> getNotationById(@PathVariable Long id) {
        return ResponseEntity.ok(notationService.getNotationById(id));
    }

    /**
     *  Récupérer toutes les notations d’un candidat
     */
    @GetMapping("/candidat/{candidatId}")
    public ResponseEntity<List<NotationDTO>> getNotationsByCandidat(@PathVariable Long candidatId) {
        return ResponseEntity.ok(notationService.getNotationsByCandidat(candidatId));
    }

    /**
     *  Liste de toutes les notations
     */
    @GetMapping
    public ResponseEntity<List<NotationDTO>> getAllNotations() {
        return ResponseEntity.ok(notationService.getAllNotations());
    }
}
