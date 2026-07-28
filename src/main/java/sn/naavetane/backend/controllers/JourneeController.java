package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.JourneeDTO;
import sn.naavetane.backend.entities.CategorieEntity;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.entities.MatchEntity;
import sn.naavetane.backend.repositories.JourneeRepository;
import sn.naavetane.backend.services.AuditService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import sn.naavetane.backend.entities.TicketEntity;
import sn.naavetane.backend.repositories.TicketRepository;

@RestController
@RequestMapping("/api/journees")
@RequiredArgsConstructor
public class JourneeController {

    private final JourneeRepository journeeRepository;
    private final TicketRepository ticketRepository;
    private final AuditService auditService;

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Système";
    }

    @PostMapping
    public ResponseEntity<JourneeDTO> createJournee(@RequestBody JourneeDTO dto) {
        JourneeEntity journee = JourneeEntity.builder()
                .date(dto.getDate())
                .stade(dto.getStade())
                .statut(dto.getStatut() != null ? dto.getStatut() : "PROGRAMMEE")
                .build();

        // Ajout des matchs
        if (dto.getMatchs() != null) {
            journee.setMatchs(dto.getMatchs().stream().map(m -> {
                MatchEntity match = MatchEntity.builder()
                        .equipe1(m.getEquipe1())
                        .equipe2(m.getEquipe2())
                        .heure(m.getHeure())
                        .build();
                match.setJournee(journee);
                return match;
            }).collect(Collectors.toList()));
        }

        // Ajout des catégories
        if (dto.getCategories() != null) {
            journee.setCategories(dto.getCategories().stream().map(c -> {
                CategorieEntity cat = CategorieEntity.builder()
                        .nom(c.getNom())
                        .prix(c.getPrix())
                        .placesRestantes(c.getPlacesRestantes())
                        .build();
                cat.setJournee(journee);
                return cat;
            }).collect(Collectors.toList()));
        }

        JourneeEntity saved = journeeRepository.save(journee);
        auditService.logAction(getCurrentUser(), "PROGRAMMATION", "Journées", "Programmation d'une nouvelle journée au stade: " + dto.getStade() + " pour le " + dto.getDate(), "IP_LOCALE");
        return ResponseEntity.ok(mapToDto(saved));
    }

    @GetMapping("/a-venir")
    public ResponseEntity<List<JourneeDTO>> getJourneesAVenir() {
        // Masquer automatiquement les matchs passés (dont la date est strictement avant aujourd'hui)
        List<JourneeEntity> journees = journeeRepository.findByDateGreaterThanEqualOrderByDateAsc(java.time.LocalDate.now());
        List<JourneeDTO> dtos = journees.stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/du-jour")
    public ResponseEntity<List<JourneeDTO>> getJourneesDuJour() {
        // Renvoie uniquement les journées dont la date correspond à la date système actuelle
        List<JourneeEntity> journees = journeeRepository.findByDate(java.time.LocalDate.now());
        
        // Trier pour que la journée la plus récemment créée apparaisse en premier (au cas où il y a des doublons)
        List<JourneeDTO> dtos = journees.stream()
                .sorted((j1, j2) -> {
                    if (j1.getCreatedDate() != null && j2.getCreatedDate() != null) {
                        return j2.getCreatedDate().compareTo(j1.getCreatedDate());
                    }
                    return 0;
                })
                .map(this::mapToDto)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(dtos);
    }

    private JourneeDTO mapToDto(JourneeEntity entity) {
        return JourneeDTO.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .stade(entity.getStade())
                .statut(entity.getStatut())
                .matchs(entity.getMatchs() != null ? entity.getMatchs().stream().map(m -> JourneeDTO.MatchDTO.builder()
                        .id(m.getId())
                        .equipe1(m.getEquipe1())
                        .equipe2(m.getEquipe2())
                        .heure(m.getHeure())
                        .build()).collect(Collectors.toList()) : java.util.Collections.emptyList())
                .categories(entity.getCategories() != null ? entity.getCategories().stream()
                        .filter(distinctByKey(c -> c.getNom().trim().toUpperCase() + "_" + c.getPrix()))
                        .map(c -> JourneeDTO.CategorieDTO.builder()
                        .id(c.getId())
                        .nom(c.getNom())
                        .prix(c.getPrix())
                        .placesTotal(c.getPlacesTotal())
                        .placesRestantes(c.getPlacesRestantes())
                        .build()).collect(Collectors.toList()) : java.util.Collections.emptyList())
                .build();
    }

    public static <T> java.util.function.Predicate<T> distinctByKey(java.util.function.Function<? super T, ?> keyExtractor) {
        java.util.Set<Object> seen = java.util.concurrent.ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteJournee(@PathVariable java.util.UUID id) {
        // Supprimer d'abord les tickets liés pour éviter les erreurs de clés étrangères (utile pour les tests)
        List<TicketEntity> tickets = ticketRepository.findByMatchId(id);
        if (!tickets.isEmpty()) {
            ticketRepository.deleteAll(tickets);
        }
        
        journeeRepository.findById(id).ifPresent(j -> 
            auditService.logAction(getCurrentUser(), "SUPPRESSION", "Journées", "Suppression de la journée du " + j.getDate() + " au stade " + j.getStade(), "IP_LOCALE")
        );
        
        journeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<JourneeDTO> updateJournee(@PathVariable java.util.UUID id, @RequestBody JourneeDTO dto) {
        return journeeRepository.findById(id).map(journee -> {
            journee.setDate(dto.getDate());
            journee.setStade(dto.getStade());
            if (dto.getStatut() != null) {
                journee.setStatut(dto.getStatut());
            }

            // Update matchs
            if (dto.getMatchs() != null) {
                journee.getMatchs().clear();
                journee.getMatchs().addAll(dto.getMatchs().stream().map(m -> {
                    MatchEntity match = MatchEntity.builder()
                            .equipe1(m.getEquipe1())
                            .equipe2(m.getEquipe2())
                            .heure(m.getHeure())
                            .build();
                    match.setJournee(journee);
                    return match;
                }).collect(Collectors.toList()));
            }

            // Update categories
            if (dto.getCategories() != null) {
                journee.getCategories().clear();
                journee.getCategories().addAll(dto.getCategories().stream().map(c -> {
                    CategorieEntity cat = CategorieEntity.builder()
                            .nom(c.getNom())
                            .prix(c.getPrix())
                            .placesRestantes(c.getPlacesRestantes())
                            .build();
                    cat.setJournee(journee);
                    return cat;
                }).collect(Collectors.toList()));
            }

            JourneeEntity updated = journeeRepository.save(journee);
            auditService.logAction(getCurrentUser(), "MODIFICATION", "Journées", "Modification de la journée au stade: " + dto.getStade() + " pour le " + dto.getDate(), "IP_LOCALE");
            return ResponseEntity.ok(mapToDto(updated));
        }).orElse(ResponseEntity.notFound().build());
    }
}
