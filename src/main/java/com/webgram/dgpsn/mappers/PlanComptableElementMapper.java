package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.models.PlanComptableElementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlanComptableElementMapper extends EntityMapper<PlanComptableElementDTO, PlanComptableElementEntity> {

    @Override
    @Mapping(source = "planId", target = "plan", qualifiedByName = "mapPlanIdToEntity")
    PlanComptableElementEntity asEntity(PlanComptableElementDTO dto);


    @Override
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "plan", target = "planDetails", qualifiedByName = "mapPlanToSimpleDto")
    PlanComptableElementDTO asDto(PlanComptableElementEntity entity);


    @Named("mapPlanIdToEntity")
    default PlanComptableElementEntity mapPlanIdToEntity(Long planId) {
        if (planId == null) {
            return null;
        }
        return PlanComptableElementEntity.builder()
                .id(planId)
                .build();
    }


    @Named("mapPlanToSimpleDto")
    default PlanComptableElementDTO mapPlanToSimpleDto(PlanComptableElementEntity entity) {
        if (entity == null) {
            return null;
        }
        return PlanComptableElementDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .libelle(entity.getLibelle())
                .type(entity.getType())
                .build();
    }
}