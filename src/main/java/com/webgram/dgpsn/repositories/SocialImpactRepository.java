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
import com.webgram.dgpsn.entities.SocialImpactEntity;
import com.webgram.dgpsn.entities.QSocialImpactEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Repository
public interface SocialImpactRepository extends JpaRepository<SocialImpactEntity, Long>, QuerydslPredicateExecutor<SocialImpactEntity> {
    default Page<SocialImpactEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String libelle,
            Number nbPersonnesAffectees,
            Number nbMenagesAffectees,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeImpactId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(code)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.code.containsIgnoreCase(code));
        }
        if(Objects.nonNull(libelle)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(nbPersonnesAffectees)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.nbPersonnesAffectees.eq(nbPersonnesAffectees));
        }
        if(Objects.nonNull(nbMenagesAffectees)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.nbMenagesAffectees.eq(nbMenagesAffectees));
        }
        if(Objects.nonNull(source)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.source.containsIgnoreCase(source));
        }
        if(Objects.nonNull(natureImpact)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.natureImpact.containsIgnoreCase(natureImpact));
        }
        if(Objects.nonNull(importanceImpact)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.importanceImpact.containsIgnoreCase(importanceImpact));
        }
        if(Objects.nonNull(startDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                booleanBuider.and(QSocialImpactEntity.socialImpactEntity.startDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(endDate)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                booleanBuider.and(QSocialImpactEntity.socialImpactEntity.endDate.eq(date));
            }catch (ParseException ex) {
                new ParseException(ex.getMessage(), ex.getErrorOffset());
            }
        }
        if(Objects.nonNull(categorieId)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.categorie.id.eq(categorieId));
        }
        if(Objects.nonNull(typeImpactId)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.typeImpact.id.eq(typeImpactId));
        }
        if(Objects.nonNull(projetId)) {
            booleanBuider.and(QSocialImpactEntity.socialImpactEntity.projet.id.eq(projetId));
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

