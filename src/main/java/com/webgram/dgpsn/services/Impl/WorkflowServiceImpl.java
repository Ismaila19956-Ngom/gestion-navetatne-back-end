package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.QWorkflowEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.exceptions.WorkflowUniqueModuleException;
import com.webgram.dgpsn.mappers.WorkflowMapper;
import com.webgram.dgpsn.models.WorkflowDTO;
import com.webgram.dgpsn.repositories.WorkflowRepository;
import com.webgram.dgpsn.repositories.WorkflowStepRepository;
import com.webgram.dgpsn.services.WorkflowService;
import com.webgram.dgpsn.services.WorkflowStepService;

import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkflowServiceImpl implements WorkflowService {
    private final WorkflowRepository workflowRepository;
    private final WorkflowMapper workflowMapper;
    private final WorkflowStepRepository workflowStepRepository;
    private final WorkflowStepService workflowStepService;


    @Override
    public WorkflowDTO createWorkflow(WorkflowDTO workflowDTO) {
        WorkflowType type = workflowDTO.getType();
        if (workflowRepository.findByType(type).isPresent()) {
            throw new WorkflowUniqueModuleException(
                    String.format("Un workflow avec le module %s existe déjà !", type)
            );
        }

        log.info("WF_TYPE: {}", type);

        var savedWorkFlow = workflowRepository.save(workflowMapper.asEntity(workflowDTO));

        log.info("workflow successfully added {}", savedWorkFlow.getId());

        return workflowMapper.asDto(savedWorkFlow);
    }


//    @Override
//    public WorkflowDTO createWorkflow(WorkflowDTO workflowDTO) {
//        if(workflowRepository.findByType(WorkflowType.FLUX_TRESORERIE).isPresent()) {
//            throw new WorkflowUniqueModuleException(String.format("Un workflow avec le module %s déja!", WorkflowType.FLUX_TRESORERIE));
//        }
//
//        log.info("WF_TYPE: {}", workflowDTO.getType());
//
//        var savedWorkFlow = workflowRepository.save(workflowMapper.asEntity(workflowDTO));
//
//        log.info("workflow successfully added {}", savedWorkFlow.getId());
//
//        return workflowMapper.asDto(savedWorkFlow);
//    }

    @Override
    public WorkflowDTO updateWorkflow(WorkflowDTO workflowDTO) {
        if(workflowRepository.existsByTypeAndIdNot(WorkflowType.DEMANDE_CONGE, workflowDTO.getId())) {
            throw new WorkflowUniqueModuleException(String.format("Un workflow avec le module %s existe déja!", WorkflowType.DEMANDE_CONGE));
        }

        var updatedWorkFlow = workflowRepository.save(workflowMapper.asEntity(workflowDTO));

        log.info("workflow successfully updated {}", updatedWorkFlow.getId());

        return workflowMapper.asDto(updatedWorkFlow);
    }

    @Override
    public WorkflowDTO readWorkflow(Long workflowId) {
        var workFlow = workflowRepository.findById(workflowId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Workflow '%d' n'existe pas!", workflowId)));

        log.info("workflow successfully red {}", workFlow.getId());

        return workflowMapper.asDto(workFlow);
    }

    @Override
    public void deleteWorkflow(Long workflowId) {
        try {
            var steps = workflowStepRepository.findByWorkflowId(workflowId);
            steps.forEach(step -> {
                workflowStepService.deleteWorkflowStep(step.getId());
            });
            workflowRepository.deleteById(workflowId);
            log.info("The workflow id {} is deleted", workflowId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<WorkflowDTO> readAllWorkflow(Map<String, String> searchParams, int page, int size) {
        var searchBuilder = buildSearch(searchParams);
        var workflowPage = workflowRepository
                .findAll(searchBuilder, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        return workflowPage.map(workflowMapper::asDto);
    }

    private BooleanBuilder buildSearch(Map<String, String> searchParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(searchParams)) {
            var qWorkflow = QWorkflowEntity.workflowEntity;
            if(searchParams.containsKey("id")) {
                booleanBuilder.and(qWorkflow.id.eq(Long.parseLong(searchParams.get("id"))));
            }
            if(searchParams.containsKey("code")) {
                booleanBuilder.and(qWorkflow.code.containsIgnoreCase(searchParams.get("code")));
            }
            if(searchParams.containsKey("libelle")) {
                booleanBuilder.and(qWorkflow.libelle.containsIgnoreCase(searchParams.get("libelle")));
            }
        }
        return booleanBuilder;
    }
}
