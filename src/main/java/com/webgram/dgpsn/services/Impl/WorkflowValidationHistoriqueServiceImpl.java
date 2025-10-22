package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QWorkflowValidationHistoriqueEntity;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.entities.WorkflowValidationHistoriqueEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.exceptions.UserNotAllowedToValidWorkflowException;
import com.webgram.dgpsn.mappers.WorkflowValidationHistoriqueMapper;
import com.webgram.dgpsn.models.WorkflowValidationHistoriqueDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.security.SecurityUtils;
import com.webgram.dgpsn.services.WorkflowValidationHistoriqueService;
import com.webgram.dgpsn.tools.ActionType;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkflowValidationHistoriqueServiceImpl implements WorkflowValidationHistoriqueService {
    private final WorkflowValidationHistoriqueRepository workflowHistoriqueRepository;
//    private final WorkflowStepRepository workflowStepRepository;
    private final UserRepository userRepository;
    private final WorkflowValidationHistoriqueMapper historiqueMapper;
//    private final WorkflowStepService workflowStepService;
    private final WorkflowStepValidationUserRepository stepValidationUserRepository;
//    private final WorkflowStepValidationRepository workflowStepValidationRepository;
//    private final WorkflowStepValidationUserRepository validationUserRepository;
//    private final FluxtresorerieRepository fluxtresorerieRepository;
//    private final ResultatindicaRepository resultatindicaRepository;
//    private final FluxtresorerieService fluxtresorerieService;

    @Override
    @Journal(actionType = ActionType.ADD_WORKFLOW_HISTORIQUE)
    public WorkflowValidationHistoriqueDTO createHistorique(WorkflowValidationHistoriqueDTO historiqueDTO) {
//        var stepToValid =  workflowStepRepository.findById(historiqueDTO.getEtapeId())
//                .orElseThrow(() -> new ResourceNotFoundException(String.format("L'etape avec l'id '%d' n'existe pas!", historiqueDTO.getEtapeId())));

        /**
         * Here we set the current workflow to the next step
         * according to the previous step ('stepToValid')
         * and the object that workflow is configured ('historiqueDTO')
         * Historique is saved only if setWorkflowToNextStep success
         */
//        this.setWorkflowToNextStep(stepToValid, historiqueDTO);

        var historiqueToSave = historiqueMapper.asEntity(historiqueDTO);

        if(Objects.nonNull(historiqueDTO.getEtapeId())) {
            SecurityUtils.getCurrentUserLogin()
                    .flatMap(userRepository::findByLogin)
                    .ifPresent(user -> {
                        var configOpt = stepValidationUserRepository.findUserConfig(historiqueDTO.getEtapeId(), user.getId());
                        if(configOpt.isPresent()) {
                            historiqueToSave.setUser(configOpt.get().getUser());
                        } else {
                            throw new UserNotAllowedToValidWorkflowException(String.format("User '%s' '%s' n'est pas configuré pour faire la validation!", user.getAgent().getPrenom(), user.getAgent().getNom()));
                        }
                    });
        }

        historiqueToSave.setDate(new Date(System.currentTimeMillis()));
        if(historiqueDTO.getWorkflowType().equals(WorkflowType.DEMANDE_CONGE) || historiqueDTO.getWorkflowType().equals(WorkflowType.VALIDATION_COURRIER)) {
            if (historiqueDTO.getYear() == null || historiqueDTO.getMonth() == null) {
                throw new IllegalArgumentException("L'année et le mois sont requis pour enregistrer une validation.");
            }
        }

        historiqueToSave.setYear(historiqueDTO.getYear());
        historiqueToSave.setMonth(historiqueDTO.getMonth());
        var savedHistorique = workflowHistoriqueRepository.save(historiqueToSave);

        log.info("validation étape successfully added {}", historiqueDTO.getId());

        return historiqueMapper.asDto(savedHistorique);
    }

//    @Override
//    public WorkflowValidationHistoriqueDTO updateHistorique(WorkflowValidationHistoriqueDTO historiqueDTO) {
//
//        var updatedWorkFlow = null;
//
//        log.info("validation étape successfully updated {}", updatedWorkFlow.getId());
//
//        return historiqueMapper.asDto(updatedWorkFlow);
//    }

    @Override
    @Journal(actionType = ActionType.READ_WORKFLOW_HISTORIQUE)
    public WorkflowValidationHistoriqueDTO readHistorique(Long historiqueId) {
        var workFlow = workflowHistoriqueRepository.findById(historiqueId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Une validation avec cet '%d' n'existe pas!", historiqueId)));

        log.info("validation étape successfully red {}", workFlow.getId());

        return historiqueMapper.asDto(workFlow);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_WORKFLOW_HISTORIQUE)
    public void deleteHistorique(Long workflowId) {
        try {
            workflowHistoriqueRepository.deleteById(workflowId);
            log.info("validation étape id {} is deleted", workflowId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_WORKFLOW_HISTORIQUE)
    public Page<WorkflowValidationHistoriqueDTO> readAllHistorique(Map<String, String> searchParams, int page, int size) {
        var searchBuilder = buildSearch(searchParams);
        var historiquePage = workflowHistoriqueRepository
                .findAll(searchBuilder, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date")));
        var histpriqueDTOs = historiquePage.map(historiqueMapper::asDto);

        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByLogin)
                .ifPresent(user -> histpriqueDTOs.getContent().stream()
                        .forEach(historique -> {
                            if(historique.getUser().getId().equals(user.getId())) {
                                historique.getUser().setPrenom("Moi");
                                historique.getUser().setNom("");
                            }
                        })
                );

        return histpriqueDTOs;
    }

    private BooleanBuilder buildSearch(Map<String, String> searchParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(searchParams)) {
            var qHistorique = QWorkflowValidationHistoriqueEntity.workflowValidationHistoriqueEntity;
            if(searchParams.containsKey("id")) {
                booleanBuilder.and(qHistorique.id.eq(Long.parseLong(searchParams.get("id"))));
            }
            if(searchParams.containsKey("workflowType")) {
                booleanBuilder.and(qHistorique.workflowType.eq(WorkflowType.valueOf(searchParams.get("workflowType"))));
            }
            if(searchParams.containsKey("entityId")) {
                booleanBuilder.and(qHistorique.entityId.eq(Long.parseLong(searchParams.get("entityId"))));
            }
            if (searchParams.containsKey("year")) {
                booleanBuilder.and(qHistorique.year.eq(Integer.parseInt(searchParams.get("year"))));
            }
            if (searchParams.containsKey("month")) {
                booleanBuilder.and(qHistorique.month.eq(Integer.parseInt(searchParams.get("month"))));
            }
            if (searchParams.containsKey("dateCalcul")) {
                booleanBuilder.and(qHistorique.dateCalcul.eq(LocalDate.parse(searchParams.get("dateCalcul"))));
            }
        }
        return booleanBuilder;
    }

    @Override
    public Optional<WorkflowStepEntity> findLastValidatedStepForLcr(int year, int month) {
        return workflowHistoriqueRepository.findTopByWorkflowTypeAndYearAndMonthOrderByDateDesc(
                        WorkflowType.DEMANDE_CONGE, year, month)
                .map(WorkflowValidationHistoriqueEntity::getEtape);
    }

    @Override
    public Optional<WorkflowStepEntity> findLastValidatedStepForNsfr(int year, int month) {
        return workflowHistoriqueRepository.findTopByWorkflowTypeAndYearAndMonthOrderByDateDesc(
                        WorkflowType.VALIDATION_COURRIER, year, month)
                .map(WorkflowValidationHistoriqueEntity::getEtape);
    }

//    private void setWorkflowToNextStep(WorkflowStepEntity stepToValid, WorkflowValidationHistoriqueDTO historiqueDTO) {
//        var stepConfig = workflowStepValidationRepository.findByWorkflowStepId(stepToValid.getId())
//                .orElseThrow(() -> new ResourceNotFoundException(String.format("Aucune configuration de validation n'existe pour cette étape id '%d'", stepToValid.getId())));
//
//        var nextStep = workflowStepService.getNextStep(stepToValid.getWorkflow().getId(), stepToValid.getOrdre());
//
//        if(historiqueDTO.getWorkflowType().equals(WorkflowType.FLUX_TRESORERIE)) {
//            fluxtresorerieService.validateStepWorkflow(historiqueDTO, stepToValid, stepConfig, nextStep);
//        }
//        else if (historiqueDTO.getWorkflowType().equals(WorkflowType.RESULTAT_INDICATEUR)) {
//            // TODO: implementer ici la vilidation pour 'RESULTAT_INDICATEUR'
//        }
//    }
}
