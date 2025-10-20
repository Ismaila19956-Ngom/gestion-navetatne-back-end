package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QRecommendationEntity;
import com.webgram.dgpsn.entities.RecommendationEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Long>, QuerydslPredicateExecutor<RecommendationEntity> {

    default Page<RecommendationEntity> readAllByFilters(Pageable pageable, String libelle, String responsable
            , String deadline, Long issueLogId, Long riskId, Long assignmentId, Long statusId, Long projectId) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QRecommendationEntity.recommendationEntity.libelle.containsIgnoreCase(libelle));
        }
        if(StringUtils.isNotEmpty(responsable)) {
            booleanBuider.and(QRecommendationEntity.recommendationEntity.responsable.containsIgnoreCase(responsable));
        }
        if(Objects.nonNull(issueLogId)){
            booleanBuider.and(QRecommendationEntity.recommendationEntity.issueLog.id.eq(issueLogId));
        }
        if(Objects.nonNull(riskId)){
            booleanBuider.and(QRecommendationEntity.recommendationEntity.risk.id.eq(riskId));
        }
        if(Objects.nonNull(assignmentId)){
            booleanBuider.and(QRecommendationEntity.recommendationEntity.assignment.id.eq(assignmentId));
        }
        if(Objects.nonNull(statusId)){
            booleanBuider.and(QRecommendationEntity.recommendationEntity.status.id.eq(statusId));
        }
        if(StringUtils.isNotEmpty(deadline)){
            Date formatDeadline = null;
            try {
                formatDeadline = new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
            } catch (ParseException ex) {
                throw new NullPointerException("Error occurred when parsing deadline");
            }
            booleanBuider.and(QRecommendationEntity.recommendationEntity.deadline.eq(formatDeadline));
        }
        if(Objects.nonNull(projectId)){
            booleanBuider.and(QRecommendationEntity.recommendationEntity.issueLog.projet.id.eq(projectId));
        }

        return findAll(booleanBuider, pageable);
    }
}
