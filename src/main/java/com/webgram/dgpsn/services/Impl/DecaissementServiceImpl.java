package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QDecaissementEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DecaissementMapper;
import com.webgram.dgpsn.models.DecaissementDTO;
import com.webgram.dgpsn.repositories.DecaissementRepository;
import com.webgram.dgpsn.services.DecaissementService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DecaissementServiceImpl implements DecaissementService {
    private final DecaissementRepository decaissementRepository;
    private final DecaissementMapper decaissementMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public DecaissementDTO create(DecaissementDTO decaissementDTO) {
        var savedDecaissement = decaissementRepository.save(decaissementMapper.asEntity(decaissementDTO));
        log.info("Decaissement successfully added {}", savedDecaissement);
        return decaissementMapper.asDto(savedDecaissement);
    }


    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public DecaissementDTO update(DecaissementDTO decaissementDTO) {
        var decaissementSaved = decaissementMapper.asEntity(decaissementDTO);
        var updatedDecaissement = decaissementMapper.asDto(decaissementRepository.save(decaissementSaved));
        log.info("Decaissement successfully updated {}", updatedDecaissement.getId());
        return updatedDecaissement;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public DecaissementDTO read(Long decaissementId) {
        var decaissement = decaissementRepository
                .findById(decaissementId)
                .orElseThrow(() -> new ResourceNotFoundException("Decaissement", decaissementId));
        log.info("Reading decaissement id {}", decaissementId);
        return decaissementMapper.asDto(decaissement);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long decaissementId) {
        try {
            decaissementRepository.deleteById(decaissementId);
            log.info("The decaissement id {} is deleted", decaissementId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<DecaissementDTO> readAllDecaissements(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return decaissementRepository.findAll(booleanBuilder, pageable)
                .map(decaissementMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QDecaissementEntity.decaissementEntity;
            if (searchParams.containsKey("mois"))
                booleanBuilder.and(qEntity.mois.eq(searchParams.get("mois")));
            if (searchParams.containsKey("activite"))
                booleanBuilder.and(qEntity.activite.eq(searchParams.get("activite")));
            if (searchParams.containsKey("montantPrev"))
                booleanBuilder.and(qEntity.montantPrev.eq(Double.valueOf(searchParams.get("montantPrev"))));
            if (searchParams.containsKey("montantDecaisse"))
                booleanBuilder.and(qEntity.montantDecaisse.eq(Double.valueOf(searchParams.get("montantDecaisse"))));
            if (searchParams.containsKey("ecart"))
                booleanBuilder.and(qEntity.ecart.eq(Double.valueOf(searchParams.get("ecart"))));
            if (searchParams.containsKey("observation"))
                booleanBuilder.and(qEntity.observation.eq(searchParams.get("observation")));
            if (searchParams.containsKey("ordonnancementId"))
                booleanBuilder.and(qEntity.ordonnancement.id.eq(Long.valueOf(searchParams.get("ordonnancementId"))));
        }
    }
}