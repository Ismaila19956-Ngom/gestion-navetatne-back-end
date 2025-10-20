package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.RepertoireDeclarationEntity;
import com.webgram.dgpsn.models.RepertoireDeclarationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RepertoireDeclarationMapper extends EntityMapper<RepertoireDeclarationDTO, RepertoireDeclarationEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    RepertoireDeclarationEntity asEntity(RepertoireDeclarationDTO dto);
}