package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.FournisseurEntity;
import com.webgram.dgpsn.models.FournisseurDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FournisseurMapper extends EntityMapper<FournisseurDTO, FournisseurEntity> {

    @Mapping(source = "categorieFournisseur.id", target = "categorieFournisseurId")
    FournisseurDTO asDto(FournisseurEntity entity);

    @Mapping(source = "categorieFournisseurId", target = "categorieFournisseur.id")
    FournisseurEntity asEntity(FournisseurDTO dto);
}