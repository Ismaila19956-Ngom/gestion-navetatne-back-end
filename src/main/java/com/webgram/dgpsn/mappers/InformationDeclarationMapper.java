package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.InformationDeclarationEntity;
import com.webgram.dgpsn.models.InformationDeclarationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface InformationDeclarationMapper extends EntityMapper<InformationDeclarationDTO, InformationDeclarationEntity> {
    @Override
    @Mapping(target = "declaration.id", source = "declarationId")
    InformationDeclarationEntity asEntity(InformationDeclarationDTO dto);
}