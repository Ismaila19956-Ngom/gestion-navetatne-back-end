package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.AlerteEntity;
import com.webgram.dgpsn.models.AlerteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AlerteMapper extends EntityMapper<AlerteDTO, AlerteEntity> {

}
