package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CessationFonctionEntity;
import com.webgram.dgpsn.models.CessationFonctionDTO;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CessationFonctionMapper implements EntityMapper<CessationFonctionDTO, CessationFonctionEntity> {
    @Mapping(target = "conge.id", source = "congeId")
    public abstract CessationFonctionEntity asEntity(CessationFonctionDTO dto);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(CessationFonctionDTO dto, @MappingTarget CessationFonctionEntity entity);



}
