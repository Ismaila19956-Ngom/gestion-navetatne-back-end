package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CessationFonctionEntity;
import com.webgram.dgpsn.entities.CessationPerduEntity;
import com.webgram.dgpsn.models.CessationFonctionDTO;
import com.webgram.dgpsn.models.CessationPerduDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CessationPerduMapper implements EntityMapper<CessationPerduDTO, CessationPerduEntity> {
    @Mapping(target = "conge.id", source = "congeId")
    public abstract CessationPerduEntity asEntity(CessationPerduDTO dto);

    @Mapping(target = "id", ignore = true)
    public abstract void updateEntityFromDto(CessationFonctionDTO dto, @MappingTarget CessationFonctionEntity entity);



}
