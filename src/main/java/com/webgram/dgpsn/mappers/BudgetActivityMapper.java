package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.BudgetActivityEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.models.BudgetActivityDTO;
import com.webgram.dgpsn.repositories.BudgetActivityRepository;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class BudgetActivityMapper implements EntityMapper<BudgetActivityDTO, BudgetActivityEntity> {

    @Autowired
    private BudgetActivityRepository budgetActivityRepository;

    @Override
//    @Mapping(target = "typeBudget", source = "typeBudgetId", qualifiedByName = "getTypeBudgetId")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    public abstract BudgetActivityEntity asEntity(BudgetActivityDTO dto);


    @Named("getTypeBudgetId")
    public LabelEntity getTypeBudgetId(Long typeBudgetId) {
        if(Objects.nonNull(typeBudgetId)) {
            return LabelEntity.builder().id(typeBudgetId).build();
        }
        return null;
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

//    public abstract MilestoneEntity asEntity(MilestoneExcelDTO dto);
//
//    public abstract MilestoneExcelDTO asExcelDto(MilestoneEntity entity);
}
