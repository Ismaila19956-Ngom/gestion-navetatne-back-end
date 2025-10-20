package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.StationEntity;
import com.webgram.dgpsn.models.StationDTO;
import com.webgram.dgpsn.repositories.CadreLogiqueRepository;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class StationMapper implements EntityMapper<StationDTO, StationEntity> {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private CadreLogiqueRepository cadreLogiqueRepository;

    @Override
    @Mapping(target = "type", source = "typeId", qualifiedByName = "getType")
    @Mapping(target = "region", source = "regionId", qualifiedByName = "getRegion")
    @Mapping(target = "departement", source = "departementId", qualifiedByName = "getDepartement")
    public abstract StationEntity asEntity(StationDTO dto);

    @Override
    public abstract StationDTO asDto(StationEntity entity);

    @Named("getType")
    public LabelEntity getType(Long typeId) {
        if (Objects.isNull(typeId)) {
            return null;
        }
        return LabelEntity.builder().id(typeId).build();
    }

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
}