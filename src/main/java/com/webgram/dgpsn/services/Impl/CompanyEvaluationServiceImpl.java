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
import com.webgram.dgpsn.entities.QCompanyEvaluationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CompanyEvaluationMapper;
import com.webgram.dgpsn.models.CompanyEvaluationDTO;
import com.webgram.dgpsn.repositories.CompanyEvaluationRepository;
import com.webgram.dgpsn.services.CompanyEvaluationService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyEvaluationServiceImpl implements CompanyEvaluationService {
    private final CompanyEvaluationRepository companyEvaluationRepository;
    private final CompanyEvaluationMapper companyEvaluationMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public CompanyEvaluationDTO create(CompanyEvaluationDTO companyEvaluationDTO) {
        var savedCompanyEvaluation = companyEvaluationRepository.save(companyEvaluationMapper.asEntity(companyEvaluationDTO));
        log.info("Company evaluation successfully added {}", savedCompanyEvaluation);
        return companyEvaluationMapper.asDto(savedCompanyEvaluation);
    }

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public List<CompanyEvaluationDTO> createMultiple(List<CompanyEvaluationDTO> companyEvaluationDTOs) {
        var entities = companyEvaluationDTOs.stream()
                .map(companyEvaluationMapper::asEntity)
                .collect(Collectors.toList());
        var savedEntities = companyEvaluationRepository.saveAll(entities);
        log.info("Multiple company evaluations successfully added");
        return savedEntities.stream()
                .map(companyEvaluationMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public CompanyEvaluationDTO update(CompanyEvaluationDTO companyEvaluationDTO) {
        var companyEvaluationSaved = companyEvaluationMapper.asEntity(companyEvaluationDTO);
        var updatedCompanyEvaluation = companyEvaluationMapper.asDto(companyEvaluationRepository.save(companyEvaluationSaved));
        log.info("Company evaluation successfully updated {}", updatedCompanyEvaluation.getId());
        return updatedCompanyEvaluation;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public CompanyEvaluationDTO read(Long companyEvaluationId) {
        var companyEvaluation = companyEvaluationRepository
                .findById(companyEvaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("CompanyEvaluation", companyEvaluationId));
        log.info("Reading company evaluation id {}", companyEvaluationId);
        return companyEvaluationMapper.asDto(companyEvaluation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long companyEvaluationId) {
        try {
            companyEvaluationRepository.deleteById(companyEvaluationId);
            log.info("The company evaluation id {} is deleted", companyEvaluationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<CompanyEvaluationDTO> readAllCompanyEvaluations(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return companyEvaluationRepository.findAll(booleanBuilder, pageable)
                .map(companyEvaluationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCompanyEvaluationEntity.companyEvaluationEntity;
            if (searchParams.containsKey("startupId"))
                booleanBuilder.and(qEntity.startup.id.eq(Long.valueOf(searchParams.get("startupId"))));
            if (searchParams.containsKey("evaluationId"))
                booleanBuilder.and(qEntity.evaluation.id.eq(Long.valueOf(searchParams.get("evaluationId"))));
            if (searchParams.containsKey("annee")) {
                String annee = searchParams.get("annee");
                booleanBuilder.and(qEntity.evaluation.annee.eq(Integer.valueOf(annee)));}
            if (searchParams.containsKey("categoryId"))
                booleanBuilder.and(qEntity.category.id.eq(Long.valueOf(searchParams.get("categoryId"))));
            if (searchParams.containsKey("montant"))
                booleanBuilder.and(qEntity.montant.eq(Double.valueOf(searchParams.get("montant"))));
            if (searchParams.containsKey("pourcentageAvancement"))
                booleanBuilder.and(qEntity.pourcentageAvancement.eq(Integer.valueOf(searchParams.get("pourcentageAvancement"))));
        }
    }
}