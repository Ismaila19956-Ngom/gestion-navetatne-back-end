package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.MilieuxPollutiontEntity;
import com.webgram.dgpsn.models.MilieuxPollutionDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class MilieuxPollutionMapper implements EntityMapper<MilieuxPollutionDTO, MilieuxPollutiontEntity> {

    @Mapping(target = "pointPrelevement", source = "pointPrelevementId", qualifiedByName = "getPointPrelevement")
    @Mapping(target = "condMeteo", source = "condMeteoId", qualifiedByName = "getCondMeteo")

    public abstract MilieuxPollutiontEntity asEntity(MilieuxPollutionDTO milieuxPollutionDTO);

    @Named("getPointPrelevement")
    public LabelEntity getPointPrelevement(Long pointPrelevementId) {
        if(Objects.nonNull(pointPrelevementId)) {
            return LabelEntity.builder().id(pointPrelevementId).build();
        }
        return null;
    }

    @Named("getCondMeteo")
    public LabelEntity getCondMeteo(Long condMeteoId) {
        if(Objects.nonNull(condMeteoId)) {
            return LabelEntity.builder().id(condMeteoId).build();
        }
        return null;
    }

//   public abstract MilieuxPollutionDTO asDto(MilieuxPollutiontEntity milieuxPollutiontEntity);


}
