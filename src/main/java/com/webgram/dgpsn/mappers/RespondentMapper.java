package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.RespondentEntity;
import com.webgram.dgpsn.models.RespondentDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RespondentMapper extends EntityMapper<RespondentDTO, RespondentEntity> {

    @Mapping(target = "funding.id", source = "fundingId")
    @Override
    RespondentEntity asEntity(RespondentDTO respondentDTO);

}
