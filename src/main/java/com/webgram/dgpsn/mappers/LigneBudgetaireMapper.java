package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.models.RecrutementDTO;
import com.webgram.dgpsn.repositories.BudgetDgpsnRepository;
import com.webgram.dgpsn.repositories.PlanComptableElementRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.webgram.dgpsn.entities.BudgetDgpsnEntity;
import com.webgram.dgpsn.entities.LigneBudgetaireEntity;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class LigneBudgetaireMapper implements EntityMapper<LigneBudgetaireDTO, LigneBudgetaireEntity> {

    @Override
    @Mapping(target = "budget", source = "budgetId", qualifiedByName = "getBudget")
    @Mapping(target = "rubrique", source = "rubriqueId", qualifiedByName = "getRubrique")
    public abstract LigneBudgetaireEntity asEntity(LigneBudgetaireDTO dto);

    @Override
    public abstract LigneBudgetaireDTO asDto(LigneBudgetaireEntity entity);

    @Named("getBudget")
    public BudgetDgpsnEntity getBudget(Long budgetId) {
        return BudgetDgpsnEntity.builder().id(budgetId).build();

    }
    @Named("getRubrique")
    public PlanComptableElementEntity getRubrique(Long planComptableElementId) {
        return PlanComptableElementEntity.builder().id(planComptableElementId).build();
    }
}