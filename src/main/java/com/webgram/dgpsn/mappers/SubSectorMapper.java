package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.SubSectorEntity;
import com.webgram.dgpsn.models.SubSectorDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubSectorMapper extends EntityMapper<SubSectorDTO, SubSectorEntity> {

    @Mapping(target = "sector.id", source = "sectorId")
    SubSectorEntity asEntity(SubSectorDTO dto);
}
