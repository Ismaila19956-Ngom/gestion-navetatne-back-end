package com.webgram.dgpsn.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.models.RiskDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.modelExcel.RiskExcelDTO;

import java.text.MessageFormat;
import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class RiskMapper implements EntityMapper<RiskDTO, RiskEntity> {

    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Override
    @Mapping(target = "projet.id", source = "projetId")
    @Mapping(target = "status", source = "statusId", qualifiedByName = "getStatus")
    @Mapping(target = "nature", source = "natureId", qualifiedByName = "getNature")
    public abstract RiskEntity asEntity(RiskDTO dto);

//    @Named("getDelayImpact")
//    public DelayImpactEntity getDelayImpact(Long delayImpactId) {
//        if(Objects.nonNull(delayImpactId)) {
//            return DelayImpactEntity.builder().id(delayImpactId).build();
//        }
//        return null;
//    }
//    @Named("getFinancialImpact")
//    public FinancialImpactEntity getFinancialImpact(Long financialImpactId) {
//        if(Objects.nonNull(financialImpactId)) {
//            return FinancialImpactEntity.builder().id(financialImpactId).build();
//        }
//        return null;
//    }
    @Named("getStatus")
    public StatusEntity getStatus(Long statusId) {
        if(Objects.nonNull(statusId)) {
            return StatusEntity.builder().id(statusId).build();
        }
        return null;
    }
    @Named("getNature")
    public LabelEntity getNature(Long natureId) {
        if(Objects.nonNull(natureId)) {
            return LabelEntity.builder().id(natureId).build();
        }
        return null;
    }

    @Named("getCriticity")
    public LabelEntity getCriticity(Long criticityId) {
        if(Objects.nonNull(criticityId)) {
            return LabelEntity.builder().id(criticityId).build();
        }
        return null;
    }


//    @Mapping(target = "projet", source = "projectId", qualifiedByName = "getProjet")
//    @Mapping(target = "criticity", source = "criticityCode", qualifiedByName = "getCriticityByCode")
//    @Mapping(target = "delayImpact", source = "delayImpactCode", qualifiedByName = "getDelayImpactByCode")
//    @Mapping(target = "financialImpact", source = "financialImpactCode", qualifiedByName = "getFinancialImpactByCode")
//    @Mapping(target = "status", source = "statusCode", qualifiedByName = "getStatusByCode")
//    @Mapping(target = "nature", source = "natureCode", qualifiedByName = "getNatureByCode")
    public abstract RiskEntity asEntity(RiskExcelDTO dto);

//    @Mapping(source = "criticity.code", target = "criticityCode")
//    @Mapping(source = "delayImpact.code", target = "delayImpactCode")
//    @Mapping(source = "financialImpact.code", target = "financialImpactCode")
//    @Mapping(source = "status.code", target = "statusCode")
//    @Mapping(source = "nature.code", target = "natureCode")
    public abstract RiskExcelDTO asExcelDto(RiskEntity entity);

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projectId) {
        return ManagementUnitEntity.builder().id(projectId).build();
    }

    @Named("getCriticityByCode")
    public LabelEntity getCriticity(String criticityCode) {
        if(StringUtils.isNotEmpty(criticityCode)){
            return labelRepository.findByCode(criticityCode)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code criticité n'existe pas {}", criticityCode)));
        }
        return null;
    }

//    @Named("getDelayImpactByCode")
//    public DelayImpactEntity getDelayImpact(String delayImpactCode) {
//        if(StringUtils.isNotEmpty(delayImpactCode)){
//            var delayImpact = delayImpactRepository.findByCode(delayImpactCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code d'impact delai n'existe pas {}", delayImpactCode)));
//            return delayImpact;
//
//        }
//        return null;
//    }

//    @Named("getFinancialImpactByCode")
//    public FinancialImpactEntity getFinancialImpact(String financialImpactCode) {
//        if(StringUtils.isNotEmpty(financialImpactCode)){
//            var financialImpact = financialImpactRepository.findByCode(financialImpactCode)
//                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code d'impact financier n'existe pas {}", financialImpactCode)));
//            return financialImpact;
//
//        }
//        return null;
//    }

    @Named("getStatusByCode")
    public StatusEntity getStatus(String statusCode) {
        if(StringUtils.isNotEmpty(statusCode)){
            var status = statusRepository.findByCode(statusCode)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code de status n'existe pas {}", statusCode)));
            return status;

        }
        return null;
    }

    @Named("getNatureByCode")
    public LabelEntity getNature(String natureCode) {
        if(StringUtils.isNotEmpty(natureCode)){
            return labelRepository.findByCode(natureCode)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("Ce code de nature n'existe pas {}", natureCode)));
        }
        return null;
    }
}
