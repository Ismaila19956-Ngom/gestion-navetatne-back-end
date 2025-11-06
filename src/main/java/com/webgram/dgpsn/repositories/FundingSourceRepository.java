package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
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

    /**
     * Trouve toutes les sources de financement d'une unité de gestion
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.managementUnit.id = :managementUnitId")
    List<FundingSourceEntity> findByManagementUnitIdPerso(@Param("managementUnitId") Long managementUnitId);

    /**
     * Trouve toutes les sources de financement d'une tâche
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.tache.id = :tacheId")
    List<FundingSourceEntity> findByTacheId(@Param("tacheId") Long tacheId);

    /**
     * Trouve toutes les sources de financement d'un indicateur
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.valueIndicator.id = :valueIndicatorId")
    List<FundingSourceEntity> findByValueIndicatorId(@Param("valueIndicatorId") Long valueIndicatorId);

    /**
     * Trouve toutes les sources de financement d'un budget
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.budget.id = :budgetId")
    List<FundingSourceEntity> findByBudgetId(@Param("budgetId") Long budgetId);

    /**
     * Trouve toutes les sources de financement pour une liste d'unités de gestion.
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.managementUnit.id IN :managementUnitIds")
    List<FundingSourceEntity> findByManagementUnitIdIn(@Param("managementUnitIds") List<Long> managementUnitIds);

    /**
     * Trouve toutes les sources de financement pour une liste de tâches.
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.tache.id IN :tacheIds")
    List<FundingSourceEntity> findByTacheIdIn(@Param("tacheIds") List<Long> tacheIds);

    /**
     * Trouve toutes les sources de financement pour une liste de valeurs d'indicateur.
     */
    @Query("SELECT f FROM FundingSourceEntity f WHERE f.valueIndicator.id IN :valueIndicatorIds")
    List<FundingSourceEntity> findByValueIndicatorIdIn(@Param("valueIndicatorIds") List<Long> valueIndicatorIds);
}
