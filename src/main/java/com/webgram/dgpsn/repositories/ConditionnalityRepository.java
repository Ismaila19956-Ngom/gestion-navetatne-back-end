package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ConditionnalityEntity;
import com.webgram.dgpsn.entities.QConditionnalityEntity;


import java.util.Date;
import java.util.Objects;

@Repository
public interface ConditionnalityRepository extends JpaRepository<ConditionnalityEntity, Long>, QuerydslPredicateExecutor<ConditionnalityEntity> {
    default Page<ConditionnalityEntity> readAllByFilters(Pageable pageable, String libelle, Long conditionnalityTypeId
        , Long stateProgressId, Long agentId, Long projetId, Date date) {
        var booleanBuilder = new BooleanBuilder();
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(conditionnalityTypeId)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.conditionnalityType.id.eq(conditionnalityTypeId));
        }
        if(Objects.nonNull(stateProgressId)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.stateProgress.id.eq(stateProgressId));
        }
        if(Objects.nonNull(agentId)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.agent.id.eq(agentId));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(date)){
            booleanBuilder.and(QConditionnalityEntity.conditionnalityEntity.deadline.eq(date));
        }
        return findAll(booleanBuilder, pageable);
    }
}
