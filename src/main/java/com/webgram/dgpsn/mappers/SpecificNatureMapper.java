package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.SpecificNatureEntity;
import com.webgram.dgpsn.models.SpecificNatureDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SpecificNatureMapper extends EntityMapper<SpecificNatureDTO, SpecificNatureEntity> {
    @Mapping(target = "nature.id", source = "natureId")
    SpecificNatureEntity asEntity(SpecificNatureDTO dto);

}
