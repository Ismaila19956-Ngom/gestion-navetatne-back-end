package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.FicheVisiteDTO;
import com.webgram.dgpsn.models.MembreMissionDTO;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FicheVisiteMapper implements EntityMapper<FicheVisiteDTO, FicheVisiteEntity> {

    @Mapping(target = "region", source = "regionId", qualifiedByName = "getRegion")
    @Mapping(target = "departement", source = "departementId", qualifiedByName = "getDepartement")
    @Mapping(target = "arrondissement", source = "arrondissementId", qualifiedByName = "getArrondissement")
    @Mapping(target = "commune", source = "communeId", qualifiedByName = "getCommune")
    @Mapping(source = "membres", target = "membres")
   public abstract FicheVisiteEntity asEntity(FicheVisiteDTO dto);


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


    @Named("getArrondissement")
    public CadreLogiqueEntity getArrondissement(Long arrondissementId) {
        if (Objects.isNull(arrondissementId)) {
            return null;
        }
        return CadreLogiqueEntity.builder().id(arrondissementId).build();
    }


    @Named("getCommune")
    public CadreLogiqueEntity getCommune(Long communeId) {
        if (Objects.isNull(communeId)) {
            return null;
        }
        return CadreLogiqueEntity.builder().id(communeId).build();
    }



    @Mapping(source = "membres", target = "membres")
    public abstract FicheVisiteDTO asDto(FicheVisiteEntity entity);

    public abstract MembreMissionDTO toMembreDTO(MembreMissiomEntity membreEntity);
    public abstract MembreMissiomEntity toMembreEntity(MembreMissionDTO membreDTO);
    public abstract List<MembreMissionDTO> toMembreDTOList(List<MembreEntity> membreEntities);
    public abstract List<MembreEntity> toMembreEntityList(List<MembreMissionDTO> membreDTOs);
}