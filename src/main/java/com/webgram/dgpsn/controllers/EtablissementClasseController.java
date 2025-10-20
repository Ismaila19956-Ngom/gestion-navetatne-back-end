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
import com.webgram.dgpsn.models.EtablissementClasseDTO;
import com.webgram.dgpsn.services.EtablissementClasseService;

@RestController
@RequestMapping("/etablissements-classes")
@Tag(name = "établissements classés", description = "Établissements classés controller")
@RequiredArgsConstructor
public class EtablissementClasseController {

    private final EtablissementClasseService etablissementClasseService;

    @Operation(summary = "Créer un établissement classé", description = "Ce endpoint prend un établissement classé en entrée et l’enregistre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EtablissementClasseDTO addEtablissementClasse(@RequestBody EtablissementClasseDTO etablissementClasseDTO) {
        return etablissementClasseService.create(etablissementClasseDTO);
    }

    @Operation(summary = "Mettre à jour un établissement classé", description = "Ce endpoint met à jour un établissement classé existant avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @PutMapping("/{etablissementClasseId}")
    @ResponseStatus(HttpStatus.OK)
    public EtablissementClasseDTO updateEtablissementClasse(
            @Parameter(name = "etablissementClasseId", description = "L’ID de l’établissement classé à mettre à jour")
            @PathVariable Long etablissementClasseId,
            @RequestBody EtablissementClasseDTO etablissementClasseDTO) {
        etablissementClasseDTO.setId(etablissementClasseId);
        return etablissementClasseService.update(etablissementClasseDTO);
    }

    @Operation(summary = "Supprimer un établissement classé", description = "Ce endpoint supprime un établissement classé avec l’ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Request sent by the client was syntactically incorrect"),
            @ApiResponse(responseCode = "404", description = "Resource access does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @DeleteMapping("/{etablissementClasseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtablissementClasse(
            @Parameter(name = "etablissementClasseId", description = "L’ID de l’établissement classé à supprimer")
            @PathVariable Long etablissementClasseId) {
        etablissementClasseService.delete(etablissementClasseId);
    }

    @Operation(summary = "Lire tous les établissements classés", description = "Ce endpoint retourne une liste paginée d’établissements classés avec des filtres optionnels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<EtablissementClasseDTO> readAllEtablissementClasse(
            Pageable pageable,
            @Parameter(name = "code", description = "Valeur du code pour filtrer les établissements classés")
            @RequestParam(value = "code", required = false) String code,
            @Parameter(name = "libelle", description = "Valeur du libellé pour filtrer les établissements classés")
            @RequestParam(value = "libelle", required = false) String libelle,
            @Parameter(name = "latitude", description = "Valeur de la latitude pour filtrer les établissements classés")
            @RequestParam(value = "latitude", required = false) String latitude,
            @Parameter(name = "longitude", description = "Valeur de la longitude pour filtrer les établissements classés")
            @RequestParam(value = "longitude", required = false) String longitude,
            @Parameter(name = "contactPerson", description = "Nom de la personne de contact pour filtrer les établissements classés")
            @RequestParam(value = "contactPerson", required = false) String contactPerson,
            @Parameter(name = "contactRole", description = "Rôle de la personne de contact pour filtrer les établissements classés")
            @RequestParam(value = "contactRole", required = false) String contactRole,
            @Parameter(name = "contactInfo", description = "Informations de contact pour filtrer les établissements classés")
            @RequestParam(value = "contactInfo", required = false) String contactInfo,
            @Parameter(name = "mainActivity", description = "Activité principale pour filtrer les établissements classés")
            @RequestParam(value = "mainActivity", required = false) String mainActivity,
            @Parameter(name = "typeEtablissementId", description = "ID du type d’établissement pour filtrer les établissements classés")
            @RequestParam(value = "typeEtablissementId", required = false) Long typeEtablissementId,
            @Parameter(name = "regionId", description = "ID de la région pour filtrer les établissements classés")
            @RequestParam(value = "regionId", required = false) Long regionId,
            @Parameter(name = "departementId", description = "ID du département pour filtrer les établissements classés")
            @RequestParam(value = "departementId", required = false) Long departementId,
            @Parameter(name = "categoryICPEId", description = "ID de la catégorie ICPE pour filtrer les établissements classés")
            @RequestParam(value = "categoryICPEId", required = false) Long categoryICPEId,
            @Parameter(name = "sortBy", description = "Champ utilisé pour trier les résultats")
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @Parameter(name = "ascending", description = "Ordre de tri (true pour ascendant, false pour descendant)")
            @RequestParam(value = "ascending", required = false) Boolean ascending
    ) {
        return etablissementClasseService.readAll(pageable, code, libelle, latitude, longitude, contactPerson, contactRole, contactInfo, mainActivity, typeEtablissementId, regionId, departementId, categoryICPEId, sortBy, ascending);
    }
}