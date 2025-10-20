package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QTdrEntity;
import com.webgram.dgpsn.entities.TdrEntity;

import java.util.Objects;

@Repository
public interface TdrRepository extends JpaRepository<TdrEntity, Long>, QuerydslPredicateExecutor<TdrEntity> {

    default Page<TdrEntity> findByCriteria(Long instructionId, String intitule, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        QTdrEntity qTdr = QTdrEntity.tdrEntity;

        if (Objects.nonNull(instructionId)) {
            builder.and(qTdr.instruction.id.eq(instructionId));
        }
        if (Objects.nonNull(intitule) && !intitule.isEmpty()) {
            builder.and(qTdr.intitule.containsIgnoreCase(intitule));
        }

        return findAll(builder, pageable);
    }
}