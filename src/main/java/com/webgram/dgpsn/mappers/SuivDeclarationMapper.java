package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.SuivDeclarationEntity;
import com.webgram.dgpsn.models.SuivDeclarationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SuivDeclarationMapper extends EntityMapper<SuivDeclarationDTO, SuivDeclarationEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    SuivDeclarationEntity asEntity(SuivDeclarationDTO dto);
}