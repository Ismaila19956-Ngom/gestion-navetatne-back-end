package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QReviewIssueLogEntity;
import com.webgram.dgpsn.entities.ReviewIssueLogEntity;

import java.util.Date;
import java.util.Objects;

@Repository
public interface ReviewIssueLogRepository extends JpaRepository<ReviewIssueLogEntity, Long>, QuerydslPredicateExecutor<ReviewIssueLogEntity> {

    default Page<ReviewIssueLogEntity> readAllByFilters(
            Pageable pageable, Long reviewId, Long issueLogId, String libelle, Date identificationDate,
            Date resolutionDate, Long criticityId, Long statusId, Long specificNatureId) {
        var booleanBuilder = new BooleanBuilder();
        if(Objects.nonNull(reviewId)){
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.review.id.eq(reviewId));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(identificationDate)) {
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.identificationDate.eq(identificationDate));
        }
        if(Objects.nonNull(resolutionDate)) {
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.resolutionDate.eq(resolutionDate));
        }
//        if(Objects.nonNull(criticityId)) {
//            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.criticity.id.eq(criticityId));
//        }
        if(Objects.nonNull(statusId)) {
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.status.id.eq(statusId));
        }
        if(Objects.nonNull(specificNatureId)) {
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.specificNature.id.eq(specificNatureId));
        }
        if(Objects.nonNull(issueLogId)){
            booleanBuilder.and(QReviewIssueLogEntity.reviewIssueLogEntity.issueLog.id.eq(issueLogId));
        }
        return findAll(booleanBuilder, pageable);
    }
}
