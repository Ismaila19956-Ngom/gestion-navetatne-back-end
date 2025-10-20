package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.OrdonnancementEntity;
import com.webgram.dgpsn.models.OrdonnancementDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OrdonnancementMapper extends EntityMapper<OrdonnancementDTO, OrdonnancementEntity> {

//    @Mapping(source = "beneficiaire.id", target = "beneficiaireId")
//    @Mapping(source = "engagement.id", target = "engagementId")
    @Mapping(source = "budgetPassation.id", target = "budgetPassationId")
    OrdonnancementDTO asDto(OrdonnancementEntity entity);

//    @Mapping(source = "beneficiaireId", target = "beneficiaire.id")
//    @Mapping(source = "engagementId", target = "engagement.id")
    @Mapping(source = "budgetPassationId", target = "budgetPassation.id")
    OrdonnancementEntity asEntity(OrdonnancementDTO dto);
}