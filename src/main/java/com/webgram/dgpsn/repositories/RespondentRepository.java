package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QRespondentEntity;
import com.webgram.dgpsn.entities.RespondentEntity;


import java.util.Date;
import java.util.Objects;

@Repository
public interface RespondentRepository extends JpaRepository<RespondentEntity, Long>, QuerydslPredicateExecutor<RespondentEntity> {

    default Page<RespondentEntity> readAllByFiltering(
            Pageable pageable, String firstName, String lastName, String phone, Date startDate, Date endDate, Long fundingId) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(firstName)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.firstName.containsIgnoreCase(firstName));
        }
        if(StringUtils.isNotEmpty(lastName)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.lastName.containsIgnoreCase(lastName));
        }
        if(StringUtils.isNotEmpty(phone)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.phone.containsIgnoreCase(phone));
        }
        if(Objects.nonNull(startDate)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.startDate.goe(startDate));
        }
        if(Objects.nonNull(endDate)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.endDate.loe(endDate));
        }
        if(Objects.nonNull(fundingId)) {
            booleanBuider.and(QRespondentEntity.respondentEntity.funding.id.eq(fundingId));
        }
        return findAll(booleanBuider, pageable);
    }
}
