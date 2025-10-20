package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.InterventionEntity;
import com.webgram.dgpsn.models.InterventionDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InterventionMapper extends EntityMapper<InterventionDTO, InterventionEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    InterventionEntity asEntity(InterventionDTO dto);
}
