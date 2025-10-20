package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QEngagementEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EngagementMapper;
import com.webgram.dgpsn.models.EngagementDTO;
import com.webgram.dgpsn.repositories.EngagementRepository;
import com.webgram.dgpsn.services.EngagementService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EngagementServiceImpl implements EngagementService {
    private final EngagementRepository engagementRepository;
    private final EngagementMapper engagementMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public EngagementDTO create(EngagementDTO engagementDTO) {
        var savedEngagement = engagementRepository.save(engagementMapper.asEntity(engagementDTO));
        log.info("Engagement successfully added {}", savedEngagement);
        return engagementMapper.asDto(savedEngagement);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public EngagementDTO update(EngagementDTO engagementDTO) {
        var engagementSaved = engagementMapper.asEntity(engagementDTO);
        var updatedEngagement = engagementMapper.asDto(engagementRepository.save(engagementSaved));
        log.info("Engagement successfully updated {}", updatedEngagement.getId());
        return updatedEngagement;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public EngagementDTO read(Long engagementId) {
        var engagement = engagementRepository
                .findById(engagementId)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement", engagementId));
        log.info("Reading engagement id {}", engagementId);
        return engagementMapper.asDto(engagement);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long engagementId) {
        try {
            engagementRepository.deleteById(engagementId);
            log.info("The engagement id {} is deleted", engagementId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<EngagementDTO> readAllEngagements(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return engagementRepository.findAll(booleanBuilder, pageable)
                .map(engagementMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QEngagementEntity.engagementEntity;
            if (searchParams.containsKey("mois"))
                booleanBuilder.and(qEntity.mois.eq(searchParams.get("mois")));
            if (searchParams.containsKey("reference"))
                booleanBuilder.and(qEntity.reference.eq(searchParams.get("reference")));
            if (searchParams.containsKey("objet"))
                booleanBuilder.and(qEntity.objet.eq(searchParams.get("objet")));
            if (searchParams.containsKey("montantEngage"))
                booleanBuilder.and(qEntity.montantEngage.eq(Double.valueOf(searchParams.get("montantEngage"))));
            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(searchParams.get("statut")));
            if (searchParams.containsKey("fournisseur"))
                booleanBuilder.and(qEntity.fournisseur.eq(searchParams.get("fournisseur")));
//            if (searchParams.containsKey("typeEngagementId"))
//                booleanBuilder.and(qEntity.typeEngagement.id.eq(Long.valueOf(searchParams.get("typeEngagementId"))));
            if (searchParams.containsKey("budgetPassationId"))
                booleanBuilder.and(qEntity.budgetPassation.id.eq(Long.valueOf(searchParams.get("budgetPassationId"))));
//            if (searchParams.containsKey("structureBeneficiaireId"))
//                booleanBuilder.and(qEntity.structureBeneficiaire.id.eq(Long.valueOf(searchParams.get("structureBeneficiaireId"))));
               if (searchParams.containsKey("dateEngagement"))
                booleanBuilder.and(qEntity.dateEngagement.eq(LocalDate.parse(searchParams.get("dateEngagement"))));
            if (searchParams.containsKey("dateReceptionFacture"))
                booleanBuilder.and(qEntity.dateReceptionFacture.eq(LocalDate.parse(searchParams.get("dateReceptionFacture"))));
            if (searchParams.containsKey("justificatif"))
                booleanBuilder.and(qEntity.justificatif.eq(searchParams.get("justificatif")));
            if (searchParams.containsKey("creePar"))
                booleanBuilder.and(qEntity.creePar.eq(searchParams.get("creePar")));
            if (searchParams.containsKey("dateCreation"))
                booleanBuilder.and(qEntity.dateCreation.eq(LocalDate.parse(searchParams.get("dateCreation"))));
        }
    }
}