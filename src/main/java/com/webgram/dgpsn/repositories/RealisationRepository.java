package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.models.RealisationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QRealisationEntity;
import com.webgram.dgpsn.entities.RealisationEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public interface RealisationRepository extends JpaRepository<RealisationEntity, Long>, QuerydslPredicateExecutor<RealisationEntity> {

    default Page<RealisationEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            Long realisationsId,
            Double montant,
            LocalDate date,
            String fournisseur,
            String numeroBon,
            String numeroBE,
            String numeroMandat,
            String description,
            Long ligneBudgetaireId
    ) {
        var booleanBuilder = new BooleanBuilder();

        if (Objects.nonNull(code)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.code.equalsIgnoreCase(code));
        }
        if (Objects.nonNull(realisationsId)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.realisations.id.eq(realisationsId));
        }
        if (Objects.nonNull(montant)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.montant.eq(montant));
        }
        if (Objects.nonNull(date)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.date.eq(date));
        }
        if (Objects.nonNull(fournisseur)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.fournisseur.equalsIgnoreCase(fournisseur));
        }
        if (Objects.nonNull(numeroBon)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.numeroBon.equalsIgnoreCase(numeroBon));
        }
        if (Objects.nonNull(numeroBE)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.numeroBE.equalsIgnoreCase(numeroBE));
        }
        if (Objects.nonNull(numeroMandat)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.numeroMandat.equalsIgnoreCase(numeroMandat));
        }
        if (Objects.nonNull(description)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.description.containsIgnoreCase(description));
        }
        if (Objects.nonNull(ligneBudgetaireId)) {
            booleanBuilder.and(QRealisationEntity.realisationEntity.ligneBudgetaire.id.eq(ligneBudgetaireId));
        }

        return findAll(booleanBuilder, pageable);
    }

    List<RealisationEntity> findByLigneBudgetaireIdIn(List<Long> ligneBudgetaireIds);
}