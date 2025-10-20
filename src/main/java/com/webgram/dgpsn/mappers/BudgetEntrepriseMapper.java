package com.webgram.dgpsn.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.webgram.dgpsn.entities.BudgetEntrepriseEntity;
import com.webgram.dgpsn.entities.CategoriebudgetaireEntity;
import com.webgram.dgpsn.entities.PeriodeEntity;
import com.webgram.dgpsn.entities.PeriodiciteEntity;
import com.webgram.dgpsn.models.BudgetEntrepriseDto;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.BudgetExcelDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class BudgetEntrepriseMapper implements EntityMapper<BudgetEntrepriseDto, BudgetEntrepriseEntity> {

@Mapping(target = "entreprise.id", source = "entrepriseId")
@Mapping(target = "categoriebudgetaire", source = "categoriebudgetaireId", qualifiedByName = "mapCategoriebudgetaire")
@Mapping(target = "periode", source = "periodeId", qualifiedByName = "mapPeriode")
@Mapping(target = "periodicite", source = "periodiciteId", qualifiedByName = "mapPeriodicite")
public abstract BudgetEntrepriseEntity asEntity(BudgetEntrepriseDto budgetEntity);

@Named("mapCategoriebudgetaire")
public CategoriebudgetaireEntity mapCategoriebudgetaire(Long categoriebudgetaireId) {
    if (Objects.nonNull(categoriebudgetaireId)) {
        return CategoriebudgetaireEntity.builder().id(categoriebudgetaireId).build();
    }
    return null;
 }
@Named("mapPeriode")
public PeriodeEntity mapPeriode(Long periodeId) {
    if (Objects.nonNull(periodeId)) {
        return PeriodeEntity.builder().id(periodeId).build();
    }
    return null;
 }
@Named("mapPeriodicite")
public PeriodiciteEntity mapPeriodicite(Long periodiciteId) {
    if (Objects.nonNull(periodiciteId)) {
        return PeriodiciteEntity.builder().id(periodiciteId).build();
    }
    return null;
 }
public abstract BudgetEntrepriseDto asDto(BudgetEntrepriseEntity budgetEntity);
public abstract BudgetExcelDTO asExcelDto(BudgetEntrepriseEntity entity);
}
