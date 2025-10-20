package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.models.PromoteurDTO;
import com.webgram.dgpsn.entities.PromoteurEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PromoteurMapper extends EntityMapper<PromoteurDTO, PromoteurEntity> {



}
