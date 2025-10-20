package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.EvaluationEnvironnementaleEntity;
import com.webgram.dgpsn.entities.QEvaluationEnvironnementaleEntity;

import java.util.List;
import java.util.Objects;

@Repository
public interface EvaluationEnvironnementaleRepository extends JpaRepository<EvaluationEnvironnementaleEntity, Long>, QuerydslPredicateExecutor<EvaluationEnvironnementaleEntity> {

    List<EvaluationEnvironnementaleEntity> findByPromoteurId(Long promoteurId);

    List<EvaluationEnvironnementaleEntity> findByTitreProjetContainingIgnoreCase(String titreProjet);

    List<EvaluationEnvironnementaleEntity> findByProgrammeId(Long programmeId);

    default Page<EvaluationEnvironnementaleEntity> readAllByFiltering(
            Long programmeId,
            Long projetId,
            Long activiteId,
            Long directionId,
            Long promoteurId,
            String titreProjet,
            Pageable pageable,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(programmeId)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.programme.id.eq(programmeId));
        }
        if (Objects.nonNull(projetId)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.projet.id.eq(projetId));
        }
        if (Objects.nonNull(activiteId)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.activite.id.eq(activiteId));
        }
        if (Objects.nonNull(directionId)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.direction.id.eq(directionId));
        }
        if (Objects.nonNull(promoteurId)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.promoteur.id.eq(promoteurId));
        }
        if (StringUtils.isNotEmpty(titreProjet)) {
            booleanBuilder.and(QEvaluationEnvironnementaleEntity.evaluationEnvironnementaleEntity.titreProjet.containsIgnoreCase(titreProjet));
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Objects.nonNull(ascending) && ascending) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}