package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.FicheRenseignementEntity;
import com.webgram.dgpsn.models.FicheRenseignementDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FicheRenseignementMapper implements EntityMapper<FicheRenseignementDTO, FicheRenseignementEntity> {
    @Mapping(target = "region", source = "regionId", qualifiedByName = "getRegion")
    @Mapping(target = "departement", source = "departementId", qualifiedByName = "getDepartement")
    @Mapping(target = "commune", source = "communeId", qualifiedByName = "getCommune")
    public abstract FicheRenseignementEntity asEntity(FicheRenseignementDTO dto);

    @Named("getRegion")
    public CadreLogiqueEntity getRegion(Long regionId) {
        if (Objects.isNull(regionId)) {
            return null;
        }
        return CadreLogiqueEntity.builder().id(regionId).build();
    }

    @Named("getDepartement")
    public CadreLogiqueEntity getDepartement(Long departementId) {
        if (Objects.isNull(departementId)) {
            return null;
        }
        return CadreLogiqueEntity.builder().id(departementId).build();
    }

    @Named("getCommune")
    public CadreLogiqueEntity getCommune(Long communeId) {
        if (Objects.isNull(communeId)) {
            return null;
        }
        return CadreLogiqueEntity.builder().id(communeId).build();
    }

    public abstract FicheRenseignementDTO asDto(FicheRenseignementEntity entity);
}