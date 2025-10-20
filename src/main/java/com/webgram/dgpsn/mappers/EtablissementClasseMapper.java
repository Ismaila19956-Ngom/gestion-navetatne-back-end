package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.EtablissementClasseEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.models.EtablissementClasseDTO;
import com.webgram.dgpsn.repositories.LabelRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class EtablissementClasseMapper implements EntityMapper<EtablissementClasseDTO, EtablissementClasseEntity> {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    @Mapping(target = "typeEtablissement", source = "typeEtablissementId", qualifiedByName = "getTypeEtablissement")
    @Mapping(target = "region", source = "regionId", qualifiedByName = "getRegion")
    @Mapping(target = "departement", source = "departementId", qualifiedByName = "getDepartement")
    @Mapping(target = "categoryICPE", source = "categoryICPEId", qualifiedByName = "getCategoryICPE")
    public abstract EtablissementClasseEntity asEntity(EtablissementClasseDTO dto);

    @Override
    public abstract EtablissementClasseDTO asDto(EtablissementClasseEntity entity);

    @Named("getTypeEtablissement")
    public LabelEntity getTypeEtablissement(Long typeEtablissementId) {
        if (Objects.isNull(typeEtablissementId)) {
            return null;
        }
        return LabelEntity.builder().id(typeEtablissementId).build();
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

    @Named("getCategoryICPE")
    public LabelEntity getCategoryICPE(Long categoryICPEId) {
        if (Objects.isNull(categoryICPEId)) {
            return null;
        }
        return LabelEntity.builder().id(categoryICPEId).build();
    }
}