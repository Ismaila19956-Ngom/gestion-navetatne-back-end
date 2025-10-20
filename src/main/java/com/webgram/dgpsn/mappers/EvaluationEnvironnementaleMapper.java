package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.EvaluationEnvironnementaleDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class EvaluationEnvironnementaleMapper implements EntityMapper<EvaluationEnvironnementaleDTO, EvaluationEnvironnementaleEntity> {

    @Override
    @Mapping(target = "programme", source = "programmeId", qualifiedByName = "getManagementUnit")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getManagementUnit")
    @Mapping(target = "activite", source = "activiteId", qualifiedByName = "getManagementUnit")
    @Mapping(target = "direction", source = "directionId", qualifiedByName = "getDirection")
    @Mapping(target = "promoteur", source = "promoteurId", qualifiedByName = "getPromoteur")
    @Mapping(target = "activitesDescription", source = "activitesDescription")
    public abstract EvaluationEnvironnementaleEntity asEntity(EvaluationEnvironnementaleDTO dto);

    @Override
    @Mapping(target = "programmeId", source = "programme.id")
    @Mapping(target = "projetId", source = "projet.id")
    @Mapping(target = "activiteId", source = "activite.id")
    @Mapping(target = "directionId", source = "direction.id")
    @Mapping(target = "promoteurId", source = "promoteur.id")
    @Mapping(target = "activitesDescription", source = "activitesDescription")
    public abstract EvaluationEnvironnementaleDTO asDto(EvaluationEnvironnementaleEntity entity);

    @Named("getManagementUnit")
    public ManagementUnitEntity getManagementUnit(Long programmeId) {
        if (Objects.isNull(programmeId)) {
            return null;
        }
        return ManagementUnitEntity.builder().id(programmeId).build();
    }

    @Named("getDirection")
    public DirectionEntity getDirection(Long directionId) {
        if (Objects.isNull(directionId)) {
            return null;
        }
        return DirectionEntity.builder().id(directionId).build();
    }

    @Named("getPromoteur")
    public PromoteurEntity getPromoteur(Long promoteurId) {
        if (Objects.isNull(promoteurId)) {
            return null;
        }
        return PromoteurEntity.builder().id(promoteurId).build();
    }
}