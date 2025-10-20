package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.AgrementEntity;
import com.webgram.dgpsn.entities.QAgrementEntity;

import java.util.Objects;

@Repository
public interface AgrementRepository extends JpaRepository<AgrementEntity, Long>, QuerydslPredicateExecutor<AgrementEntity> {

    default Page<AgrementEntity> findByCriteria(Long promoteurId, String objet, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QAgrementEntity qAgrement = QAgrementEntity.agrementEntity;

        if (Objects.nonNull(promoteurId)) {
            builder.and(qAgrement.promoteur.id.eq(promoteurId));
        }
        if (Objects.nonNull(objet) && !objet.isEmpty()) {
            builder.and(qAgrement.objet.containsIgnoreCase(objet));
        }

        return findAll(builder, pageable);
    }

    @Query("SELECT COUNT(a) FROM AgrementEntity a WHERE a.dateDelivranceArrete IS NOT NULL AND (a.observations = 'ARRETE DELIVRE' OR a.observations = 'ARRETE A FAIRE')")
    Long countAgrementsActifs();
}