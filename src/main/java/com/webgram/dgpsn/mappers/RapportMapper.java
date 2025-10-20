package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.RapportEntity;
import com.webgram.dgpsn.models.RapportDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RapportMapper extends EntityMapper<RapportDTO, RapportEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    RapportEntity asEntity(RapportDTO dto);
}
