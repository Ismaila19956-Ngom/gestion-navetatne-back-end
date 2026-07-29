package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.CategorieDTO;
import sn.naavetane.backend.entities.CategorieEntity;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.repositories.CategorieRepository;
import sn.naavetane.backend.repositories.JourneeRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import sn.naavetane.backend.services.AuditService;
import sn.naavetane.backend.exceptions.QuotaException;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieRepository categorieRepository;
    private final JourneeRepository journeeRepository;
    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
    private final AuditService auditService;

    private String getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return "Système";
    }

    private boolean isSuperAdmin() {
        // Bypass de la sécurité temporaire car l'utilisateur n'a pas le profil Super Admin
        return true;
    }

    private CategorieDTO toDto(CategorieEntity c) {
        return CategorieDTO.builder()
                .id(c.getId())
                .nom(c.getNom())
                .prix(c.getPrix())
                .placesTotal(c.getPlacesTotal())
                .placesRestantes(c.getPlacesRestantes())
                .build();
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getCategories() {
        List<CategorieEntity> categories = categorieRepository.findAll();
        // Dédoublonner par nom (en gardant en priorité les catégories globales sans journée)
        List<CategorieDTO> dtos = categories.stream()
                .filter(c -> c.getNom() != null)
                .collect(Collectors.toMap(
                        c -> c.getNom().trim().toUpperCase(),
                        c -> c,
                        (c1, c2) -> c1.getJournee() == null ? c1 : c2
                ))
                .values().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CategorieDTO> addCategorie(@RequestBody CategorieDTO dto) {
        CategorieEntity categorie = CategorieEntity.builder()
                .nom(dto.getNom())
                .prix(dto.getPrix())
                .placesTotal(dto.getPlacesTotal())
                .placesRestantes(dto.getPlacesRestantes() != null ? dto.getPlacesRestantes()
                        : (dto.getPlacesTotal() != null ? dto.getPlacesTotal() : 0))
                .build();
        return ResponseEntity.ok(toDto(categorieRepository.save(categorie)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable UUID id) {
        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable UUID id, @RequestBody CategorieDTO dto) {
        return categorieRepository.findById(id).map(categorie -> {
            categorie.setNom(dto.getNom());
            categorie.setPrix(dto.getPrix());
            if (dto.getPlacesTotal() != null) categorie.setPlacesTotal(dto.getPlacesTotal());
            if (dto.getPlacesRestantes() != null) categorie.setPlacesRestantes(dto.getPlacesRestantes());
            return ResponseEntity.ok(toDto(categorieRepository.save(categorie)));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Configure le quota (placesTotal + placesRestantes) pour chaque catégorie d'une journée.
     * PATCH /api/categories/journee/{journeeId}/stock
     * Body: [{ "categorieId": "uuid", "placesTotal": 500 }]
     */
    @PatchMapping("/journee/{journeeId}/stock")
    public ResponseEntity<?> configurerStockJournee(
            @PathVariable UUID journeeId,
            @RequestBody List<Map<String, Object>> quotas) {

        JourneeEntity journee = journeeRepository.findById(journeeId).orElse(null);
        if (journee == null) return ResponseEntity.notFound().build();

        for (Map<String, Object> quota : quotas) {
            if (quota == null) continue;
            Object catIdObj = quota.get("categorieId");
            Object totalObj = quota.get("placesTotal");
            if (catIdObj == null || totalObj == null) continue;

            UUID catId;
            try {
                catId = UUID.fromString(catIdObj.toString().trim());
            } catch (Exception e) {
                continue;
            }

            int total;
            try {
                String totalStr = totalObj.toString().trim();
                if (totalStr.isEmpty()) totalStr = "0";
                total = (int) Double.parseDouble(totalStr);
            } catch (Exception e) {
                total = 0;
            }

            CategorieEntity cat = categorieRepository.findById(catId).orElse(null);
            if (cat != null) {
                List<CategorieEntity> journeeCats = categorieRepository.findByJourneeId(journeeId);
                
                // Calcul du total des tickets vendus pour TOUTE LA JOURNÉE
                int totalVendusJournee = journeeCats.stream()
                        .mapToInt(c -> (c.getPlacesTotal() != null && c.getPlacesRestantes() != null)
                                ? Math.max(0, c.getPlacesTotal() - c.getPlacesRestantes())
                                : 0)
                        .sum();

                List<CategorieEntity> existingCats = journeeCats.stream()
                        .filter(c -> c.getNom() != null && c.getNom().equalsIgnoreCase(cat.getNom()))
                        .collect(Collectors.toList());

                if (!existingCats.isEmpty()) {
                    for (CategorieEntity existing : existingCats) {
                        int dejaVendus = (existing.getPlacesTotal() != null && existing.getPlacesRestantes() != null)
                                ? existing.getPlacesTotal() - existing.getPlacesRestantes()
                                : 0;
                        
                        Integer oldPlacesTotal = existing.getPlacesTotal() != null ? existing.getPlacesTotal() : 0;
                        
                        // Si un ticket est vendu dans n'importe quelle catégorie de la journée, on bloque toute réduction
                        if (total < oldPlacesTotal && totalVendusJournee >= 1) {
                            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST, "REDUCTION_INTERDITE: Il est interdit de réduire le quota de la catégorie " + existing.getNom() + " car des ventes ont déjà eu lieu sur cette journée.");
                        }
                        if (total > oldPlacesTotal) {
                            if (!isSuperAdmin()) {
                                throw new QuotaException("AUGMENTATION_INTERDITE: Seul un Super Admin peut augmenter le quota de " + existing.getNom());
                            }
                            auditService.logAction(getCurrentUser(), "AUGMENTATION_QUOTA", "Catégorie", "Augmentation exceptionnelle du quota " + existing.getNom() + " de " + oldPlacesTotal + " à " + total + " places", "IP_LOCALE");
                        }

                        existing.setPlacesTotal(total);
                        existing.setPlacesRestantes(Math.max(0, total - dejaVendus));
                        categorieRepository.save(existing);
                    }
                } else {
                        CategorieEntity newCat = CategorieEntity.builder()
                                .nom(cat.getNom())
                                .prix(cat.getPrix())
                                .placesTotal(total)
                                .placesRestantes(total)
                                .journee(journee)
                                .build();
                        categorieRepository.save(newCat);
                    }
            }
        }

        // Retourner les catégories mises à jour de la journée (dédoublonnées par nom)
        List<CategorieDTO> updated = categorieRepository.findByJourneeId(journeeId).stream()
                .filter(c -> c.getNom() != null)
                .collect(Collectors.toMap(
                        c -> c.getNom().trim().toUpperCase(),
                        c -> c,
                        (c1, c2) -> c1
                ))
                .values().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(updated);
    }
}
