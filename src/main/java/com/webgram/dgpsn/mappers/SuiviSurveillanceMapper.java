package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.PromoteurEntity;
import com.webgram.dgpsn.entities.SuiviSurveillanceEntity;
import com.webgram.dgpsn.models.SuiviSurveillanceDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class SuiviSurveillanceMapper implements EntityMapper<SuiviSurveillanceDTO, SuiviSurveillanceEntity> {

    @Override
    @Mapping(source = "promoteurId", target = "promoteur", qualifiedByName = "getPromoteurById")
    public abstract SuiviSurveillanceEntity asEntity(SuiviSurveillanceDTO dto);

    @Override
    @Mapping(source = "promoteur.id", target = "promoteurId")
    public abstract SuiviSurveillanceDTO asDto(SuiviSurveillanceEntity entity);
    
    @Named("getPromoteurById")
    public PromoteurEntity getPromoteurById(Long id) {
        if (id == null) return null;
        return PromoteurEntity.builder().id(id).build();
    }
}