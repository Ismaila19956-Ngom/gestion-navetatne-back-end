package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QFicheVisiteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FicheVisiteMapper;
import com.webgram.dgpsn.models.FicheVisiteDTO;
import com.webgram.dgpsn.repositories.FicheVisiteRepository;
import com.webgram.dgpsn.services.FicheVisiteService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FicheVisiteServiceImpl implements FicheVisiteService {
    private final FicheVisiteRepository ficheVisiteRepository;
    private final FicheVisiteMapper ficheVisiteMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public FicheVisiteDTO create(FicheVisiteDTO ficheVisiteDTO) {
        var savedFicheVisite = ficheVisiteRepository.save(ficheVisiteMapper.asEntity(ficheVisiteDTO));
        log.info("Fiche visite successfully added {}", savedFicheVisite);
        return ficheVisiteMapper.asDto(savedFicheVisite);
    }

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public List<FicheVisiteDTO> createMultiple(List<FicheVisiteDTO> ficheVisiteDTOs) {
        var entities = ficheVisiteDTOs.stream()
                .map(ficheVisiteMapper::asEntity)
                .collect(Collectors.toList());
        var savedEntities = ficheVisiteRepository.saveAll(entities);
        log.info("Multiple fiche visites successfully added");
        return savedEntities.stream()
                .map(ficheVisiteMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public FicheVisiteDTO update(FicheVisiteDTO ficheVisiteDTO) {
        var ficheVisiteSaved = ficheVisiteMapper.asEntity(ficheVisiteDTO);
        var updatedFicheVisite = ficheVisiteMapper.asDto(ficheVisiteRepository.save(ficheVisiteSaved));
        log.info("Fiche visite successfully updated {}", updatedFicheVisite.getId());
        return updatedFicheVisite;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public FicheVisiteDTO read(Long ficheVisiteId) {
        var ficheVisite = ficheVisiteRepository
                .findById(ficheVisiteId)
                .orElseThrow(() -> new ResourceNotFoundException("FicheVisite", ficheVisiteId));
        log.info("Reading fiche visite id {}", ficheVisiteId);
        return ficheVisiteMapper.asDto(ficheVisite);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long ficheVisiteId) {
        try {
            ficheVisiteRepository.deleteById(ficheVisiteId);
            log.info("The fiche visite id {} is deleted", ficheVisiteId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<FicheVisiteDTO> readAllFicheVisites(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return ficheVisiteRepository.findAll(booleanBuilder, pageable)
                .map(ficheVisiteMapper::asDto);
    }

    //    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
//        if (Objects.nonNull(searchParams)) {
//            var qEntity = QFicheVisiteEntity.ficheVisiteEntity;
//            if (searchParams.containsKey("structure"))
//                booleanBuilder.and(qEntity.structure.eq(searchParams.get("structure")));
//            if (searchParams.containsKey("titre"))
//                booleanBuilder.and(qEntity.titre.eq(searchParams.get("titre")));
//            if (searchParams.containsKey("nomPromoteur"))
//                booleanBuilder.and(qEntity.nomPromoteur.eq(searchParams.get("nomPromoteur")));
//            if (searchParams.containsKey("regionId"))
//                booleanBuilder.and(qEntity.region.id.eq(Long.valueOf(searchParams.get("regionId"))));
//            if (searchParams.containsKey("annee"))
//                booleanBuilder.and(qEntity.annee.eq(Integer.valueOf(searchParams.get("annee"))));
//        }
//    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (searchParams == null || searchParams.isEmpty()) {
            log.debug("No search parameters provided; returning all results.");
            return;
        }

        var qEntity = QFicheVisiteEntity.ficheVisiteEntity;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Structure et visite
        if (searchParams.containsKey("structure")) {
            booleanBuilder.and(qEntity.structure.containsIgnoreCase(searchParams.get("structure")));
        }
        if (searchParams.containsKey("dateVisite")) {
            try {
                LocalDate dateVisite = LocalDate.parse(searchParams.get("dateVisite"), formatter);
                booleanBuilder.and(qEntity.dateVisite.eq((dateVisite)));
            } catch (Exception e) {
                log.warn("Invalid date format for dateVisite: {}", searchParams.get("dateVisite"));
            }
        }

        // Informations du projet
        if (searchParams.containsKey("titre")) {
            booleanBuilder.and(qEntity.titre.containsIgnoreCase(searchParams.get("titre")));
        }
        if (searchParams.containsKey("typeProjet")) {
            booleanBuilder.and(qEntity.typeProjet.containsIgnoreCase(searchParams.get("typeProjet")));
        }
        if (searchParams.containsKey("nomPromoteur")) {
            booleanBuilder.and(qEntity.nomPromoteur.containsIgnoreCase(searchParams.get("nomPromoteur")));
        }
        if (searchParams.containsKey("responsables")) {
            booleanBuilder.and(qEntity.responsables.containsIgnoreCase(searchParams.get("responsables")));
        }
        if (searchParams.containsKey("village")) {
            booleanBuilder.and(qEntity.village.containsIgnoreCase(searchParams.get("village")));
        }
        if (searchParams.containsKey("annee")) {
            try {
                Integer annee = Integer.valueOf(searchParams.get("annee"));
                booleanBuilder.and(qEntity.annee.eq(annee));
            } catch (NumberFormatException e) {
                log.warn("Invalid annee value: {}", searchParams.get("annee"));
            }
        }
        if (searchParams.containsKey("coordX")) {
            booleanBuilder.and(qEntity.coordX.containsIgnoreCase(searchParams.get("coordX")));
        }
        if (searchParams.containsKey("coordY")) {
            booleanBuilder.and(qEntity.coordY.containsIgnoreCase(searchParams.get("coordY")));
        }

        // Présentation du site
        if (searchParams.containsKey("limiteEst")) {
            booleanBuilder.and(qEntity.limiteEst.containsIgnoreCase(searchParams.get("limiteEst")));
        }
        if (searchParams.containsKey("limiteOuest")) {
            booleanBuilder.and(qEntity.limiteOuest.containsIgnoreCase(searchParams.get("limiteOuest")));
        }
        if (searchParams.containsKey("limiteNord")) {
            booleanBuilder.and(qEntity.limiteNord.containsIgnoreCase(searchParams.get("limiteNord")));
        }
        if (searchParams.containsKey("limiteSud")) {
            booleanBuilder.and(qEntity.limiteSud.containsIgnoreCase(searchParams.get("limiteSud")));
        }
        if (searchParams.containsKey("statutJuridique")) {
            booleanBuilder.and(qEntity.statutJuridique.containsIgnoreCase(searchParams.get("statutJuridique")));
        }
        if (searchParams.containsKey("superficie")) {
            booleanBuilder.and(qEntity.superficie.containsIgnoreCase(searchParams.get("superficie")));
        }
        if (searchParams.containsKey("utilisationAnterieure")) {
            booleanBuilder.and(qEntity.utilisationAnterieure.containsIgnoreCase(searchParams.get("utilisationAnterieure")));
        }

        // Description du projet
        if (searchParams.containsKey("descriptionProjet")) {
            booleanBuilder.and(qEntity.descriptionProjet.containsIgnoreCase(searchParams.get("descriptionProjet")));
        }
        if (searchParams.containsKey("phasePreConstruction")) {
            booleanBuilder.and(qEntity.phasePreConstruction.containsIgnoreCase(searchParams.get("phasePreConstruction")));
        }
        if (searchParams.containsKey("phaseConstruction")) {
            booleanBuilder.and(qEntity.phaseConstruction.containsIgnoreCase(searchParams.get("phaseConstruction")));
        }
        if (searchParams.containsKey("phaseExploitation")) {
            booleanBuilder.and(qEntity.phaseExploitation.containsIgnoreCase(searchParams.get("phaseExploitation")));
        }

        // Sensibilités et recommandations
        if (searchParams.containsKey("sensibiliteEnvironnementale")) {
            booleanBuilder.and(qEntity.sensibiliteEnvironnementale.containsIgnoreCase(searchParams.get("sensibiliteEnvironnementale")));
        }
        if (searchParams.containsKey("sensibiliteSociale")) {
            booleanBuilder.and(qEntity.sensibiliteSociale.containsIgnoreCase(searchParams.get("sensibiliteSociale")));
        }
        if (searchParams.containsKey("particularites")) {
            booleanBuilder.and(qEntity.particularites.containsIgnoreCase(searchParams.get("particularites")));
        }
        if (searchParams.containsKey("pointsAccent")) {
            booleanBuilder.and(qEntity.pointsAccent.containsIgnoreCase(searchParams.get("pointsAccent")));
        }
        if (searchParams.containsKey("recommandations")) {
            booleanBuilder.and(qEntity.recommandations.containsIgnoreCase(searchParams.get("recommandations")));
        }

        // Chef de division
        if (searchParams.containsKey("chefDivision")) {
            booleanBuilder.and(qEntity.chefDivision.containsIgnoreCase(searchParams.get("chefDivision")));
        }

        // Localisation géographique (IDs)
        if (searchParams.containsKey("regionId")) {
            try {
                Long regionId = Long.valueOf(searchParams.get("regionId"));
                booleanBuilder.and(qEntity.region.id.eq(regionId));
            } catch (NumberFormatException e) {
                log.warn("Invalid regionId value: {}", searchParams.get("regionId"));
            }
        }
        if (searchParams.containsKey("departementId")) {
            try {
                Long departementId = Long.valueOf(searchParams.get("departementId"));
                booleanBuilder.and(qEntity.departement.id.eq(departementId));
            } catch (NumberFormatException e) {
                log.warn("Invalid departementId value: {}", searchParams.get("departementId"));
            }
        }
        if (searchParams.containsKey("arrondissementId")) {
            try {
                Long arrondissementId = Long.valueOf(searchParams.get("arrondissementId"));
                booleanBuilder.and(qEntity.arrondissement.id.eq(arrondissementId));
            } catch (NumberFormatException e) {
                log.warn("Invalid arrondissementId value: {}", searchParams.get("arrondissementId"));
            }
        }
        if (searchParams.containsKey("communeId")) {
            try {
                Long communeId = Long.valueOf(searchParams.get("communeId"));
                booleanBuilder.and(qEntity.commune.id.eq(communeId));
            } catch (NumberFormatException e) {
                log.warn("Invalid communeId value: {}", searchParams.get("communeId"));
            }
        }
    }
}