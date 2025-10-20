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
import com.webgram.dgpsn.entities.GouvernanceConformiteEntity;
import com.webgram.dgpsn.entities.QGouvernanceConformiteEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface GouvernanceConformiteRepository extends JpaRepository<GouvernanceConformiteEntity, Long>, QuerydslPredicateExecutor<GouvernanceConformiteEntity> {
    default Page<GouvernanceConformiteEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String etat,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeReferenceId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(code)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.code.containsIgnoreCase(code));
        }
        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(source)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.source.containsIgnoreCase(source));
        }
        if(Objects.nonNull(etat)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.etat.containsIgnoreCase(etat));
        }
        if(Objects.nonNull(startDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.startDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(endDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.endDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(categorieId)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.categorie.id.eq(categorieId));
        }
        if(Objects.nonNull(typeReferenceId)) {
            booleanBuider.and(QGouvernanceConformiteEntity.gouvernanceConformiteEntity.typeReference.id.eq(typeReferenceId));
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if ((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }
}

