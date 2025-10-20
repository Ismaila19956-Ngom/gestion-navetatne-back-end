package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CompteRenduEntity;
import com.webgram.dgpsn.models.CompteRenduDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CompteRenduMapper extends EntityMapper<CompteRenduDTO, CompteRenduEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    CompteRenduEntity asEntity(CompteRenduDTO dto);
}
