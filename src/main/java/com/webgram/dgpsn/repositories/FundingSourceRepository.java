package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.models.responses.StatisticalFundingDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public interface FundingSourceRepository extends JpaRepository<FundingSourceEntity, Long>, QuerydslPredicateExecutor<FundingSourceEntity> {

    default Page<FundingSourceEntity> readAllByFiltering(
            Pageable pageable,
            String montant,
            Long managementUnitId,
            Long structureId,
            Long budgetId,
            Long tacheId,
            Long valueIndicatorId,
            Long budgetGlobalId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(montant)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.montant.stringValue().equalsIgnoreCase(montant));
        }

        if(Objects.nonNull(structureId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.structure.id.eq(structureId));
        }

        if(Objects.nonNull(budgetId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.budget.id.eq(budgetId));
        }

        if(Objects.nonNull(managementUnitId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.managementUnit.id.eq(managementUnitId));
        }
        if (Objects.nonNull(tacheId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.tache.id.eq(tacheId));
        }
        if (Objects.nonNull(valueIndicatorId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.valueIndicator.id.eq(valueIndicatorId));
        }
        if (Objects.nonNull(budgetGlobalId)) {
            booleanBuider.and(QFundingSourceEntity.fundingSourceEntity.budgetGlobal.id.eq(budgetGlobalId));
        }
        return findAll(booleanBuider, pageable);
    }
    @Query("select new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(f.structure.nom, sum(f.montant)) from FundingSourceEntity f group by f.structure.nom")
    List<StatisticalFundingDTO> findTotalFundingByPartner();

//    @Query("select new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(f.structure.code, avg(datediff(current_date, f.managementUnit.actualStartDate) / 365)) from FundingSourceEntity f group by f.structure.code")
//    List<StatisticalFundingDTO> avgAgeProjectByPartner();
@Query(value = "SELECT fs.structure_code, AVG(EXTRACT(EPOCH FROM (CURRENT_DATE - fs.management_unit_actual_start_date)) / (60 * 60 * 24 * 365.25)) " +
        "FROM funding_source_entity fs " +
        "GROUP BY fs.structure_code", nativeQuery = true)
List<StatisticalFundingDTO> avgAgeProjectByPartner();

    List<FundingSourceEntity> findAllByBudgetId(Long budgetId);

    Set<FundingSourceEntity> findByManagementUnitId(Long id);

}
