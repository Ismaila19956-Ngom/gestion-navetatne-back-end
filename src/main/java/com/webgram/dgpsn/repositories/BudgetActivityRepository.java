package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.*;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

@Repository
public interface BudgetActivityRepository extends JpaRepository<BudgetActivityEntity, Long>, QuerydslPredicateExecutor<BudgetActivityEntity> {
    default Page<BudgetActivityEntity> readAllByFiltering(
            Pageable pageable,
            Long typeBudgetId,
            Integer year,
            Double amount,
            String sortBy,
            Boolean ascending,
            Long projetId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(year)) {
//            var formatPredicatedDate = new SimpleDateFormat("yyyy-MM-dd").parse(allocation);
            booleanBuider.and(QBudgetActivityEntity.budgetActivityEntity.year.eq(year));
        }
        if(Objects.nonNull(amount)) {
            booleanBuider.and(QBudgetActivityEntity.budgetActivityEntity.amount.eq(amount));
        }

        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QBudgetActivityEntity.budgetActivityEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(typeBudgetId)) {
            booleanBuider.and(QBudgetActivityEntity.budgetActivityEntity.typeBudget.id.eq(typeBudgetId));
        }
        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }
//    @Query("SELECT new sn.webg.suivievaluation.models.responses.StatisticalDateBudgetDTO(b.allocation, SUM(b.amount)) FROM BudgetActivityEntity b" +
//            "GROUP BY b.allocation")
//    List<StatisticalDateBudgetDTO> totalBudgetActiviterByYear();

//@Query("SELECT new sn.webg.suivievaluation.models.responses.StatisticalFundingDTO(b.managementUnit.name, b.allocation, SUM(b.amount)) FROM BudgetActivityEntity b GROUP BY b.managementUnit.name, b.allocation")
//List<StatisticalFundingDTO> totalBudgetActiviterByYear();
@Query("select new com.webgram.dgpsn.models.responses.StatisticalBudgetActivityDTO(mu.name, sum(b.amount)) " +
        "from BudgetActivityEntity b " +
        "join b.projet mu " +
        "where mu.type = :typeProjet " +
        "group by mu.name")
List<StatisticalBudgetActivityDTO> findTotalBudgetByActivity(@Param("typeProjet") TypeProjet typeProjet);

    @Query("select new com.webgram.dgpsn.models.responses.StatisticalBudgetActivityDTO(mu.name, sum(b.amount)) " +
            "from BudgetActivityEntity b " +
            "join b.projet mu " +
            "where mu.parent.id = :projectId and mu.type = :typeProjet " +
            "group by mu.name")
    List<StatisticalBudgetActivityDTO> findActivitiesAndBudgetsByProject(@Param("projectId") Long projectId, @Param("typeProjet") TypeProjet typeProjet);




}
