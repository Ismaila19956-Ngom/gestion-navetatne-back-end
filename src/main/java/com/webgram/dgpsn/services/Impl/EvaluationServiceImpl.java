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
import com.webgram.dgpsn.entities.QEvaluationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EvaluationMapper;
import com.webgram.dgpsn.models.EvaluationDTO;
import com.webgram.dgpsn.repositories.EvaluationRepository;
import com.webgram.dgpsn.services.EvaluationService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluationServiceImpl implements EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final EvaluationMapper evaluationMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public EvaluationDTO create(EvaluationDTO evaluationDTO) {
        var savedEvaluation = evaluationRepository.save(evaluationMapper.asEntity(evaluationDTO));
        log.info("Evaluation successfully added {}", savedEvaluation);
        return evaluationMapper.asDto(savedEvaluation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public EvaluationDTO update(EvaluationDTO evaluationDTO) {
        var evaluationSaved = evaluationMapper.asEntity(evaluationDTO);
        var updatedEvaluation = evaluationMapper.asDto(evaluationRepository.save(evaluationSaved));
        log.info("Evaluation successfully updated {}", updatedEvaluation.getId());
        return updatedEvaluation;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public EvaluationDTO read(Long evaluationId) {
        var evaluation = evaluationRepository
                .findById(evaluationId)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluation", evaluationId));
        log.info("Reading evaluation id {}", evaluationId);
        return evaluationMapper.asDto(evaluation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long evaluationId) {
        try {
            evaluationRepository.deleteById(evaluationId);
            log.info("The evaluation id {} is deleted", evaluationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<EvaluationDTO> readAllEvaluations(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return evaluationRepository.findAll(booleanBuilder, pageable)
                .map(evaluationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QEvaluationEntity.evaluationEntity;
            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
            if (searchParams.containsKey("annee"))
                booleanBuilder.and(qEntity.annee.eq(Integer.valueOf(searchParams.get("annee"))));
        }
        var qEntity = QEvaluationEntity.evaluationEntity;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (searchParams.containsKey("dateDebut")) {
            try {
                LocalDate dateDebut = LocalDate.parse(searchParams.get("dateDebut"), formatter);
                booleanBuilder.and(qEntity.dateDebut.eq(java.sql.Date.valueOf(dateDebut)));
            } catch (Exception e) {
                log.warn("Invalid date format for dateVisite: {}", searchParams.get("dateVisite"));
            }
        }
        if (searchParams.containsKey("dateFin")) {
            try {
                LocalDate dateFin = LocalDate.parse(searchParams.get("dateFin"), formatter);
                booleanBuilder.and(qEntity.dateFin.eq(java.sql.Date.valueOf(dateFin)));
            } catch (Exception e) {
                log.warn("Invalid date format for dateFin: {}", searchParams.get("dateFin"));
            }
        }

    }
}