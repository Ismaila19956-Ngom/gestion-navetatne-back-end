package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.entities.QCandidatEntity;
import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface CandidatRepository extends JpaRepository<CandidatEntity, Long>, QuerydslPredicateExecutor<CandidatEntity> {

    Optional<CandidatEntity> findByMatricule(String matricule);

    default Page<CandidatEntity> readAllByFiltering(
            Pageable pageable,
            List<Long> idsToIgnore,
            String nom,
            String prenom,
            String adresse,
            NiveauEtude niveauEtude,
            ExperienceProfessionnelle experience,
            Boolean preselectionneEntretien,
            Boolean selectionne,
            String sortBy,
            Boolean ascending
    ) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(idsToIgnore)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.id.notIn(idsToIgnore));
        }
        if (StringUtils.isNotEmpty(nom)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.nom.containsIgnoreCase(nom));
        }
        if (StringUtils.isNotEmpty(prenom)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.prenom.containsIgnoreCase(prenom));
        }
        if (StringUtils.isNotEmpty(adresse)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.adresse.containsIgnoreCase(adresse));
        }
        if (Objects.nonNull(niveauEtude)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.niveauEtude.eq(niveauEtude));
        }
        if (Objects.nonNull(experience)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.experienceProfessionnelle.eq(experience));
        }
        if (Objects.nonNull(preselectionneEntretien)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.preselectionneEntretien.eq(preselectionneEntretien));
        }
        if (Objects.nonNull(selectionne)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.selectionne.eq(selectionne));
        }

        // Aucun filtre sur les compétences pour le moment
        // booleanBuilder.and(QCandidatEntity.candidatEntity.competences.contains(...));

        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Boolean.TRUE.equals(ascending)) {
            sort = sort.ascending();
        } else if (Boolean.FALSE.equals(ascending)) {
            sort = sort.descending();
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}
