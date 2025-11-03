package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.entities.QCandidatEntity;
import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.entities.enums.StatusCadidature;
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
            StatusCadidature statusCandidature,  // NOUVEAU
            String sortBy,
            Boolean ascending
    ) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        // 1. Ignorer certains IDs
        if (Objects.nonNull(idsToIgnore) && !idsToIgnore.isEmpty()) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.id.notIn(idsToIgnore));
        }

        // 2. Filtres texte
        if (StringUtils.isNotBlank(nom)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.nom.containsIgnoreCase(nom));
        }
        if (StringUtils.isNotBlank(prenom)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.prenom.containsIgnoreCase(prenom));
        }
        if (StringUtils.isNotBlank(adresse)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.adresse.containsIgnoreCase(adresse));
        }

        // 3. Filtres enum
        if (Objects.nonNull(niveauEtude)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.niveauEtude.eq(niveauEtude));
        }
        if (Objects.nonNull(experience)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.experienceProfessionnelle.eq(experience));
        }

        // 4. FILTRE PAR STATUT (remplace les booléens)
        if (Objects.nonNull(statusCandidature)) {
            booleanBuilder.and(QCandidatEntity.candidatEntity.statusCandidature.eq(statusCandidature));
        }

        // 5. Tri
        if (StringUtils.isNotBlank(sortBy)) {
            sort = ascending != null && ascending ?
                    Sort.by(sortBy).ascending() :
                    Sort.by(sortBy).descending();
        }

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}