package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QReviewRiskEntity;
import com.webgram.dgpsn.entities.ReviewRiskEntity;

import java.util.Date;
import java.util.Objects;

@Repository
public interface ReviewRiskRepository extends JpaRepository<ReviewRiskEntity, Long>, QuerydslPredicateExecutor<ReviewRiskEntity> {

    default Page<ReviewRiskEntity> readAllByFilters(
            Pageable pageable, Long reviewId, Long riskId, String libelle, Date identificationDate,
            Date resolutionDate, Long criticityId, Long statusId, Long natureId) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(reviewId)){
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.review.id.eq(reviewId));
        }
        if(Objects.nonNull(riskId)){
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.id.eq(riskId));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(identificationDate)) {
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.identificationDate.eq(identificationDate));
        }
        if(Objects.nonNull(resolutionDate)) {
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.resolutionDate.eq(resolutionDate));
        }
//        if(Objects.nonNull(criticityId)) {
//            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.criticity.id.eq(criticityId));
//        }
        if(Objects.nonNull(statusId)) {
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.status.id.eq(statusId));
        }
        if(Objects.nonNull(natureId)) {
            booleanBuilder.and(QReviewRiskEntity.reviewRiskEntity.risk.nature.id.eq(natureId));
        }

        return findAll(booleanBuilder, pageable);
    }
}
