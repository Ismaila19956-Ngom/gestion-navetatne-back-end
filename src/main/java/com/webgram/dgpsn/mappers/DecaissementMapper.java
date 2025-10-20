package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.DecaissementEntity;
import com.webgram.dgpsn.models.DecaissementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DecaissementMapper extends EntityMapper<DecaissementDTO, DecaissementEntity> {

    @Mapping(source = "ordonnancement.id", target = "ordonnancementId")
    DecaissementDTO asDto(DecaissementEntity entity);

    @Mapping(source = "ordonnancementId", target = "ordonnancement.id")
    DecaissementEntity asEntity(DecaissementDTO dto);
}