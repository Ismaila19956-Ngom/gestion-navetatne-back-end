package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QFicheRenseignementEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.FicheRenseignementMapper;
import com.webgram.dgpsn.models.FicheRenseignementDTO;
import com.webgram.dgpsn.repositories.FicheRenseignementRepository;
import com.webgram.dgpsn.services.FicheRenseignementService;
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
public class FicheRenseignementServiceImpl implements FicheRenseignementService {
    private final FicheRenseignementRepository ficheRenseignementRepository;
    private final FicheRenseignementMapper ficheRenseignementMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public FicheRenseignementDTO create(FicheRenseignementDTO ficheRenseignementDTO) {
        var savedFicheRenseignement = ficheRenseignementRepository.save(ficheRenseignementMapper.asEntity(ficheRenseignementDTO));
        log.info("Fiche renseignement successfully added {}", savedFicheRenseignement);
        return ficheRenseignementMapper.asDto(savedFicheRenseignement);
    }

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public List<FicheRenseignementDTO> createMultiple(List<FicheRenseignementDTO> ficheRenseignementDTOs) {
        var entities = ficheRenseignementDTOs.stream()
                .map(ficheRenseignementMapper::asEntity)
                .collect(Collectors.toList());
        var savedEntities = ficheRenseignementRepository.saveAll(entities);
        log.info("Multiple fiche renseignement successfully added");
        return savedEntities.stream()
                .map(ficheRenseignementMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public FicheRenseignementDTO update(FicheRenseignementDTO ficheRenseignementDTO) {
        var ficheRenseignementSaved = ficheRenseignementMapper.asEntity(ficheRenseignementDTO);
        var updatedFicheRenseignement = ficheRenseignementMapper.asDto(ficheRenseignementRepository.save(ficheRenseignementSaved));
        log.info("Fiche renseignement successfully updated {}", updatedFicheRenseignement.getId());
        return updatedFicheRenseignement;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public FicheRenseignementDTO read(Long ficheRenseignementId) {
        var ficheRenseignement = ficheRenseignementRepository
                .findById(ficheRenseignementId)
                .orElseThrow(() -> new ResourceNotFoundException("FicheRenseignement", ficheRenseignementId));
        log.info("Reading fiche renseignement id {}", ficheRenseignementId);
        return ficheRenseignementMapper.asDto(ficheRenseignement);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long ficheRenseignementId) {
        try {
            ficheRenseignementRepository.deleteById(ficheRenseignementId);
            log.info("The fiche renseignement id {} is deleted", ficheRenseignementId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<FicheRenseignementDTO> readAllFicheRenseignements(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return ficheRenseignementRepository.findAll(booleanBuilder, pageable)
                .map(ficheRenseignementMapper::asDto);
    }

private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
    if (searchParams == null || searchParams.isEmpty()) {
        log.debug("No search parameters provided; returning all results.");
        return;
    }

    var qEntity = QFicheRenseignementEntity.ficheRenseignementEntity;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Information sur la compagnie
    if (searchParams.containsKey("nomCompagnie")) {
        booleanBuilder.and(qEntity.nomCompagnie.containsIgnoreCase(searchParams.get("nomCompagnie")));
    }

    // Information sur la mission
    if (searchParams.containsKey("structureInstruction")) {
        booleanBuilder.and(qEntity.structureInstruction.containsIgnoreCase(searchParams.get("structureInstruction")));
    }
    if (searchParams.containsKey("dateMission")) {
        try {
            LocalDate dateMission = LocalDate.parse(searchParams.get("dateMission"), formatter);
            booleanBuilder.and(qEntity.dateMission.eq(java.sql.Date.valueOf(dateMission)));
        } catch (Exception e) {
            log.warn("Invalid date format for dateMission: {}", searchParams.get("dateMission"));
        }
    }
    if (searchParams.containsKey("membresMission")) {
        booleanBuilder.and(qEntity.membresMission.containsIgnoreCase(searchParams.get("membresMission")));
    }
    if (searchParams.containsKey("objetMission")) {
        booleanBuilder.and(qEntity.objetMission.containsIgnoreCase(searchParams.get("objetMission")));
    }

    // Identification du promoteur
    if (searchParams.containsKey("raisonSociale")) {
        booleanBuilder.and(qEntity.raisonSociale.containsIgnoreCase(searchParams.get("raisonSociale")));
    }
    if (searchParams.containsKey("numeroRCCM")) {
        try {
            Integer numeroRCCM = Integer.valueOf(searchParams.get("numeroRCCM"));
            booleanBuilder.and(qEntity.numeroRCCM.eq(numeroRCCM));
        } catch (NumberFormatException e) {
            log.warn("Invalid numeroRCCM value: {}", searchParams.get("numeroRCCM"));
        }
    }
    if (searchParams.containsKey("numeroNINEA")) {
        try {
            Integer numeroNINEA = Integer.valueOf(searchParams.get("numeroNINEA"));
            booleanBuilder.and(qEntity.numeroNINEA.eq(numeroNINEA));
        } catch (NumberFormatException e) {
            log.warn("Invalid numeroNINEA value: {}", searchParams.get("numeroNINEA"));
        }
    }
    if (searchParams.containsKey("nomExploitant")) {
        booleanBuilder.and(qEntity.nomExploitant.containsIgnoreCase(searchParams.get("nomExploitant")));
    }
    if (searchParams.containsKey("telephoneExploitant")) {
        booleanBuilder.and(qEntity.telephoneExploitant.containsIgnoreCase(searchParams.get("telephoneExploitant")));
    }
    if (searchParams.containsKey("adresseExploitant")) {
        booleanBuilder.and(qEntity.adresseExploitant.containsIgnoreCase(searchParams.get("adresseExploitant")));
    }
    if (searchParams.containsKey("natureActivite")) {
        booleanBuilder.and(qEntity.natureActivite.containsIgnoreCase(searchParams.get("natureActivite")));
    }

    // Localisation géographique
    if (searchParams.containsKey("quartier")) {
        booleanBuilder.and(qEntity.quartier.containsIgnoreCase(searchParams.get("quartier")));
    }
    if (searchParams.containsKey("limiteEst")) {
        booleanBuilder.and(qEntity.limiteEst.containsIgnoreCase(searchParams.get("limiteEst")));
    }
    if (searchParams.containsKey("limiteNord")) {
        booleanBuilder.and(qEntity.limiteNord.containsIgnoreCase(searchParams.get("limiteNord")));
    }
    if (searchParams.containsKey("limiteOuest")) {
        booleanBuilder.and(qEntity.limiteOuest.containsIgnoreCase(searchParams.get("limiteOuest")));
    }
    if (searchParams.containsKey("limiteSud")) {
        booleanBuilder.and(qEntity.limiteSud.containsIgnoreCase(searchParams.get("limiteSud")));
    }
    if (searchParams.containsKey("localisationGPS")) {
        booleanBuilder.and(qEntity.localisationGPS.containsIgnoreCase(searchParams.get("localisationGPS")));
    }
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
    if (searchParams.containsKey("communeId")) {
        try {
            Long communeId = Long.valueOf(searchParams.get("communeId"));
            booleanBuilder.and(qEntity.commune.id.eq(communeId));
        } catch (NumberFormatException e) {
            log.warn("Invalid communeId value: {}", searchParams.get("communeId"));
        }
    }

    // Superficie
    if (searchParams.containsKey("surfaceEquipee")) {
        try {
            Double surfaceEquipee = Double.valueOf(searchParams.get("surfaceEquipee"));
            booleanBuilder.and(qEntity.surfaceEquipee.eq(surfaceEquipee));
        } catch (NumberFormatException e) {
            log.warn("Invalid surfaceEquipee value: {}", searchParams.get("surfaceEquipee"));
        }
    }
    if (searchParams.containsKey("surfaceNonEquipee")) {
        try {
            Double surfaceNonEquipee = Double.valueOf(searchParams.get("surfaceNonEquipee"));
            booleanBuilder.and(qEntity.surfaceNonEquipee.eq(surfaceNonEquipee));
        } catch (NumberFormatException e) {
            log.warn("Invalid surfaceNonEquipee value: {}", searchParams.get("surfaceNonEquipee"));
        }
    }
    if (searchParams.containsKey("surfaceTotale")) {
        try {
            Double surfaceTotale = Double.valueOf(searchParams.get("surfaceTotale"));
            booleanBuilder.and(qEntity.surfaceTotale.eq(surfaceTotale));
        } catch (NumberFormatException e) {
            log.warn("Invalid surfaceTotale value: {}", searchParams.get("surfaceTotale"));
        }
    }

    // Constats généraux
    if (searchParams.containsKey("compositionEtablissement")) {
        booleanBuilder.and(qEntity.compositionEtablissement.containsIgnoreCase(searchParams.get("compositionEtablissement")));
    }
    if (searchParams.containsKey("listeEquipements")) {
        booleanBuilder.and(qEntity.listeEquipements.containsIgnoreCase(searchParams.get("listeEquipements")));
    }
    if (searchParams.containsKey("salubriteEtablissement")) {
        booleanBuilder.and(qEntity.salubriteEtablissement.containsIgnoreCase(searchParams.get("salubriteEtablissement")));
    }
    if (searchParams.containsKey("moyensSecours")) {
        booleanBuilder.and(qEntity.moyensSecours.containsIgnoreCase(searchParams.get("moyensSecours")));
    }
    if (searchParams.containsKey("formationExtincteurs")) {
        try {
            Boolean formationExtincteurs = Boolean.valueOf(searchParams.get("formationExtincteurs"));
            booleanBuilder.and(qEntity.formationExtincteurs.eq(formationExtincteurs));
        } catch (Exception e) {
            log.warn("Invalid formationExtincteurs value: {}", searchParams.get("formationExtincteurs"));
        }
    }
    if (searchParams.containsKey("equipementsProtection")) {
        booleanBuilder.and(qEntity.equipementsProtection.containsIgnoreCase(searchParams.get("equipementsProtection")));
    }
    if (searchParams.containsKey("affichageConsignes")) {
        booleanBuilder.and(qEntity.affichageConsignes.containsIgnoreCase(searchParams.get("affichageConsignes")));
    }
    if (searchParams.containsKey("sourceElectricite")) {
        booleanBuilder.and(qEntity.sourceElectricite.containsIgnoreCase(searchParams.get("sourceElectricite")));
    }
    if (searchParams.containsKey("sourceEau")) {
        booleanBuilder.and(qEntity.sourceEau.containsIgnoreCase(searchParams.get("sourceEau")));
    }

    // Gestion des rejets
    if (searchParams.containsKey("dechetsSolides")) {
        booleanBuilder.and(qEntity.dechetsSolides.containsIgnoreCase(searchParams.get("dechetsSolides")));
    }
    if (searchParams.containsKey("rejetsLiquides")) {
        booleanBuilder.and(qEntity.rejetsLiquides.containsIgnoreCase(searchParams.get("rejetsLiquides")));
    }
    if (searchParams.containsKey("rejetsAtmospheriques")) {
        booleanBuilder.and(qEntity.rejetsAtmospheriques.containsIgnoreCase(searchParams.get("rejetsAtmospheriques")));
    }

    // Autres informations
    if (searchParams.containsKey("autresConstats")) {
        booleanBuilder.and(qEntity.autresConstats.containsIgnoreCase(searchParams.get("autresConstats")));
    }
    if (searchParams.containsKey("prescriptionMission")) {
        booleanBuilder.and(qEntity.prescriptionMission.containsIgnoreCase(searchParams.get("prescriptionMission")));
    }
    if (searchParams.containsKey("conclusionMission")) {
        booleanBuilder.and(qEntity.conclusionMission.containsIgnoreCase(searchParams.get("conclusionMission")));
    }
    if (searchParams.containsKey("signatureChef")) {
        booleanBuilder.and(qEntity.signatureChef.containsIgnoreCase(searchParams.get("signatureChef")));
    }
}
}