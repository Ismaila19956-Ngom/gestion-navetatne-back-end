package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.GeographicalLocationEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.GeographicalLocationDTO;
;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GeographicalLocationMapper extends EntityMapper<GeographicalLocationDTO, GeographicalLocationEntity> {

    @Override
    @Mapping(target = "cadreLogique", source = "cadreLogiqueId", qualifiedByName = "getCadreLogique")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    GeographicalLocationEntity asEntity(GeographicalLocationDTO dto);

    @Named("getCadreLogique")
    default CadreLogiqueEntity getCadreLogique(Long cadreLogiqueId) {
        return CadreLogiqueEntity.builder().build().builder().id(cadreLogiqueId).build();
    }

    @Named("getProjet")
    default ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }
}
