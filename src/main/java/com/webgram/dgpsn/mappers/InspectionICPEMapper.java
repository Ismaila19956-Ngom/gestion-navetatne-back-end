package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.InspectionICPEDTO;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.EtablissementClasseRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class InspectionICPEMapper implements EntityMapper<InspectionICPEDTO, InspectionICPEEntity> {

    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private EtablissementClasseRepository etablissementClasseRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Override
    @Mapping(target = "typeInspection", source = "typeInspectionId", qualifiedByName = "getTypeInspection")
    @Mapping(target = "etablissement", source = "etablissementId", qualifiedByName = "getEtablissement")
    @Mapping(target = "complianceLevel", source = "complianceLevelId", qualifiedByName = "getComplianceLevel")
    @Mapping(target = "environmentalRisk", source = "environmentalRiskId", qualifiedByName = "getEnvironmentalRisk")
    @Mapping(target = "teamMembers", source = "teamMemberIds", qualifiedByName = "getTeamMembers")
    @Mapping(target = "teamLead", source = "teamLeadId", qualifiedByName = "getTeamLead")
    @Mapping(target = "direction", source = "directionId", qualifiedByName = "getDirection")
    @Mapping(target = "programme", source = "programmeId", qualifiedByName = "getProgramme")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "activite", source = "activiteId", qualifiedByName = "getActivite")
    public abstract InspectionICPEEntity asEntity(InspectionICPEDTO dto);

    @Override
    public abstract InspectionICPEDTO asDto(InspectionICPEEntity entity);

    @Named("getDirection")
    public DirectionEntity getDirection(Long directionId) {
        if (Objects.isNull(directionId)) {
            return null;
        }
        return DirectionEntity.builder().id(directionId).build();
    }

    @Named("getProgramme")
    public ManagementUnitEntity getProgramme(Long programmeId) {
        if (Objects.isNull(programmeId)) {
            return null;
        }
        return ManagementUnitEntity.builder().id(programmeId).build();
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        if (Objects.isNull(projetId)) {
            return null;
        }
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getActivite")
    public ManagementUnitEntity getActivite(Long activiteId) {
        if (Objects.isNull(activiteId)) {
            return null;
        }
        return ManagementUnitEntity.builder().id(activiteId).build();
    }

    @Named("getTeamLead")
    public AgentEntity getTeamLead(Long teamLeadId) {
        if (Objects.isNull(teamLeadId)) {
            return null;
        }
        return AgentEntity.builder().id(teamLeadId).build();
    }

    @Named("getTypeInspection")
    public LabelEntity getTypeInspection(Long typeInspectionId) {
        if (Objects.isNull(typeInspectionId)) {
            return null;
        }
        return LabelEntity.builder().id(typeInspectionId).build();
    }

    @Named("getEtablissement")
    public EtablissementClasseEntity getEtablissement(Long etablissementId) {
        if (Objects.isNull(etablissementId)) {
            return null;
        }
        return EtablissementClasseEntity.builder().id(etablissementId).build();
    }

    @Named("getComplianceLevel")
    public LabelEntity getComplianceLevel(Long complianceLevelId) {
        if (Objects.isNull(complianceLevelId)) {
            return null;
        }
        return LabelEntity.builder().id(complianceLevelId).build();
    }

    @Named("getEnvironmentalRisk")
    public LabelEntity getEnvironmentalRisk(Long environmentalRiskId) {
        if (Objects.isNull(environmentalRiskId)) {
            return null;
        }
        return LabelEntity.builder().id(environmentalRiskId).build();
    }

    @Named("getTeamMembers")
    public Set<AgentEntity> getTeamMembers(Set<Long> teamMemberIds) {
        if (Objects.isNull(teamMemberIds) || teamMemberIds.isEmpty()) {
            return null;
        }
        return teamMemberIds.stream()
                .map(id -> AgentEntity.builder().id(id).build())
                .collect(Collectors.toSet());
    }
}