package com.webgram.dgpsn.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.NatureCourrier;
import com.webgram.dgpsn.entities.enums.StatutCourrier;
import com.webgram.dgpsn.services.CourrierService;

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
            @RequestParam(value = "nature", required = false) NatureCourrier nature,

            @Parameter(name = "statut", description = "Filtrer par statut")
            @RequestParam(value = "statut", required = false) StatutCourrier statut) {

        return courrierService.readAll(pageable, keyword, type, nature, statut);
    }

    @Operation(summary = "Lire un courrier", description = "Endpoint pour récupérer un courrier par son ID")
    @GetMapping("/{courrierId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CourrierEntity> readCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à récupérer")
            @PathVariable Long courrierId) {
        return courrierService.read(courrierId);
    }

    @Operation(summary = "Archiver un courrier", description = "Endpoint pour archiver un courrier")
    @PutMapping("/{courrierId}/archiver")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity archiverCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier à archiver")
            @PathVariable Long courrierId) {
        return courrierService.archiver(courrierId);
    }

    @Operation(summary = "Changer le statut d'un courrier", description = "Endpoint pour modifier le statut d'un courrier")
    @PutMapping("/{courrierId}/statut")
    @ResponseStatus(HttpStatus.OK)
    public CourrierEntity changerStatutCourrier(
            @Parameter(name = "courrierId", description = "ID du courrier")
            @PathVariable Long courrierId,

            @Parameter(name = "nouveauStatut", description = "Nouveau statut à assigner")
            @RequestParam StatutCourrier nouveauStatut) {
        return courrierService.changerStatut(courrierId, nouveauStatut);
    }

    @Operation(summary = "Compter les courriers par type, nature et statut", description = "Endpoint pour compter les courriers")
    @GetMapping("/statistiques/comptage")
    @ResponseStatus(HttpStatus.OK)
    public long countCourriers(
            @Parameter(name = "type", description = "Type des courriers")
            @RequestParam CourrierType type,

            @Parameter(name = "nature", description = "Nature des courriers")
            @RequestParam NatureCourrier nature,

            @Parameter(name = "statut", description = "Statut des courriers")
            @RequestParam StatutCourrier statut) {
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
            @PathVariable NatureCourrier nature,
            Pageable pageable) {
        return courrierService.findByNature(nature, pageable);
    }
}