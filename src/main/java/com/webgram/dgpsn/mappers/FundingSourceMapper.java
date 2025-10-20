package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.FundingSourceDTO;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class FundingSourceMapper implements EntityMapper<FundingSourceDTO, FundingSourceEntity> {

    @Autowired
//    private BudgetRepository budgetRepository;

    @Override
    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    @Mapping(target = "structure", source = "structureId", qualifiedByName = "getStructure")
    @Mapping(target = "budget", source = "budgetId", qualifiedByName = "getBudget")
    public abstract FundingSourceEntity asEntity(FundingSourceDTO dto);

    @Named("getManagementUnit")
    public ManagementUnitEntity getManagementUnit(Long managementUnitId) {
        return ManagementUnitEntity.builder().id(managementUnitId).build();
    }

    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        return StructureEntity.builder().id(structureId).build();
    }

    @Named("getBudget")
    public BudgetEntity getBudget(Long budgetId) {
        return BudgetEntity.builder().id(budgetId).build();
    }


/// public abstract FundingActivityEntity asEntity(FundingActivityDTO dto);

//    public abstract FundingActivityDTO asExcelDto(FundingActivityEntity entity);
}
