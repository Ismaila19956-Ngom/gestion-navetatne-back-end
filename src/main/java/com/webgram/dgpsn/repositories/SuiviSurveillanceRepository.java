package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QSuiviSurveillanceEntity;
import com.webgram.dgpsn.entities.SuiviSurveillanceEntity;

import java.util.Objects;

@Repository
public interface SuiviSurveillanceRepository extends JpaRepository<SuiviSurveillanceEntity, Long>, QuerydslPredicateExecutor<SuiviSurveillanceEntity> {

    default Page<SuiviSurveillanceEntity> findByCriteria(Long promoteurId, String intitule, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QSuiviSurveillanceEntity qSuivi = QSuiviSurveillanceEntity.suiviSurveillanceEntity;

        if (Objects.nonNull(promoteurId)) {
            builder.and(qSuivi.promoteur.id.eq(promoteurId));
        }
        if (Objects.nonNull(intitule) && !intitule.isEmpty()) {
            builder.and(qSuivi.intitule.containsIgnoreCase(intitule));
        }

        return findAll(builder, pageable);
    }
}