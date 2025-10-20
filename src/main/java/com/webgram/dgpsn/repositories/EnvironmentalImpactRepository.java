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
import com.webgram.dgpsn.entities.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface EnvironmentalImpactRepository extends JpaRepository<EnvironmentalImpactEntity, Long>, QuerydslPredicateExecutor<EnvironmentalImpactEntity> {
    default Page<EnvironmentalImpactEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(code)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.code.containsIgnoreCase(code));
        }
        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(source)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.source.containsIgnoreCase(source));
        }
        if(Objects.nonNull(natureImpact)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.natureImpact.containsIgnoreCase(natureImpact));
        }
        if(Objects.nonNull(importanceImpact)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.importanceImpact.containsIgnoreCase(importanceImpact));
        }
        if(Objects.nonNull(startDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.startDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(endDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.endDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(categorieId)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.categorie.id.eq(categorieId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QEnvironmentalImpactEntity.environmentalImpactEntity.projet.id.eq(projetId));
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

