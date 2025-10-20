package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.UserEntity;
import com.webgram.dgpsn.entities.WorkflowStepValidationEntity;
import com.webgram.dgpsn.models.WorkflowStepValidationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class WorkflowStepValidationMapper implements EntityMapper<WorkflowStepValidationDTO, WorkflowStepValidationEntity> {
    @Autowired
    WorkflowStepMapper workflowStepMapper;
    @Autowired
    ProfileMapper profileMapper;

    @Override
    @Mapping(target = "workflowStep.id", source = "workflowStepId")
    public abstract WorkflowStepValidationEntity asEntity(WorkflowStepValidationDTO dto);

    public Set<WorkflowStepValidationDTO.UserModel> toUserModel(Collection<UserEntity> users) {
        return users.stream()
                .map(user -> WorkflowStepValidationDTO
                        .UserModel
                        .builder()
                        .id(user.getId())
                        .prenom(user.getAgent().getPrenom())
                        .nom(user.getAgent().getNom())
                        .profile(profileMapper.asDto(user.getProfile()))
                        .build())
                .collect(Collectors.toSet());
    }
}
