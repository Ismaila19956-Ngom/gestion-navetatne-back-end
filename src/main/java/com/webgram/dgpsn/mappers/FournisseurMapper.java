package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.FournisseurEntity;
import com.webgram.dgpsn.models.FournisseurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, FournisseurEntity> {

    FournisseurEntity asEntity(FournisseurDTO dto);
    FournisseurDTO asDto(FournisseurEntity entity);
}