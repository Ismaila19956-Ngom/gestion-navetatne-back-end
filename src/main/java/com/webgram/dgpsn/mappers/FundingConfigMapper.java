package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.FundingConfigEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.FundingConfigDTO;
import com.webgram.dgpsn.repositories.BudgetRepository;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FundingConfigMapper implements EntityMapper<FundingConfigDTO, FundingConfigEntity> {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    public abstract FundingConfigEntity asEntity(FundingConfigDTO dto);

    @Named("getManagementUnit")
    public ManagementUnitEntity getManagementUnit(Long managementUnitId) {
        return ManagementUnitEntity.builder().id(managementUnitId).build();
    }

/// public abstract FundingActivityEntity asEntity(FundingActivityDTO dto);

//    public abstract FundingActivityDTO asExcelDto(FundingActivityEntity entity);
}
