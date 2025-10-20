package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.models.AlerteDTO;
import com.webgram.dgpsn.entities.AlerteEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AlerteMapper extends EntityMapper<AlerteDTO, AlerteEntity> {

}
