package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.models.StatusDTO;
import com.webgram.dgpsn.entities.StatusEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StatusMapper extends EntityMapper<StatusDTO, StatusEntity> {
    @Override
    StatusDTO asDto(StatusEntity status);

    @Override
    StatusEntity asEntity(StatusDTO statusDTO);


}
