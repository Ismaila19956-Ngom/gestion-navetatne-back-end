package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.EtatFinancierEntity;;
import com.webgram.dgpsn.models.EtatFinancierDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class EtatFinancierMapper implements EntityMapper<EtatFinancierDto, EtatFinancierEntity> {

    @Mapping(target = "entreprise.id", source = "entrepriseId")
    public abstract EtatFinancierEntity asEntity(EtatFinancierDto etatFinancierDto);

    public abstract EtatFinancierDto asDto(EtatFinancierEntity etatFinancierEntity);
}