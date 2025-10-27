package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QSuiviInventaireEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SuiviInventaireMapper;
import com.webgram.dgpsn.models.SuiviInventaireDTO;
import com.webgram.dgpsn.repositories.SuiviInventaireRepository;
import com.webgram.dgpsn.services.SuiviInventaireService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SuiviInventaireServiceImpl implements SuiviInventaireService {
    private final SuiviInventaireRepository suiviInventaireRepository;
    private final SuiviInventaireMapper suiviInventaireMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public SuiviInventaireDTO create(SuiviInventaireDTO suiviInventaireDTO) {
        var savedSuiviInventaire = suiviInventaireRepository.save(suiviInventaireMapper.asEntity(suiviInventaireDTO));
        log.info("SuiviInventaire successfully added {}", savedSuiviInventaire);
        return suiviInventaireMapper.asDto(savedSuiviInventaire);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public SuiviInventaireDTO update(SuiviInventaireDTO suiviInventaireDTO) {
        var suiviInventaireSaved = suiviInventaireMapper.asEntity(suiviInventaireDTO);
        var updatedSuiviInventaire = suiviInventaireMapper.asDto(suiviInventaireRepository.save(suiviInventaireSaved));
        log.info("SuiviInventaire successfully updated {}", updatedSuiviInventaire.getId());
        return updatedSuiviInventaire;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public SuiviInventaireDTO read(Long suiviInventaireId) {
        var suiviInventaire = suiviInventaireRepository
                .findById(suiviInventaireId)
                .orElseThrow(() -> new ResourceNotFoundException("SuiviInventaire", suiviInventaireId));
        log.info("Reading suivi inventaire id {}", suiviInventaireId);
        return suiviInventaireMapper.asDto(suiviInventaire);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long suiviInventaireId) {
        try {
            suiviInventaireRepository.deleteById(suiviInventaireId);
            log.info("The suivi inventaire id {} is deleted", suiviInventaireId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<SuiviInventaireDTO> readAllSuiviInventaires(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return suiviInventaireRepository.findAll(booleanBuilder, pageable)
                .map(suiviInventaireMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QSuiviInventaireEntity.suiviInventaireEntity;
            if (searchParams.containsKey("nomMateriel"))
                booleanBuilder.and(qEntity.nomMateriel.eq(searchParams.get("nomMateriel")));
            if (searchParams.containsKey("typeInventaireId"))
                booleanBuilder.and(qEntity.typeInventaire.id.eq(Long.valueOf(searchParams.get("typeInventaireId"))));
            if (searchParams.containsKey("localisationId"))
                booleanBuilder.and(qEntity.localisation.id.eq(Long.valueOf(searchParams.get("localisationId"))));
            if (searchParams.containsKey("occupantResponsable"))
                booleanBuilder.and(qEntity.occupantResponsable.eq(searchParams.get("occupantResponsable")));
            if (searchParams.containsKey("etatId"))
                booleanBuilder.and(qEntity.etat.id.eq(Long.valueOf(searchParams.get("etatId"))));
            if (searchParams.containsKey("dateAcquisition"))
                booleanBuilder.and(qEntity.dateAcquisition.eq(LocalDate.parse(searchParams.get("dateAcquisition"))));
            if (searchParams.containsKey("bailleurId"))
                booleanBuilder.and(qEntity.bailleur.id.eq(Long.valueOf(searchParams.get("bailleur"))));
            if (searchParams.containsKey("observation"))
                booleanBuilder.and(qEntity.observation.eq(searchParams.get("observation")));
            if (searchParams.containsKey("inventaireId"))
                booleanBuilder.and(qEntity.inventaire.id.eq(Long.valueOf(searchParams.get("inventaireId"))));
        }
    }
}