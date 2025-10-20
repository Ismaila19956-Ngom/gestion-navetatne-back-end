package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.models.LabelDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, LabelEntity> {
}
