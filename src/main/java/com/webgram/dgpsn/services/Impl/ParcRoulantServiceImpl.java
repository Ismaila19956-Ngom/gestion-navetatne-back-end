package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QParcRoulantEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ParcRoulantMapper;
import com.webgram.dgpsn.models.ParcRoulantDTO;
import com.webgram.dgpsn.repositories.ParcRoulantRepository;
import com.webgram.dgpsn.services.ParcRoulantService;
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
public class ParcRoulantServiceImpl implements ParcRoulantService {
    private final ParcRoulantRepository parcRoulantRepository;
    private final ParcRoulantMapper parcRoulantMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public ParcRoulantDTO create(ParcRoulantDTO parcRoulantDTO) {
        var savedParcRoulant = parcRoulantRepository.save(parcRoulantMapper.asEntity(parcRoulantDTO));
        log.info("ParcRoulant successfully added {}", savedParcRoulant);
        return parcRoulantMapper.asDto(savedParcRoulant);
    }


    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public ParcRoulantDTO update(ParcRoulantDTO parcRoulantDTO) {
        var parcRoulantSaved = parcRoulantMapper.asEntity(parcRoulantDTO);
        var updatedParcRoulant = parcRoulantMapper.asDto(parcRoulantRepository.save(parcRoulantSaved));
        log.info("ParcRoulant successfully updated {}", updatedParcRoulant.getId());
        return updatedParcRoulant;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public ParcRoulantDTO read(Long parcRoulantId) {
        var parcRoulant = parcRoulantRepository
                .findById(parcRoulantId)
                .orElseThrow(() -> new ResourceNotFoundException("ParcRoulant", parcRoulantId));
        log.info("Reading parc roulant id {}", parcRoulantId);
        return parcRoulantMapper.asDto(parcRoulant);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long parcRoulantId) {
        try {
            parcRoulantRepository.deleteById(parcRoulantId);
            log.info("The parc roulant id {} is deleted", parcRoulantId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<ParcRoulantDTO> readAllParcRoulants(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return parcRoulantRepository.findAll(booleanBuilder, pageable)
                .map(parcRoulantMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QParcRoulantEntity.parcRoulantEntity;
            if (searchParams.containsKey("numero"))
                booleanBuilder.and(qEntity.numero.eq(Integer.valueOf(searchParams.get("numero"))));
            if (searchParams.containsKey("marqueId"))
                booleanBuilder.and(qEntity.marque.id.eq(Long.valueOf(searchParams.get("marqueId"))));
            if (searchParams.containsKey("immatriculation"))
                booleanBuilder.and(qEntity.immatriculation.eq(searchParams.get("immatriculation")));
            if (searchParams.containsKey("miseEnService"))
                booleanBuilder.and(qEntity.miseEnService.eq(LocalDate.parse(searchParams.get("miseEnService"))));
            if (searchParams.containsKey("localisationId"))
                booleanBuilder.and(qEntity.localisation.id.eq(Long.valueOf(searchParams.get("localisationId"))));
            if (searchParams.containsKey("etatId"))
                booleanBuilder.and(qEntity.etat.id.eq(Long.valueOf(searchParams.get("etatId"))));
        }
    }
}