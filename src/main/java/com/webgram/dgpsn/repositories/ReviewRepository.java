package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QReviewEntity;
import com.webgram.dgpsn.entities.ReviewEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, QuerydslPredicateExecutor<ReviewEntity> {

    default Page<ReviewEntity> readAllByFilters(Pageable pageable, Long projetId, String keyPoint, String date) throws ParseException {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QReviewEntity.reviewEntity.projet.id.eq(projetId));
        }
        if(StringUtils.isNotEmpty(keyPoint)){
            booleanBuilder.and(QReviewEntity.reviewEntity.keyPoint.eq(keyPoint));
        }
        if(Objects.nonNull(date)){
            var formatDate= new SimpleDateFormat("yyyy-MM-dd").parse(date);
            booleanBuilder.and(QReviewEntity.reviewEntity.date.eq(formatDate));
        }
        return findAll(booleanBuilder, pageable);
    }
}
