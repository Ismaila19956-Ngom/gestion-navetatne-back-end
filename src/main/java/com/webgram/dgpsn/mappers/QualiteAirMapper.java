package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.QualiteAirEntity;
import com.webgram.dgpsn.entities.StationEntity;
import com.webgram.dgpsn.models.QualiteAirDTO;
import com.webgram.dgpsn.repositories.LabelRepository;
import com.webgram.dgpsn.repositories.StationRepository;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class QualiteAirMapper implements EntityMapper<QualiteAirDTO, QualiteAirEntity> {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private StationRepository stationRepository;

    @Override
    @Mapping(target = "station", source = "stationId", qualifiedByName = "getStation")
    @Mapping(target = "mainPollutant", source = "mainPollutantId", qualifiedByName = "getMainPollutant")
    @Mapping(target = "pollutantMeasurements", source = "pollutantMeasurements", qualifiedByName = "mapPollutantMeasurements")
    public abstract QualiteAirEntity asEntity(QualiteAirDTO dto);

    @Override
    public abstract QualiteAirDTO asDto(QualiteAirEntity entity);

    @Named("getStation")
    public StationEntity getStation(Long stationId) {
        if (Objects.isNull(stationId)) {
            return null;
        }
        return StationEntity.builder().id(stationId).build();
    }

    @Named("getMainPollutant")
    public LabelEntity getMainPollutant(Long mainPollutantId) {
        if (Objects.isNull(mainPollutantId)) {
            return null;
        }
        return LabelEntity.builder().id(mainPollutantId).build();
    }

    @Named("mapPollutantMeasurements")
    public QualiteAirEntity.PollutantMeasurement mapPollutantMeasurement(QualiteAirDTO.PollutantMeasurement dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return QualiteAirEntity.PollutantMeasurement.builder()
                .pollutant(dto.getPollutantId() != null ? LabelEntity.builder().id(dto.getPollutantId()).build() : null)
                .concentration(dto.getConcentration())
                .unit(dto.getUnitId() != null ? LabelEntity.builder().id(dto.getUnitId()).build() : null)
                .build();
    }
}