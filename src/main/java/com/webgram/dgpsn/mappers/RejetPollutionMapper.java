package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.RejetPollutiontEntity;
import com.webgram.dgpsn.models.RejetPollutionDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class RejetPollutionMapper implements EntityMapper<RejetPollutionDTO, RejetPollutiontEntity> {

    @Mapping(target = "entreprise", source = "entrepriseId", qualifiedByName = "getEntreprise")
    public abstract RejetPollutiontEntity asEntity(RejetPollutionDTO rejetPollutionDTO);

    @Named("getEntreprise")
    public LabelEntity getEntreprise(Long entrepriseId) {
        if(Objects.nonNull(entrepriseId)) {
            return LabelEntity.builder().id(entrepriseId).build();
        }
        return null;
    }



}
