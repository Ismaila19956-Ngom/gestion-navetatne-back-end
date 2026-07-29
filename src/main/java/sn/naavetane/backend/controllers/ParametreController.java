package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.ParametreDTO;
import sn.naavetane.backend.entities.ParametreEntity;
import sn.naavetane.backend.repositories.ParametreRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import sn.naavetane.backend.services.AuditService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/parametres")
@RequiredArgsConstructor
public class ParametreController {

    private final ParametreRepository parametreRepository;
    private final AuditService auditService;

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Système";
    }

    private ParametreDTO toDto(ParametreEntity p) {
        return ParametreDTO.builder()
                .id(p.getId())
                .cle(p.getCle())
                .valeur(p.getValeur())
                .description(p.getDescription())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ParametreDTO>> getAllParametres() {
        return ResponseEntity.ok(parametreRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{cle}")
    public ResponseEntity<ParametreDTO> getParametreByCle(@PathVariable String cle) {
        return parametreRepository.findByCle(cle)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ParametreDTO> saveParametre(@RequestBody ParametreDTO dto) {
        Optional<ParametreEntity> existing = parametreRepository.findByCle(dto.getCle());
        ParametreEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setValeur(dto.getValeur());
            if (dto.getDescription() != null) {
                entity.setDescription(dto.getDescription());
            }
            auditService.logAction(getCurrentUser(), "MODIFICATION", "Paramètres", "Modification du paramètre " + dto.getCle() + " : " + dto.getValeur(), "IP_LOCALE");
        } else {
            entity = ParametreEntity.builder()
                    .cle(dto.getCle())
                    .valeur(dto.getValeur())
                    .description(dto.getDescription())
                    .build();
            auditService.logAction(getCurrentUser(), "CREATION", "Paramètres", "Création du paramètre " + dto.getCle() + " : " + dto.getValeur(), "IP_LOCALE");
        }
        return ResponseEntity.ok(toDto(parametreRepository.save(entity)));
    }
}
