package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ImpactsAndObjectiveEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.QImpactsAndObjectiveEntity;
import com.webgram.dgpsn.entities.enums.DetailType;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface ImpactsAndObjectiveRepository extends JpaRepository<ImpactsAndObjectiveEntity, Long>, QuerydslPredicateExecutor<ImpactsAndObjectiveEntity> {

    default Page<ImpactsAndObjectiveEntity> readByFiltering(Pageable pageable, DetailType detailType, String description, Long managementId) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(detailType)) {
            booleanBuilder.and(QImpactsAndObjectiveEntity.impactsAndObjectiveEntity.detailType.eq(detailType));
        }
        if(StringUtils.isNotEmpty(description)) {
            booleanBuilder.and(QImpactsAndObjectiveEntity.impactsAndObjectiveEntity.description.containsIgnoreCase(description));
        }
        if(Objects.nonNull(managementId)) {
            booleanBuilder.and(QImpactsAndObjectiveEntity.impactsAndObjectiveEntity.managementUnit.id.eq(managementId));
        }

        return findAll(booleanBuilder, pageable);
    }

    Optional<LabelEntity> findByCode(String code);
}
