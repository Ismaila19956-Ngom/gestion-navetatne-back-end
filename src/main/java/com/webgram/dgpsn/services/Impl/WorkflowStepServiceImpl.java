package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.QWorkflowStepEntity;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.WorkflowStepMapper;
import com.webgram.dgpsn.models.WorkflowStepDTO;
import com.webgram.dgpsn.repositories.WorkflowRepository;
import com.webgram.dgpsn.repositories.WorkflowStepRepository;
import com.webgram.dgpsn.repositories.WorkflowStepValidationRepository;
import com.webgram.dgpsn.services.WorkflowStepService;
import com.webgram.dgpsn.services.WorkflowStepValidationService;

import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkflowStepServiceImpl implements WorkflowStepService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowStepRepository workflowStepRepository;
    private final WorkflowStepMapper workflowStepMapper;
    private final WorkflowStepValidationRepository workflowStepValidationRepository;
    private final WorkflowStepValidationService workflowStepValidationService;

    @Override
    public WorkflowStepDTO createWorkflowStep(WorkflowStepDTO workflowStepDTO) {
        if(!workflowRepository.existsById(workflowStepDTO.getWorkflowId())) {
            throw new IllegalArgumentException("Un workflow est nécessaire pour la creation d'une étape!");
        }

        if(Objects.isNull(workflowStepDTO.getOrdre())) {
            throw new IllegalArgumentException("L'ordre est nécessaire pour la creation d'une étape!");
        }

        if(workflowStepRepository.existsByOrdreAndWorkflowId(workflowStepDTO.getOrdre(), workflowStepDTO.getWorkflowId())) {
            throw new ResourceNotFoundException(String.format("Une étape avec l'odre '%d' existe déja!", workflowStepDTO.getOrdre()));
        }

        var savedWorkFlowStep = workflowStepRepository.save(workflowStepMapper.asEntity(workflowStepDTO));

        log.info("workflowStep successfully added {}", savedWorkFlowStep.getId());

        return workflowStepMapper.asDto(savedWorkFlowStep);
    }

    @Override
    public WorkflowStepDTO updateWorkflowStep(WorkflowStepDTO workflowStepDTO) {
        if(!workflowRepository.existsById(workflowStepDTO.getWorkflowId())) {
            throw new IllegalArgumentException("Un workflow est nécessaire pour la creation d'une etape!");
        }

        if(Objects.isNull(workflowStepDTO.getOrdre())) {
            throw new IllegalArgumentException("Un ordre est nécessaire pour la creation d'une étape!");
        }

        var updatedWorkFlowStep = workflowStepRepository.save(workflowStepMapper.asEntity(workflowStepDTO));

        log.info("workflowStep successfully updated {}", updatedWorkFlowStep.getId());

        return workflowStepMapper.asDto(updatedWorkFlowStep);
    }

    @Override
    public WorkflowStepDTO readWorkflowStep(Long workflowStepId) {
        var workFlowStep = workflowStepRepository.findById(workflowStepId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("WorkflowStep '%d' n'existe pas!", workflowStepId)));

        log.info("workflowStep successfully red {}", workFlowStep.getId());

        return workflowStepMapper.asDto(workFlowStep);
    }

    @Override
    public void deleteWorkflowStep(Long workflowStepId) {
        try {
            var stepValidation = workflowStepValidationRepository.findByWorkflowStepId(workflowStepId);
            if(stepValidation.isPresent()) {
                workflowStepValidationService.deleteWorkflowStepValidation(stepValidation.get().getId());
            }
            workflowStepRepository.deleteById(workflowStepId);
            log.info("The workflowStep id {} is deleted", workflowStepId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<WorkflowStepDTO> readAllWorkflowStep(Map<String, String> searchParams, int page, int size) {
        var searchBuilder = buildSearch(searchParams);
        var workflowStepPage = workflowStepRepository
                .findAll(searchBuilder, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "ordre")));
        return workflowStepPage.map(workflowStepMapper::asDto);
    }

    @Override
    public WorkflowStepEntity getNextStep(Long workflowId, Integer stepOrdre) {
        return workflowStepRepository
                .findFirstByWorkflowIdAndOrdreGreaterThanOrderByOrdreAsc(workflowId, stepOrdre)
                .orElse(null);
    }

    private BooleanBuilder buildSearch(Map<String, String> searchParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(searchParams)) {
            var qWorkflowStep = QWorkflowStepEntity.workflowStepEntity;
            if(searchParams.containsKey("id")) {
                booleanBuilder.and(qWorkflowStep.id.eq(Long.parseLong(searchParams.get("id"))));
            }
            if(searchParams.containsKey("code")) {
                booleanBuilder.and(qWorkflowStep.code.containsIgnoreCase(searchParams.get("code")));
            }
            if(searchParams.containsKey("libelle")) {
                booleanBuilder.and(qWorkflowStep.libelle.containsIgnoreCase(searchParams.get("libelle")));
            }
            if(searchParams.containsKey("ordre")) {
                booleanBuilder.and(qWorkflowStep.ordre.eq(Integer.parseInt(searchParams.get("ordre"))));
            }
            if(searchParams.containsKey("workflowId")) {
                booleanBuilder.and(qWorkflowStep.workflow.id.eq(Long.parseLong(searchParams.get("workflowId"))));
            }
        }
        return booleanBuilder;
    }
}
