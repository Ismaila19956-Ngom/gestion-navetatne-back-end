package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface IndicatorProjetRepository extends JpaRepository<IndicatorProjetEntity, Long>, QuerydslPredicateExecutor<IndicatorProjetEntity> {

    List<IndicatorProjetEntity> findByProjetId(Long id);

    Optional<IndicatorProjetEntity> findByProjetAndIndicatorCode(ManagementUnitEntity project, String indicatorCode);
    default Page<IndicatorProjetEntity> readAllByFiltering(
            Pageable pageable,
            Double targetValue,
            Long indicatorId,
            Long projetId,
            Long periodicityId
    ) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(targetValue)) {
            booleanBuider.and(QIndicatorProjetEntity.indicatorProjetEntity.targetValue.eq(targetValue));
        }
        if(Objects.nonNull(indicatorId)) {
            booleanBuider.and(QIndicatorProjetEntity.indicatorProjetEntity.indicator.id.eq(indicatorId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QIndicatorProjetEntity.indicatorProjetEntity.projet.id.eq(projetId));
        }
//        if(Objects.nonNull(periodicityId)) {
//            booleanBuider.and(QIndicatorProjetEntity.indicatorProjetEntity.periodicity.id.eq(periodicityId));
//        }

        return findAll(booleanBuider, pageable);
    }
    Optional<List<IndicatorProjetEntity>> findByProjet(ManagementUnitEntity projet);
}
