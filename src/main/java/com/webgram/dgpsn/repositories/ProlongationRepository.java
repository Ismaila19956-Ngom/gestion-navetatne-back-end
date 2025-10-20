package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ProlongationEntity;
import com.webgram.dgpsn.entities.QProlongationEntity;


import java.util.Objects;

@Repository
public interface ProlongationRepository extends JpaRepository<ProlongationEntity, Long>, QuerydslPredicateExecutor<ProlongationEntity> {

    default Page<ProlongationEntity> readAllByFiltering(
            Pageable pageable, String justification, Integer duration, Long fundingId) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(justification)) {
            booleanBuider.and(QProlongationEntity.prolongationEntity.justification.containsIgnoreCase(justification));
        }
        if(Objects.nonNull(duration)) {
            booleanBuider.and(QProlongationEntity.prolongationEntity.duration.eq(duration));
        }
        if(Objects.nonNull(fundingId)) {
            booleanBuider.and(QProlongationEntity.prolongationEntity.funding.id.eq(fundingId));
        }
        return findAll(booleanBuider, pageable);
    }
}
