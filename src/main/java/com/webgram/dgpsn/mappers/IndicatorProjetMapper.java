package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.IndicatorProjetDTO;
import com.webgram.dgpsn.repositories.IndicatorRepository;
import com.webgram.dgpsn.services.modelExcel.IndicatorProjectExcelDTO;

import java.text.MessageFormat;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class IndicatorProjetMapper implements EntityMapper<IndicatorProjetDTO, IndicatorProjetEntity> {

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Override
    @Mapping(target = "indicator", source = "indicatorId", qualifiedByName = "getIndicator")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    public abstract IndicatorProjetEntity asEntity(IndicatorProjetDTO dto);

    @Named("getIndicator")
    public IndicatorEntity getIndicator(Long indicatorId) {
        return IndicatorEntity.builder().id(indicatorId).build();
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }


//    @Mapping(target = "indicator", source = "codeIndicator", qualifiedByName = "getIndicatorByCode")
//    @Mapping(target = "periodicity", source = "codePeriodicity", qualifiedByName = "getPeriodicityByCode")
    public abstract IndicatorProjetEntity asEntity(IndicatorProjectExcelDTO dto);

    @Named("getIndicatorByCode")
    public IndicatorEntity getIndicatorByCode(String code) {
        if(StringUtils.isNotEmpty(code)){
            var indicator = indicatorRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code indicateur n'existe pas", code)));
            return indicator;
        }
        return null;
    }

//    @Mapping(source = "indicator.code", target = "codeIndicator")
//    @Mapping(source = "periodicity.code", target = "codePeriodicity")
    public abstract IndicatorProjectExcelDTO asExcelDto(IndicatorProjetEntity entity);
}
