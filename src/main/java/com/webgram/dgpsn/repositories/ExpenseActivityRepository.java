package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ExpenseActivityEntity;
import com.webgram.dgpsn.entities.QExpenseActivityEntity;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.StatisticalBudgetActivityDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Repository
public interface ExpenseActivityRepository extends JpaRepository<ExpenseActivityEntity, Long>, QuerydslPredicateExecutor<ExpenseActivityEntity> {

    default Page<ExpenseActivityEntity> readAllByFiltering(
            Pageable pageable,
            String date,
            String unitAmount,
            String quantity,
            Double totalAmount,
            Long categorieDepenseId,
            Long typeDepenseId,
            Long projetId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if (Objects.nonNull(date)) {
            var formatDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.date.eq(formatDate));
        }
        if (Objects.nonNull(unitAmount)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.unitAmount.containsIgnoreCase(unitAmount));
        }
        if (Objects.nonNull(quantity)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.quantity.containsIgnoreCase(quantity));
        }
        if (Objects.nonNull(totalAmount)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.totalAmount.eq(totalAmount));
        }

        if (Objects.nonNull(categorieDepenseId)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.categorieDepense.id.eq(categorieDepenseId));
        }
        if (Objects.nonNull(typeDepenseId)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.typeDepense.id.eq(typeDepenseId));
        }
        if (Objects.nonNull(projetId)) {
            booleanBuider.and(QExpenseActivityEntity.expenseActivityEntity.projet.id.eq(projetId));
        }
        return findAll(booleanBuider, pageable);
    }
    @Query("select new com.webgram.dgpsn.models.responses.StatisticalBudgetActivityDTO(mu.name, sum(b.totalAmount)) " +
            "from ExpenseActivityEntity b " +
            "join b.projet mu " +
            "where mu.type = :typeProjet " +
            "group by mu.name")
    List<StatisticalBudgetActivityDTO> findTotalExpanseByActivity(@Param("typeProjet") TypeProjet typeProjet);

    @Query("select new com.webgram.dgpsn.models.responses.StatisticalBudgetActivityDTO(mu.name, sum(b.totalAmount)) " +
            "from ExpenseActivityEntity b " +
            "join b.projet mu " +
            "where mu.parent.id = :projectId and mu.type = :typeProjet " +
            "group by mu.name")
    List<StatisticalBudgetActivityDTO> findActivitiesAndExpensesByProject(@Param("projectId") Long projectId, @Param("typeProjet") TypeProjet typeProjet);


}
