package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.ParticipantMissionDTO;
import com.webgram.dgpsn.repositories.ActorProjetRepository;
import com.webgram.dgpsn.repositories.RoleRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ParticipantMissionMapper implements EntityMapper<ParticipantMissionDTO, ParticipantMissionEntity> {

    @Autowired
    private ActorProjetRepository actorProjetRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Mapping(target = "assignment", source = "assignmentId", qualifiedByName = "getAssignment")
    @Mapping(target = "actor", source = "actorId", qualifiedByName = "getActor")
    @Mapping(target = "role", source = "roleId", qualifiedByName = "getRole")
    public abstract ParticipantMissionEntity asEntity(ParticipantMissionDTO participantMissionDTO);

    @Named("getAssignment")
    public AssignmentEntity getAssignment(Long assignmentId) {

        if (Objects.nonNull(assignmentId)) {
            return AssignmentEntity.builder().id(assignmentId).build();
        }
        return null;
    }

    @Named("getActor")
    public ActorProjetEntity getActor(Long actorId) {
        if (Objects.nonNull(actorId)) {
            return ActorProjetEntity.builder().id(actorId).build();
        }
        return null;
    }

    @Named("getRole")
    public RoleEntity getRole(Long roleId) {
        if (Objects.nonNull(roleId)) {
            return RoleEntity.builder().id(roleId).build();
        }
        return null;
    }

}

