package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.AlerteEntity;
import com.webgram.dgpsn.entities.QAlerteEntity;
import com.webgram.dgpsn.entities.enums.Priority;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;


@Repository
public interface AlerteRepository extends JpaRepository<AlerteEntity, Long>, QuerydslPredicateExecutor<AlerteEntity> {
    default Page<AlerteEntity> readAllByFilters(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(read)) {
            booleanBuilder.and(QAlerteEntity.alerteEntity.read.eq(read));
        }
        if(Objects.nonNull(priority)) {
            booleanBuilder.and(QAlerteEntity.alerteEntity.priority.eq(priority));
        }
        if(StringUtils.isNotEmpty(username)) {
            booleanBuilder.and(QAlerteEntity.alerteEntity.user.login.eq(username));
        }
        if(Objects.nonNull(date) && StringUtils.isNotEmpty(critere)){
            if("Egal".equals(critere)) {
                booleanBuilder.and(QAlerteEntity.alerteEntity.date.goe(date));
                booleanBuilder.and(QAlerteEntity.alerteEntity.date.loe(endDate));
            }else if ("Inferieure".equals(critere)) {
                booleanBuilder.and(QAlerteEntity.alerteEntity.date.loe(date));
            }else if ("Supperieure".equals(critere)) {
                booleanBuilder.and(QAlerteEntity.alerteEntity.date.goe(date));
            }else {
                if(Objects.nonNull(endDate)) {
                    booleanBuilder.and(QAlerteEntity.alerteEntity.date.goe(date));
                    booleanBuilder.and(QAlerteEntity.alerteEntity.date.loe(endDate));
                }else {
                    booleanBuilder.and(QAlerteEntity.alerteEntity.date.goe(date));
                }

            }

        }
        return findAll(booleanBuilder, pageable);
    }
}
