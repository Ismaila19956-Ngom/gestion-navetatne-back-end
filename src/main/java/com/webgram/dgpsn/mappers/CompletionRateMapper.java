package com.webgram.dgpsn.mappers;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.CompletionRateEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.CompletionRateDTO;

@Slf4j
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CompletionRateMapper implements EntityMapper<CompletionRateDTO, CompletionRateEntity> {

    @Mapping(target = "managementUnit.id", source = "managementUnitId")
    public abstract CompletionRateEntity asEntity(CompletionRateDTO dto);


    @Named("getProject")
    public ManagementUnitEntity getProjet(Long managementUnitId) {
        return ManagementUnitEntity.builder().id(managementUnitId).build();
    }

}
