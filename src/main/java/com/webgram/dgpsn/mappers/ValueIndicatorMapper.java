package com.webgram.dgpsn.mappers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.IndicatorProjetEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.ValueIndicatorEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.ValueIndicatorDTO;
import com.webgram.dgpsn.repositories.IndicatorProjetRepository;
import com.webgram.dgpsn.services.modelExcel.ValueIndicatorExcelDTO;

import java.text.MessageFormat;
import java.util.Objects;

@Slf4j
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ValueIndicatorMapper implements EntityMapper<ValueIndicatorDTO, ValueIndicatorEntity> {

    @Autowired
    private IndicatorProjetRepository indicatorProjetRepository;

    @Mapping(target = "indicatorProjet", source = "indicatorProjetId", qualifiedByName = "getIndicatorProjetById")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProject")
    @Mapping(target = "activity", source = "activityId", qualifiedByName = "getActivity")
    public abstract ValueIndicatorEntity asEntity(ValueIndicatorDTO dto);


//    @Mapping(target = "period", source = "periodCode", qualifiedByName = "getPeriodByCode")
    @Mapping(target = "indicatorProjet", source = "dto", qualifiedByName = "getIndicatorProjetByCode")
    @Mapping(target = "projet", source = "projectId", qualifiedByName = "getProject")
    public abstract ValueIndicatorEntity asEntity(ValueIndicatorExcelDTO dto);

//    @Mapping(source = "period.code", target = "periodCode")
    @Mapping(source = "indicatorProjet.indicator.code", target = "indicatorCode")
    public abstract ValueIndicatorExcelDTO asExcelDto(ValueIndicatorEntity entity);

    @Named("getActivity")
    public ManagementUnitEntity getActivity(Long activityId) {
        if(Objects.nonNull(activityId)) {
            return ManagementUnitEntity.builder().id(activityId).build();
        }
        return null;
    }

//    @Named("getPeriodByCode")
//    public PeriodEntity getPriod(String periodCode) {
//        if(StringUtils.isNotEmpty(periodCode)){
//            log.info(periodCode );
//            var period = periodRepository.findByCode(periodCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code periode n'existe pas {}", periodCode)));
//            return period;
//
//        }
//        return null;
//    }

    @Named("getIndicatorProjetById")
    public IndicatorProjetEntity getIndicatorProjetById(Long indicatorProjectId) {
        if(Objects.nonNull(indicatorProjectId)){
            return indicatorProjetRepository.findById(indicatorProjectId)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code indicateur {} n'existe pas pour ce projet", indicatorProjectId)));
        }
        return null;
    }

    @Named("getIndicatorProjetByCode")
    public IndicatorProjetEntity getIndicatorProjet(ValueIndicatorExcelDTO dto) {
        if(StringUtils.isNotEmpty(dto.getIndicatorCode())){
            var indicatorProjet = indicatorProjetRepository.findByProjetAndIndicatorCode(ManagementUnitEntity.builder().id(dto.getProjectId()).build(),dto.getIndicatorCode())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code indicateur n'existe pas pour ce projet", dto.getIndicatorCode())));
            return indicatorProjet;

        }
        return null;
    }

    @Named("getProject")
    public ManagementUnitEntity getProjet(Long projectId) {
        if(Objects.nonNull(projectId)) {
            return ManagementUnitEntity.builder().id(projectId).build();
        }
        return null;
    }

}
