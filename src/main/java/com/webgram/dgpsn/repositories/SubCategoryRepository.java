package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long>, QuerydslPredicateExecutor<SubCategoryEntity> {
    Optional<SubCategoryEntity> findByCode(String code);
    default Page<SubCategoryEntity> readAllByFilters(Pageable pageable, String code, String libelle) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QSubCategoryEntity.subCategoryEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QSubCategoryEntity.subCategoryEntity.libelle.containsIgnoreCase(libelle));
        }
       // var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());

        return findAll(booleanBuider, pageable);
    }
}
