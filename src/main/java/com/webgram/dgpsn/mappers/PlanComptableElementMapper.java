package com.webgram.dgpsn.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.models.PlanComptableElementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PlanComptableElementMapper extends EntityMapper<PlanComptableElementDTO, PlanComptableElementEntity> {
    @Override
    PlanComptableElementEntity asEntity(PlanComptableElementDTO dto);
    @Override
    PlanComptableElementDTO asDto(PlanComptableElementEntity entity);
}
