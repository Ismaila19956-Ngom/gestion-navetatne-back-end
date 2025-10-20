package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.PreparationEntity;
import com.webgram.dgpsn.entities.QPreparationEntity;


import java.util.Date;
import java.util.Objects;

@Repository
public interface PreparationRepository extends JpaRepository<PreparationEntity, Long>, QuerydslPredicateExecutor<PreparationEntity> {
    //Optional<PreparationEntity> findByCode();

    default Page<PreparationEntity> readAllByFilters(Pageable pageable, String libelle, Date deadline, Long phaseId, Long agentId, Long projetId) {
        var booleanBuilder = new BooleanBuilder();
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QPreparationEntity.preparationEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(deadline)){
            booleanBuilder.and(QPreparationEntity.preparationEntity.deadline.eq(deadline));
        }
        if(Objects.nonNull(phaseId)){
            booleanBuilder.and(QPreparationEntity.preparationEntity.phase.id.eq(phaseId));
        }
        if(Objects.nonNull(agentId)){
            booleanBuilder.and(QPreparationEntity.preparationEntity.agent.id.eq(agentId));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QPreparationEntity.preparationEntity.projet.id.eq(projetId));
        }
        return findAll(booleanBuilder, pageable);
    }
}
