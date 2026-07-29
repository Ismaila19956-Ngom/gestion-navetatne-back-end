package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.SaisonDTO;
import sn.naavetane.backend.entities.SaisonEntity;
import sn.naavetane.backend.repositories.SaisonRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.naavetane.backend.services.AuditService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/saisons")
@RequiredArgsConstructor
public class SaisonController {

    private final SaisonRepository saisonRepository;
    private final AuditService auditService;

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Système";
    }

    private SaisonDTO toDto(SaisonEntity s) {
        return SaisonDTO.builder()
                .id(s.getId())
                .libelle(s.getLibelle())
                .isActive(s.isActive())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<SaisonDTO>> getSaisons() {
        return ResponseEntity.ok(saisonRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<SaisonDTO> createSaison(@RequestBody SaisonDTO dto) {
        // Si c'est la première saison, on l'active par défaut
        boolean shouldBeActive = dto.isActive() || saisonRepository.count() == 0;
        
        SaisonEntity saison = SaisonEntity.builder()
                .libelle(dto.getLibelle())
                .isActive(shouldBeActive)
                .build();
                
        SaisonEntity saved = saisonRepository.save(saison);
        
        if (shouldBeActive) {
            activateSaisonInternal(saved.getId()); // désactive les autres
        }

        auditService.logAction(getCurrentUser(), "CREATION", "Saisons", "Création de la saison " + dto.getLibelle(), "IP_LOCALE");
        
        return ResponseEntity.ok(toDto(saisonRepository.findById(saved.getId()).get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaisonDTO> updateSaison(@PathVariable UUID id, @RequestBody SaisonDTO dto) {
        return saisonRepository.findById(id).map(saison -> {
            saison.setLibelle(dto.getLibelle());
            SaisonEntity updated = saisonRepository.save(saison);
            auditService.logAction(getCurrentUser(), "MODIFICATION", "Saisons", "Modification de la saison " + dto.getLibelle(), "IP_LOCALE");
            return ResponseEntity.ok(toDto(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaison(@PathVariable UUID id) {
        saisonRepository.findById(id).ifPresent(saison -> {
            auditService.logAction(getCurrentUser(), "SUPPRESSION", "Saisons", "Suppression de la saison " + saison.getLibelle(), "IP_LOCALE");
            saisonRepository.delete(saison);
        });
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    @Transactional
    public ResponseEntity<Void> activateSaison(@PathVariable UUID id) {
        activateSaisonInternal(id);
        saisonRepository.findById(id).ifPresent(saison -> {
            auditService.logAction(getCurrentUser(), "ACTIVATION", "Saisons", "La saison " + saison.getLibelle() + " est définie comme saison active", "IP_LOCALE");
        });
        return ResponseEntity.ok().build();
    }
    
    private void activateSaisonInternal(UUID idToActivate) {
        List<SaisonEntity> toutes = saisonRepository.findAll();
        for (SaisonEntity s : toutes) {
            s.setActive(s.getId().equals(idToActivate));
            saisonRepository.save(s);
        }
    }
}
