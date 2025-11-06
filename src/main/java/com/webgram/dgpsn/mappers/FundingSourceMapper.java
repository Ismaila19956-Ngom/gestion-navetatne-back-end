package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.FundingSourceDTO;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",uses = {TacheMapper.class})
public abstract class FundingSourceMapper implements EntityMapper<FundingSourceDTO, FundingSourceEntity> {

    public abstract FundingSourceDTO asDto(FundingSourceEntity entity);

    @Override
    @Mapping(target = "managementUnit", source = "managementUnitId", qualifiedByName = "getManagementUnit")
    @Mapping(target = "structure", source = "structureId", qualifiedByName = "getStructure")
    @Mapping(target = "budget", source = "budgetId", qualifiedByName = "getBudget")
    @Mapping(target = "tache", source = "tacheId", qualifiedByName = "getTache")
    @Mapping(target = "valueIndicator", source = "valueIndicatorId", qualifiedByName = "getValueIndicator")
    @Mapping(target = "budgetGlobal", source = "budgetGlobalId", qualifiedByName = "getBudgetGlobal")
    public abstract FundingSourceEntity asEntity(FundingSourceDTO dto);

    @Named("getBudgetGlobal")
    public BudgetDgpsnEntity getBudgetGlobal(Long budgetGlobalId) {
        if (Objects.nonNull(budgetGlobalId)) {
            return BudgetDgpsnEntity.builder().id(budgetGlobalId).build();
        }
        return null;
    }

    @Named("getTache")
    public TacheEntity getTache(Long tacheId) {
        if (Objects.nonNull(tacheId)) {
            return TacheEntity.builder().id(tacheId).build();
        }
        return null;
    }

    @Named("getValueIndicator")
    public ValueIndicatorEntity getValueIndicator(Long valueIndicatorId) {
        if (Objects.nonNull(valueIndicatorId)) {
            return ValueIndicatorEntity.builder().id(valueIndicatorId).build();
        }
        return null;
    }

    @Named("getManagementUnit")
    public ManagementUnitEntity getManagementUnit(Long managementUnitId) {
        if (Objects.nonNull(managementUnitId)) {
            return ManagementUnitEntity.builder().id(managementUnitId).build();
        }
        return null;
    }

    @Named("getStructure")
    public StructureEntity getStructure(Long structureId) {
        if (Objects.nonNull(structureId)) {
            return StructureEntity.builder().id(structureId).build();
        }
        return null;
    }

    @Named("getBudget")
    public BudgetEntity getBudget(Long budgetId) {
        if (Objects.nonNull(budgetId)) {
            return BudgetEntity.builder().id(budgetId).build();
        }
        return null;
    }
}
