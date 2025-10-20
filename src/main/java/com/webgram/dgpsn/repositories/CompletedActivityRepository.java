package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CompletedActivityEntity;
import com.webgram.dgpsn.entities.QCompletedActivityEntity;

import java.util.Date;
import java.util.Objects;


@Repository
public interface CompletedActivityRepository extends JpaRepository<CompletedActivityEntity, Long>, QuerydslPredicateExecutor<CompletedActivityEntity> {

    default Page<CompletedActivityEntity> readAllByFiltering(
            Pageable pageable, String code, String libelle, Date dateDebut, Date dateFin, Long issueLogId) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(dateDebut)) {
            booleanBuider.and(QCompletedActivityEntity.completedActivityEntity.dateDebut.eq(dateDebut));
        }
        if(Objects.nonNull(dateFin)) {
            booleanBuider.and(QCompletedActivityEntity.completedActivityEntity.dateFin.eq(dateFin));
        }
        if(Objects.nonNull(issueLogId)) {
            booleanBuider.and(QCompletedActivityEntity.completedActivityEntity.issueLog.id.eq(issueLogId));
        }

        return findAll(booleanBuider, pageable);
    }
}
