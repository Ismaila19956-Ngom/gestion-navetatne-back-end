package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.EngagementEntity;
import com.webgram.dgpsn.models.EngagementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EngagementMapper extends EntityMapper<EngagementDTO, EngagementEntity> {

//    @Mapping(source = "typeEngagement.id", target = "typeEngagementId")
//    @Mapping(source = "structureBeneficiaire.id", target = "structureBeneficiaireId")
    @Mapping(source = "budgetPassation.id", target = "budgetPassationId")
    EngagementDTO asDto(EngagementEntity entity);

//    @Mapping(source = "typeEngagementId", target = "typeEngagement.id")
//    @Mapping(source = "structureBeneficiaireId", target = "structureBeneficiaire.id")
    @Mapping(source = "budgetPassationId", target = "budgetPassation.id")
    EngagementEntity asEntity(EngagementDTO dto);
}