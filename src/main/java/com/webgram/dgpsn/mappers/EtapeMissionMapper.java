package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.EtapeMissionDTO;
import com.webgram.dgpsn.repositories.EtapeMissionRepository;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")

public abstract class EtapeMissionMapper implements EntityMapper<EtapeMissionDTO, EtapeMissionEntity> {

@Autowired
 private EtapeMissionRepository etapeMissionReository;
    @Override
    @Mapping(target = "status", source = "statusId", qualifiedByName = "getStatus")
    @Mapping(target = "responsable", source = "responsableId", qualifiedByName = "getResponsable")
    @Mapping(target = "assignment", source = "assignmentId", qualifiedByName = "getAssignment")
    public abstract EtapeMissionEntity asEntity(EtapeMissionDTO dto);

    @Named("getStatus")
    public LabelEntity getStatus(Long statusId) {
        if(Objects.nonNull(statusId)) {
            return LabelEntity.builder().id(statusId).build();
        }
        return null;
    }

    @Named("getResponsable")
    public ParticipantMissionEntity getResponsable(Long responsableId) {
        return ParticipantMissionEntity.builder().id(responsableId).build();
    }

    @Named("getAssignment")
    public AssignmentEntity getAssignment(Long assignmentId) {
        return AssignmentEntity.builder().id(assignmentId).build();
    }
}
