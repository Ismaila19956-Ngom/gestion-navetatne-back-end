package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.entities.enums.ReferentielType;
import com.webgram.dgpsn.services.CourrierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;

import java.util.Optional;

@RestController
@RequestMapping("/courriers")
@Tag(name = "Gestion des Courriers :", description = "Endpoints unifiés pour gérer tous les courriers (arrivés et départ)")
@RequiredArgsConstructor
public class CourrierController {
    private final CourrierService courrierService;

    @Operation(summary = "Créer un courrier", description = "Endpoint pour créer un nouveau courrier (arrivé ou départ)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Courrier créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourrierEntity createCourrier(@RequestBody CourrierEntity courrier) {
        return courrierService.create(courrier);
    }

    @Operation(summary = "Mettre à jour un courrier", description = "Endpoint pour modifier un courrier existant")
    @PutMapping("/{courrierId}")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity updateCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à modifier")
            @PathVariable Long courrierId,
            @RequestBody CourrierEntity courrier) {
        courrier.setId(courrierId);
        return courrierService.update(courrier);
    }

    @Operation(summary = "Supprimer un courrier", description = "Endpoint pour supprimer un courrier")
    @DeleteMapping("/{courrierId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à supprimer")
            @PathVariable Long courrierId) {
        courrierService.delete(courrierId);
    }

    @Operation(summary = "Lister les courriers", description = "Endpoint pour lister tous les courriers avec pagination et filtres")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CourrierEntity> readAllCourriers(
            Pageable pageable,
            @Parameter(name = "keyword", description = "Mot-clé pour la recherche")
            @RequestParam(value = "keyword", required = false) String keyword,

            @Parameter(name = "type", description = "Filtrer par type (ARRIVER ou DEPART)")
            @RequestParam(value = "type", required = false) CourrierType type,

            @Parameter(name = "nature", description = "Filtrer par nature (ACADEMIQUE, ADMINISTRATIF, etc.)")
            @RequestParam(value = "nature", required = false) ReferentielType nature,

            @Parameter(name = "statut", description = "Filtrer par statut")
            @RequestParam(value = "statut", required = false) ReferentielType statut,

            @Parameter(name = "archive", description = "Filtrer par statut d'archivage") // NOUVEAU PARAMÈTRE
            @RequestParam(value = "archive", required = false) Boolean archive) {

        return courrierService.readAll(pageable, keyword, type, nature, statut, archive);
    }

    @Operation(summary = "Lire un courrier", description = "Endpoint pour récupérer un courrier par son ID")
    @GetMapping("/{courrierId}")
    public ResponseEntity<?> readCourrier(@PathVariable Long courrierId) {
        try {
            Optional<CourrierEntity> courrier = courrierService.read(courrierId);
            if (courrier.isPresent()) {
                return ResponseEntity.ok(courrier.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Courrier non trouvé avec ID: " + courrierId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    @Operation(summary = "Archiver un courrier", description = "Endpoint pour archiver un courrier")
    @PutMapping("/{courrierId}/archiver")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity archiverCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à archiver")
            @PathVariable Long courrierId) {
        return courrierService.archiver(courrierId);
    }

    @Operation(summary = "Désarchiver un courrier", description = "Endpoint pour désarchiver un courrier") // NOUVEL ENDPOINT
    @PutMapping("/{courrierId}/desarchiver")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity desarchiverCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à désarchiver")
            @PathVariable Long courrierId) {
        return courrierService.desarchiver(courrierId);
    }

    @Operation(summary = "Changer le statut d'un courrier", description = "Endpoint pour modifier le statut d'un courrier")
    @PutMapping("/{courrierId}/statut")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity changerStatutCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier")
            @PathVariable Long courrierId,

            @Parameter(name = "nouveauStatut", description = "Nouveau statut à assigner")
            @RequestParam ReferentielType nouveauStatut) {
        return courrierService.changerStatut(courrierId, nouveauStatut);
    }

    @Operation(summary = "Compter les courriers par type, nature et statut", description = "Endpoint pour compter les courriers")
    @GetMapping("/statistiques/comptage")
    @ResponseStatus(HttpStatus.OK)
    public long countCourriers(
            @Parameter(name = "type", description = "Type des courriers")
            @RequestParam CourrierType type,

            @Parameter(name = "nature", description = "Nature des courriers")
            @RequestParam ReferentielType nature,

            @Parameter(name = "statut", description = "Statut des courriers")
            @RequestParam ReferentielType statut) {
        return courrierService.countByTypeAndNatureAndStatut(type, nature, statut);
    }

    @Operation(summary = "Compter les courriers actifs", description = "Endpoint pour compter les courriers non archivés par type")
    @GetMapping("/statistiques/actifs")
    @ResponseStatus(HttpStatus.OK)
    public long countCourriersActifs(
            @Parameter(name = "type", description = "Type des courriers")
            @RequestParam CourrierType type) {
        return courrierService.countCourriersActifs(type);
    }

    @Operation(summary = "Lister les courriers par type", description = "Endpoint pour lister les courriers arrivés ou départ")
    @GetMapping("/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourrierEntity> getCourriersByType(
            @Parameter(name = "type", description = "Type des courriers")
            @PathVariable CourrierType type,
            Pageable pageable) {
        return courrierService.findByType(type, pageable);
    }

    @Operation(summary = "Lister les courriers par nature", description = "Endpoint pour lister les courriers par nature")
    @GetMapping("/nature/{nature}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourrierEntity> getCourriersByNature(
            @Parameter(name = "nature", description = "Nature des courriers")
            @PathVariable ReferentielType nature,
            Pageable pageable) {
        return courrierService.findByNature(nature, pageable);
    }

    // NOUVEAUX ENDPOINTS POUR LA GESTION DES ARCHIVES
    @Operation(summary = "Lister les courriers archivés", description = "Endpoint pour lister tous les courriers archivés")
    @GetMapping("/archives")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourrierEntity> getCourriersArchives(Pageable pageable) {
        return courrierService.findArchives(pageable);
    }

    @Operation(summary = "Lister les courriers non archivés", description = "Endpoint pour lister tous les courriers non archivés")
    @GetMapping("/non-archives")
    @ResponseStatus(HttpStatus.OK)
    public Page<CourrierEntity> getCourriersNonArchives(Pageable pageable) {
        return courrierService.findNonArchives(pageable);
    }

    @Operation(summary = "Compter les courriers archivés", description = "Endpoint pour compter le nombre total de courriers archivés")
    @GetMapping("/statistiques/archives/count")
    @ResponseStatus(HttpStatus.OK)
    public long countArchives() {
        return courrierService.countArchives();
    }

    @Operation(summary = "Compter les courriers non archivés", description = "Endpoint pour compter le nombre total de courriers non archivés")
    @GetMapping("/statistiques/non-archives/count")
    @ResponseStatus(HttpStatus.OK)
    public long countNonArchives() {
        return courrierService.countNonArchives();
    }
}