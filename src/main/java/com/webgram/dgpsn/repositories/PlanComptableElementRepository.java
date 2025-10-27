package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QPlanComptableElementEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import java.util.List;
import java.util.Objects;

@Repository
public interface PlanComptableElementRepository extends JpaRepository<PlanComptableElementEntity, Long>,
        QuerydslPredicateExecutor<PlanComptableElementEntity> {

    /**
     * Vérifie si un code existe déjà
     */
    boolean existsByCode(String code);

    /**
     * Vérifie si un code existe déjà en excluant un ID spécifique
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM PlanComptableElementEntity p " +
            "WHERE p.code = :code AND p.id <> :id")
    boolean existsByCodeAndIdNot(String code, Long id);

    default Page<PlanComptableElementEntity> readAllByFiltering(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();
        QPlanComptableElementEntity qPlanComptableElementEntity =
                QPlanComptableElementEntity.planComptableElementEntity;

        // Application des filtres
        if (Objects.nonNull(idsToIgnore) && !idsToIgnore.isEmpty()) {
            booleanBuilder.and(qPlanComptableElementEntity.id.notIn(idsToIgnore));
        }
        if (Objects.nonNull(type)) {
            booleanBuilder.and(qPlanComptableElementEntity.type.eq(type));
        }
        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(qPlanComptableElementEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(qPlanComptableElementEntity.libelle.containsIgnoreCase(libelle));
        }

        // Configuration du tri
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Objects.nonNull(ascending)) {
            sort = Boolean.TRUE.equals(ascending) ? sort.ascending() : sort.descending();
        }

        var pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );

        return findAll(booleanBuilder, pageRequest);
    }
}