package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.QWorkflowStepValidationEntity;
import com.webgram.dgpsn.entities.QWorkflowStepValidationUserEntity;
import com.webgram.dgpsn.entities.WorkflowStepValidationUserEntity;
import com.webgram.dgpsn.entities.enums.WorkflowValidationType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.UserMapper;
import com.webgram.dgpsn.mappers.WorkflowStepValidationMapper;
import com.webgram.dgpsn.models.WorkflowStepValidationDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.WorkflowStepValidationService;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WorkflowStepValidationServiceImpl implements WorkflowStepValidationService {
    private final WorkflowStepRepository workflowStepRepository;
    private final WorkflowStepValidationRepository workflowStepValidationRepository;
    private final WorkflowStepValidationUserRepository workflowStepValidationUserRepository;
    private final WorkflowStepValidationMapper workflowStepValidationMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    @Override
    public WorkflowStepValidationDTO createWorkflowStepValidation(WorkflowStepValidationDTO workflowStepValidationDTO) {
        var savedValidationConfig = saveWorkflowStepValidation(workflowStepValidationDTO);

        log.info("validation step config successfully added {}", savedValidationConfig.getId());

        return savedValidationConfig;
    }

    @Transactional
    @Override
    public WorkflowStepValidationDTO updateWorkflowStepValidation(WorkflowStepValidationDTO workflowStepValidationDTO) {
        var updatedValidationConfig = saveWorkflowStepValidation(workflowStepValidationDTO);

        log.info("validation step config successfully updated {}", updatedValidationConfig.getId());

        return updatedValidationConfig;
    }

    @Override
    public WorkflowStepValidationDTO readWorkflowStepValidation(Long workflowStepValidationId) {
        var workflowStepValidation = workflowStepValidationRepository.findById(workflowStepValidationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("workflow step validation '%d' n'existe pas!", workflowStepValidationId)));

        log.info("workflow step validation successfully red {}", workflowStepValidation.getId());

        return workflowStepValidationMapper.asDto(workflowStepValidation);
    }

    @Override
    public void deleteWorkflowStepValidation(Long workflowStepValidationId) {
        try {
            workflowStepValidationUserRepository.deleteAllByWorkflowStepValidationId(workflowStepValidationId);
            workflowStepValidationRepository.deleteById(workflowStepValidationId);
            log.info("The workflow step validation id {} is deleted", workflowStepValidationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<WorkflowStepValidationDTO> readAllWorkflowStepValidation(Map<String, String> searchParams, int page, int size) {
        var searchBuilder = buildSearch(searchParams);
        var workflowStepValidationPage = workflowStepValidationRepository
                .findAll(searchBuilder, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
        return workflowStepValidationPage.map(workflowStepValidationMapper::asDto);
    }

    @Override
    public WorkflowStepValidationDTO readWorkflowUsers(Long profileId, Long workflowStepId) {
        var builder = new BooleanBuilder()
                .and(QWorkflowStepValidationUserEntity.workflowStepValidationUserEntity.workflowStepValidation.workflowStep.id.eq(workflowStepId));

        if(Objects.nonNull(profileId)) {
            var profile = profileRepository.findById(profileId).
                    orElseThrow(() -> new ResourceNotFoundException(String.format("Le profile '%d' est introuvable!"), profileId));

            var users = userRepository.findByProfile(profile);

            builder.and(QWorkflowStepValidationUserEntity.workflowStepValidationUserEntity.user.in(users));
        }

        var result = workflowStepValidationUserRepository.findAll(builder);

        var workflowStepValidationDTO = StreamSupport.stream(result.spliterator(), false)
                .map(WorkflowStepValidationUserEntity::getWorkflowStepValidation)
                .findFirst()
                .map(validation -> workflowStepValidationMapper.asDto(validation));

        if(workflowStepValidationDTO.isPresent()) {
            var us = StreamSupport.stream(result.spliterator(), false)
                    .map(WorkflowStepValidationUserEntity::getUser).collect(Collectors.toList());

            workflowStepValidationDTO.get().setUsers(workflowStepValidationMapper.toUserModel(us));

            return workflowStepValidationDTO.get();
        }

        return null;
    }

    private WorkflowStepValidationDTO saveWorkflowStepValidation(WorkflowStepValidationDTO workflowStepValidationDTO) {
        if(!workflowStepRepository.existsById(workflowStepValidationDTO.getWorkflowStepId())) {
            throw new IllegalArgumentException("Une étape du workflow est nécessaire pour la configuration des validations");
        }

        if(workflowStepValidationDTO.getUserIds().isEmpty()) {
            throw new RuntimeException("Veuillez configurer au moins un utilisateur pour la validation");
        }

        var validationConfig = workflowStepValidationMapper.asEntity(workflowStepValidationDTO);

        var exitstedValidation = workflowStepValidationRepository
                .findByWorkflowStepId(workflowStepValidationDTO.getWorkflowStepId());

        if(exitstedValidation.isPresent()) {
            validationConfig.setId(exitstedValidation.get().getId());
            // Remove all previous users configurations for this step validation
            workflowStepValidationUserRepository.deleteAllByWorkflowStepValidationId(exitstedValidation.get().getId());
        }

        var savedValidationConfig = workflowStepValidationRepository.save(validationConfig);

        var configUsers =  userMapper.mapIdsToEntities(workflowStepValidationDTO.getUserIds())
                .stream()
                .map(user -> WorkflowStepValidationUserEntity.builder()
                        .user(user)
                        .workflowStepValidation(savedValidationConfig)
                        .build()
                ).collect(Collectors.toList());

        var savedConfigUsers = workflowStepValidationUserRepository.saveAll(configUsers);

        var savedValidationConfigDTO = workflowStepValidationMapper.asDto(savedValidationConfig);

        savedValidationConfigDTO.setUsers(
                workflowStepValidationMapper
                        .toUserModel(savedConfigUsers.stream().map(WorkflowStepValidationUserEntity::getUser)
                        .collect(Collectors.toSet()))
        );

        return savedValidationConfigDTO;
    }

    private BooleanBuilder buildSearch(Map<String, String> searchParams) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(searchParams)) {
            var qWorkflowStepStepValidation = QWorkflowStepValidationEntity.workflowStepValidationEntity;
            if(searchParams.containsKey("id")) {
                booleanBuilder.and(qWorkflowStepStepValidation.id.eq(Long.parseLong(searchParams.get("id"))));
            }
            if(searchParams.containsKey("code")) {
                booleanBuilder.and(qWorkflowStepStepValidation.validationType.eq(WorkflowValidationType.valueOf(searchParams.get("validationType"))));
            }
            if(searchParams.containsKey("workflowStepId")) {
                booleanBuilder.and(qWorkflowStepStepValidation.workflowStep.id.eq(Long.parseLong(searchParams.get("workflowStepId"))));
            }
        }
        return booleanBuilder;
    }
}
