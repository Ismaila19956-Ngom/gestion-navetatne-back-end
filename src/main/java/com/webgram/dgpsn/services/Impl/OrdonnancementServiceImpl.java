package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QOrdonnancementEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.OrdonnancementMapper;
import com.webgram.dgpsn.models.OrdonnancementDTO;
import com.webgram.dgpsn.repositories.OrdonnancementRepository;
import com.webgram.dgpsn.services.OrdonnancementService;
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
public class OrdonnancementServiceImpl implements OrdonnancementService {
    private final OrdonnancementRepository ordonnancementRepository;
    private final OrdonnancementMapper ordonnancementMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public OrdonnancementDTO create(OrdonnancementDTO ordonnancementDTO) {
        var savedOrdonnancement = ordonnancementRepository.save(ordonnancementMapper.asEntity(ordonnancementDTO));
        log.info("Ordonnancement successfully added {}", savedOrdonnancement);
        return ordonnancementMapper.asDto(savedOrdonnancement);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public OrdonnancementDTO update(OrdonnancementDTO ordonnancementDTO) {
        var ordonnancementSaved = ordonnancementMapper.asEntity(ordonnancementDTO);
        var updatedOrdonnancement = ordonnancementMapper.asDto(ordonnancementRepository.save(ordonnancementSaved));
        log.info("Ordonnancement successfully updated {}", updatedOrdonnancement.getId());
        return updatedOrdonnancement;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public OrdonnancementDTO read(Long ordonnancementId) {
        var ordonnancement = ordonnancementRepository
                .findById(ordonnancementId)
                .orElseThrow(() -> new ResourceNotFoundException("Ordonnancement", ordonnancementId));
        log.info("Reading ordonnancement id {}", ordonnancementId);
        return ordonnancementMapper.asDto(ordonnancement);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long ordonnancementId) {
        try {
            ordonnancementRepository.deleteById(ordonnancementId);
            log.info("The ordonnancement id {} is deleted", ordonnancementId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<OrdonnancementDTO> readAllOrdonnancements(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return ordonnancementRepository.findAll(booleanBuilder, pageable)
                .map(ordonnancementMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QOrdonnancementEntity.ordonnancementEntity;
            if (searchParams.containsKey("reference"))
                booleanBuilder.and(qEntity.reference.eq(searchParams.get("reference")));
            if (searchParams.containsKey("dateOrdonnancement"))
                booleanBuilder.and(qEntity.dateOrdonnancement.eq(LocalDate.parse(searchParams.get("dateOrdonnancement"))));
            if (searchParams.containsKey("montantOrdonne"))
                booleanBuilder.and(qEntity.montantOrdonne.eq(Double.valueOf(searchParams.get("montantOrdonne"))));
            if (searchParams.containsKey("objet"))
                booleanBuilder.and(qEntity.objet.eq(searchParams.get("objet")));
            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(searchParams.get("statut")));
            if (searchParams.containsKey("modePaiement"))
                booleanBuilder.and(qEntity.modePaiement.eq(searchParams.get("modePaiement")));
//            if (searchParams.containsKey("beneficiaireId"))
//                booleanBuilder.and(qEntity.beneficiaire.id.eq(Long.valueOf(searchParams.get("beneficiaireId"))));
//            if (searchParams.containsKey("engagementId"))
//                booleanBuilder.and(qEntity.engagement.id.eq(Long.valueOf(searchParams.get("engagementId"))));
            if (searchParams.containsKey("budgetPassationId"))
                booleanBuilder.and(qEntity.budgetPassation.id.eq(Long.valueOf(searchParams.get("budgetPassationId"))));

            if (searchParams.containsKey("justificatif"))
                booleanBuilder.and(qEntity.justificatif.eq(searchParams.get("justificatif")));
        }
    }
}