package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ParcRoulantEntity;
import com.webgram.dgpsn.models.ParcRoulantDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ParcRoulantMapper extends EntityMapper<ParcRoulantDTO, ParcRoulantEntity> {

    @Override
    @Mapping(target = "marque.id", source = "marqueId")
    @Mapping(target = "localisation.id", source = "localisationId")
    @Mapping(target = "etat.id", source = "etatId")
    ParcRoulantEntity asEntity(ParcRoulantDTO dto);

    ParcRoulantDTO asDto(ParcRoulantEntity entity);
}