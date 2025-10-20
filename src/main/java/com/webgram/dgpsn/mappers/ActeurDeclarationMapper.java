package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ActeurDeclarationEntity;
import com.webgram.dgpsn.models.ActeurDeclarationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ActeurDeclarationMapper extends EntityMapper<ActeurDeclarationDTO, ActeurDeclarationEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    ActeurDeclarationEntity asEntity(ActeurDeclarationDTO dto);
}