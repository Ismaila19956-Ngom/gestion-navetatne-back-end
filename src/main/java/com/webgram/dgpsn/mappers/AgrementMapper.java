package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.AgrementEntity;
import com.webgram.dgpsn.entities.PromoteurEntity;
import com.webgram.dgpsn.models.AgrementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class AgrementMapper implements EntityMapper<AgrementDTO, AgrementEntity> {

    @Override
    @Mapping(source = "promoteurId", target = "promoteur", qualifiedByName = "getPromoteurById")
    public abstract AgrementEntity asEntity(AgrementDTO dto);

    @Override
    @Mapping(source = "promoteur.id", target = "promoteurId")
    public abstract AgrementDTO asDto(AgrementEntity entity);
    
    @Named("getPromoteurById")
    public PromoteurEntity getPromoteurById(Long id) {
        if (id == null) return null;
        return PromoteurEntity.builder().id(id).build();
    }
}