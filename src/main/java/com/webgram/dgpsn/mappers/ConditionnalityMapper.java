package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ConditionnalityEntity;
import com.webgram.dgpsn.models.ConditionnalityDTO;
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class





ConditionnalityMapper implements EntityMapper<ConditionnalityDTO, ConditionnalityEntity> {

}
