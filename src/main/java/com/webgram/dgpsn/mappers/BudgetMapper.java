package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.BudgetEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.BudgetDTO;
import com.webgram.dgpsn.repositories.BudgetRepository;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class BudgetMapper implements EntityMapper<BudgetDTO, BudgetEntity> {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    public abstract BudgetEntity asEntity(BudgetDTO dto);

    @Named("getManagementUnit")
    public ManagementUnitEntity getManagementUnit(Long getManagementUnitId) {
        return ManagementUnitEntity.builder().id(getManagementUnitId).build();
    }

/// public abstract FundingActivityEntity asEntity(FundingActivityDTO dto);

//    public abstract FundingActivityDTO asExcelDto(FundingActivityEntity entity);
}