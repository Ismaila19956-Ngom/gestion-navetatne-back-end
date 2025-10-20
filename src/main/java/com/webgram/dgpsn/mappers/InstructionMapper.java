package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CadreLogiqueEntity;
import com.webgram.dgpsn.entities.InstructionEntity;
import com.webgram.dgpsn.entities.PromoteurEntity;
import com.webgram.dgpsn.models.InstructionDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class InstructionMapper implements EntityMapper<InstructionDTO, InstructionEntity> {

    @Override
    @Mapping(source = "promoteurId", target = "promoteur", qualifiedByName = "getPromoteurById")
    @Mapping(source = "regionId", target = "region", qualifiedByName = "getRegionById")
    public abstract InstructionEntity asEntity(InstructionDTO dto);

    @Override
    @Mapping(source = "promoteur.id", target = "promoteurId")
    @Mapping(source = "region.id", target = "regionId")
    public abstract InstructionDTO asDto(InstructionEntity entity);
    
    @Named("getPromoteurById")
    public PromoteurEntity getPromoteurById(Long id) {
        if (id == null) return null;
        return PromoteurEntity.builder().id(id).build();
    }
    
    @Named("getRegionById")
    public CadreLogiqueEntity getRegionById(Long id) {
        if (id == null) return null;
        return CadreLogiqueEntity.builder().id(id).build();
    }
}