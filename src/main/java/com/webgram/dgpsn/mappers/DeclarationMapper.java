package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.DeclarationEntity;
import com.webgram.dgpsn.models.DeclarationDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DeclarationMapper extends EntityMapper<DeclarationDTO, DeclarationEntity> {
}
