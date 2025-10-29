package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.TacheEntity;
import com.webgram.dgpsn.entities.ValueIndicatorEntity;
import com.webgram.dgpsn.models.TacheDto;

import com.webgram.dgpsn.services.modelExcel.TacheExcelDTO;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class TacheMapper implements EntityMapper<TacheDto, TacheEntity> {

    @Mapping(target = "activite", source = "activiteId", qualifiedByName = "getActivite")
    @Mapping(target = "indicator", source = "indicatorId", qualifiedByName = "getIndicator")
    @Mapping(target = "semaines", source = "semaines", qualifiedByName = "listToString")
    public abstract TacheEntity asEntity(TacheDto tacheDto);

    @Mapping(target = "semaines", source = "semaines", qualifiedByName = "stringToList")
    public abstract TacheDto asDto(TacheEntity tacheEntity);

    @Mapping(target = "libelleActivite", source = "activite.name")
    @Mapping(target = "statut", source = "statut", qualifiedByName = "statutToString")
    public abstract TacheExcelDTO asExcelDto(TacheEntity entity);

    @Named("getActivite")
    public ManagementUnitEntity getActivite(Long activiteId) {
        if (Objects.nonNull(activiteId)) {
            return ManagementUnitEntity.builder().id(activiteId).build();
        }
        return null;
    }

    @Named("getIndicator")
    public ValueIndicatorEntity getIndicator(Long indicatorId) {
        if (Objects.nonNull(indicatorId)) {
            return ValueIndicatorEntity.builder().id(indicatorId).build();
        }
        return null;
    }

    @Named("listToString")
    public String listToString(List<Integer> semaines) {
        if (Objects.nonNull(semaines) && !semaines.isEmpty()) {
            return semaines.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
        }
        return null;
    }

    @Named("stringToList")
    public List<Integer> stringToList(String semaines) {
        if (Objects.nonNull(semaines) && !semaines.isEmpty()) {
            return Arrays.stream(semaines.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Named("statutToString")
    public String statutToString(TacheEntity.StatutTache statut) {
        if (Objects.nonNull(statut)) {
            return statut.name();
        }
        return null;
    }
}
