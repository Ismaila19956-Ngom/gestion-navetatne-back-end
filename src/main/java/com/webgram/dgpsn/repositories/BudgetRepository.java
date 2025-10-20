package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.BudgetEntity;
import com.webgram.dgpsn.entities.QBudgetEntity;
import com.webgram.dgpsn.entities.enums.SouceBudget;
import com.webgram.dgpsn.models.responses.StatisticalFundingDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long>, QuerydslPredicateExecutor<BudgetEntity> {

    default Page<BudgetEntity> readAllByFiltering(
            Pageable pageable,
            String libelle,
            SouceBudget souceBudget,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QBudgetEntity.budgetEntity.libelle.equalsIgnoreCase(libelle));
        }
        if(Objects.nonNull(souceBudget)) {
            booleanBuider.and(QBudgetEntity.budgetEntity.souceBudget.eq(souceBudget));
        }
        if(Objects.nonNull(estimatedAmount)) {
            booleanBuider.and(QBudgetEntity.budgetEntity.estimatedAmount.equalsIgnoreCase(estimatedAmount));
        }
        if(Objects.nonNull(actualAmount)) {
            booleanBuider.and(QBudgetEntity.budgetEntity.actualAmount.stringValue().equalsIgnoreCase(actualAmount));
        }

        if(Objects.nonNull(managementUnitId)) {
            booleanBuider.and(QBudgetEntity.budgetEntity.managementUnit.id.eq(managementUnitId));
        }

        return findAll(booleanBuider, pageable);
    }
    @Query("select new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(ss.libelle,SUM(f.actualAmount)) " +
            "FROM BudgetEntity f " +
            "JOIN f.managementUnit mu " +
            "JOIN mu.subSectors ss " +
            "GROUP BY ss.libelle")
    List<StatisticalFundingDTO> findAverageFundingBySector();

    @Query("SELECT SUM(b.actualAmount) FROM BudgetEntity b")
    Long findTotalFunding();

    @Query("SELECT SUM(b.actualAmount) FROM BudgetEntity b WHERE b.managementUnit.id = :projectId")
    Double getTotalBudgetByProject(@Param("projectId") Long projectId);

    @Query("SELECT b.id as budgetId, b.estimatedAmount as estimatedAmount, b.souceBudget as typefinancement FROM BudgetEntity b WHERE b.managementUnit.id = :projectId ORDER BY b.id")
    List<Map<String, Object>> findEstimatedAmountByProjet(@Param("projectId") Long projectId);

    @Query("SELECT distinct(b.souceBudget) FROM BudgetEntity b WHERE b.managementUnit.id = :projectId")
    Set<SouceBudget> findAllDistinctBySouceBudget(@Param("projectId") Long projectId);

    @Query("SELECT COALESCE(SUM(b.actualAmount), 0.0) " +
            "FROM BudgetEntity b " +
            "WHERE b.managementUnit.type = 'PROJECT'")
    Double calculateTotalActualAmountForProjects();

    @Query("SELECT COALESCE(SUM(b.actualAmount), 0.0) " +
            "FROM BudgetEntity b " +
            "WHERE b.managementUnit.id = :managementUnitId")
    Double sumActualAmountByManagementUnitId(@Param("managementUnitId") Long managementUnitId);
}
