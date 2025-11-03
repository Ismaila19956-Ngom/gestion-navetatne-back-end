package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.LigneBudgetaireEntity;
import com.webgram.dgpsn.entities.QLigneBudgetaireEntity;

import java.util.List;
import java.util.Objects;

@Repository
public interface LigneBudgetaireRepository extends JpaRepository<LigneBudgetaireEntity, Long>, QuerydslPredicateExecutor<LigneBudgetaireEntity> {

    default List<LigneBudgetaireEntity> findByBudgetIdAndRubriqueIdAndType(
            Long budgetId,
            Long rubriqueId,
            TypeLigneBugetaire type) {

        var booleanBuilder = new BooleanBuilder();

        if (Objects.nonNull(budgetId)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.budget.id.eq(budgetId));
        }
        if (Objects.nonNull(rubriqueId)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.rubrique.id.eq(rubriqueId));
        }
        if (Objects.nonNull(type)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.typeLigneBugetaire.eq(type));
        }

        return (List<LigneBudgetaireEntity>) findAll(booleanBuilder);
    }

    default Page<LigneBudgetaireEntity> readAllByFiltering(
            Pageable pageable,
            Long rubriqueId,
            Double montant,
            TypeLigneBugetaire typeLigneBugetaire,
            String commentaire,
            Long budgetId
    ) {
        var booleanBuilder = new BooleanBuilder();

        if (Objects.nonNull(rubriqueId)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.rubrique.id.eq(rubriqueId));
        }
        if (Objects.nonNull(montant)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.montant.eq(montant));
        }
        if (Objects.nonNull(typeLigneBugetaire)) {
            // 🔹 Utilisation du bon nom de champ de l'entité
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.typeLigneBugetaire.eq(typeLigneBugetaire));
        }
        if (Objects.nonNull(commentaire)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.commentaire.containsIgnoreCase(commentaire));
        }
        if (Objects.nonNull(budgetId)) {
            booleanBuilder.and(QLigneBudgetaireEntity.ligneBudgetaireEntity.budget.id.eq(budgetId));
        }

        return findAll(booleanBuilder, pageable);
    }


    List<LigneBudgetaireEntity> findByBudgetId(Long budgetId);
}
