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
import com.webgram.dgpsn.entities.EnvironmentalIndicateurEntity;
import com.webgram.dgpsn.entities.QEnvironmentalIndicateurEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface EnvironmentalIndicateurRepository extends JpaRepository<EnvironmentalIndicateurEntity, Long>, QuerydslPredicateExecutor<EnvironmentalIndicateurEntity> {
    default Page<EnvironmentalIndicateurEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            String valeur,
            String source,
            String startDate,
            String endDate,
            Long indicateurId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(code)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.code.containsIgnoreCase(code));
        }
        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(valeur)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.valeur.containsIgnoreCase(valeur));
        }
        if(Objects.nonNull(source)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.source.containsIgnoreCase(source));
        }
        if(Objects.nonNull(startDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.startDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(endDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.endDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(indicateurId)) {
            booleanBuider.and(QEnvironmentalIndicateurEntity.environmentalIndicateurEntity.indicateur.id.eq(indicateurId));
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

