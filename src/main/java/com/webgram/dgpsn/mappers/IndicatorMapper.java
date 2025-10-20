package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.IndicatorDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IndicatorMapper extends EntityMapper<IndicatorDTO, IndicatorEntity> {

    @Override
    @Mapping(target = "unit", source = "unitId", qualifiedByName = "getUnit")
    @Mapping(target = "indicatorType.id", source = "indicatorTypeId")
    IndicatorEntity asEntity(IndicatorDTO dto);

    @Named("getUnit")
    default LabelEntity getUnit(Long unitId) {
        return LabelEntity.builder().id(unitId).build();
    }
}
