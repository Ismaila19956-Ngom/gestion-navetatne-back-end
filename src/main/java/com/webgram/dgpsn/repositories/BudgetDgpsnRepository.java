package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.BudgetDgpsnEntity;
import com.webgram.dgpsn.entities.QBudgetDgpsnEntity;

import java.util.Objects;

@Repository
public interface BudgetDgpsnRepository extends JpaRepository<BudgetDgpsnEntity, Long>, QuerydslPredicateExecutor<BudgetDgpsnEntity> {

    default Page<BudgetDgpsnEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            Double montant,
            Integer annee
    ) {
        var booleanBuilder = new BooleanBuilder();

        if (Objects.nonNull(code)) {
            booleanBuilder.and(QBudgetDgpsnEntity.budgetDgpsnEntity.code.equalsIgnoreCase(code));
        }
        if (Objects.nonNull(libelle)) {
            booleanBuilder.and(QBudgetDgpsnEntity.budgetDgpsnEntity.libelle.equalsIgnoreCase(libelle));
        }
        if (Objects.nonNull(montant)) {
            booleanBuilder.and(QBudgetDgpsnEntity.budgetDgpsnEntity.montant.eq(montant));
        }
        if (Objects.nonNull(annee)) {
            booleanBuilder.and(QBudgetDgpsnEntity.budgetDgpsnEntity.annee.eq(annee));
        }


        return findAll(booleanBuilder, pageable);
    }
}