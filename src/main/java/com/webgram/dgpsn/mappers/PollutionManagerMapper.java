package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.PollutiontManagerEntity;
import com.webgram.dgpsn.models.PollutionManagerDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class PollutionManagerMapper implements EntityMapper<PollutionManagerDTO, PollutiontManagerEntity> {

    @Mapping(target = "plastique", source = "plastiqueId", qualifiedByName = "getPlastique")
    @Mapping(target = "produit", source = "produitId", qualifiedByName = "getProduit")
    @Mapping(target = "dechet", source = "dechetId", qualifiedByName = "getDechet")
    @Mapping(target = "origine", source = "origineId", qualifiedByName = "getOrigine")
    @Mapping(target = "destination", source = "destinationId", qualifiedByName = "getDestination")
    @Mapping(target = "methodeElimination", source = "methodeEliminationId", qualifiedByName = "getMethodeElimination")
    @Mapping(target = "siteElimination", source = "siteEliminationId", qualifiedByName = "getSiteElimination")
    public abstract PollutiontManagerEntity asEntity(PollutionManagerDTO pollutionManagerDTO);

    @Named("getMethodeElimination")
    public LabelEntity getMethodeElimination(Long methodeEliminationId) {
        if(Objects.nonNull(methodeEliminationId)) {
            return LabelEntity.builder().id(methodeEliminationId).build();
        }
        return null;
    }
    @Named("getSiteElimination")
    public LabelEntity getSiteElimination(Long siteEliminationId) {
        if(Objects.nonNull(siteEliminationId)) {
            return LabelEntity.builder().id(siteEliminationId).build();
        }
        return null;
    }
    @Named("getPlastique")
    public LabelEntity getPlastique(Long plastiqueId) {
        if(Objects.nonNull(plastiqueId)) {
            return LabelEntity.builder().id(plastiqueId).build();
        }
        return null;
    }

    @Named("getDestination")
    public LabelEntity getDestination(Long destinationId) {
        if(Objects.nonNull(destinationId)) {
            return LabelEntity.builder().id(destinationId).build();
        }
        return null;
    }
    @Named("getDechet")
    public LabelEntity getDechet(Long dechetId) {
        if(Objects.nonNull(dechetId)) {
            return LabelEntity.builder().id(dechetId).build();
        }
        return null;
    }
    @Named("getOrigine")
    public LabelEntity getOrigine(Long plastiqueId) {
        if(Objects.nonNull(plastiqueId)) {
            return LabelEntity.builder().id(plastiqueId).build();
        }
        return null;
    }


    @Named("getProduit")
    public LabelEntity getProduit(Long produitId) {
        if(Objects.nonNull(produitId)) {
            return LabelEntity.builder().id(produitId).build();
        }
        return null;
    }



}
