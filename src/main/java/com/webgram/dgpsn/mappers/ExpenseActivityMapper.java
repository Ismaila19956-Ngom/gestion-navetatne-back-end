package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.ExpenseActivityDTO;
import com.webgram.dgpsn.repositories.BudgetActivityRepository;

import java.util.Objects;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class ExpenseActivityMapper implements EntityMapper<ExpenseActivityDTO, ExpenseActivityEntity> {

    @Autowired
    private BudgetActivityRepository budgetActivityRepository;

    @Override
    @Mapping(target = "categorieDepense", source = "categorieDepenseId", qualifiedByName = "getCategorieDepenseId")
    @Mapping(target = "typeDepense", source = "typeDepenseId", qualifiedByName = "getTypeDepenseId")
    @Mapping(target = "projet", source = "projetId", qualifiedByName = "getProjet")
    @Mapping(target = "budget", source = "budgetId", qualifiedByName = "getBudgetActivite")
    public abstract ExpenseActivityEntity asEntity(ExpenseActivityDTO dto);


    @Named("getCategorieDepenseId")
    public LabelEntity getCategorieDepenseId(Long categorieDepenseId) {
        if(Objects.nonNull(categorieDepenseId)) {
            return LabelEntity.builder().id(categorieDepenseId).build();
        }
        return null;
    }

    @Named("getTypeDepenseId")
    public TypeExpenseEntity getTypeDepenseId(Long typeDepenseId) {
        if(Objects.nonNull(typeDepenseId)) {
            return TypeExpenseEntity.builder().id(typeDepenseId).build();
        }
        return null;
    }

    @Named("getProjet")
    public ManagementUnitEntity getProjet(Long projetId) {
        return ManagementUnitEntity.builder().id(projetId).build();
    }

    @Named("getBudgetActivite")
    public BudgetActivityEntity getBudgetActivite(Long projetId) {
        return BudgetActivityEntity.builder().id(projetId).build();
    }

//    public abstract MilestoneEntity asEntity(MilestoneExcelDTO dto);
//
//    public abstract MilestoneExcelDTO asExcelDto(MilestoneEntity entity);
}
