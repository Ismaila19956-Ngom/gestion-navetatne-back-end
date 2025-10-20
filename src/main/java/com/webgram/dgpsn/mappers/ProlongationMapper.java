package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.ProlongationEntity;
import com.webgram.dgpsn.models.ProlongationDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProlongationMapper extends EntityMapper<ProlongationDTO, ProlongationEntity> {

    @Mapping(target = "funding.id", source = "fundingId")
    @Override
    ProlongationEntity asEntity(ProlongationDTO prolongationDTO);

}
