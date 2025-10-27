package com.webgram.dgpsn.mappers;
import com.webgram.dgpsn.entities.LabelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.models.PlanComptableElementDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlanComptableElementMapper extends EntityMapper<PlanComptableElementDTO, PlanComptableElementEntity> {
    @Override
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "getParent")
    PlanComptableElementEntity asEntity(PlanComptableElementDTO dto);
    @Override
    PlanComptableElementDTO asDto(PlanComptableElementEntity entity);


    @Named("getParent")
    public default PlanComptableElementEntity getParent(Long parentId) {
        if(Objects.nonNull(parentId)) {
            return PlanComptableElementEntity.builder().id(parentId).build();
        }
        return null;
    }

}