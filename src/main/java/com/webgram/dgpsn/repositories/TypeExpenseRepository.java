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
import com.webgram.dgpsn.entities.QTypeExpenseEntity;
import com.webgram.dgpsn.entities.TypeExpenseEntity;

import java.util.Objects;

@Repository
public interface TypeExpenseRepository extends JpaRepository<TypeExpenseEntity, Long>, QuerydslPredicateExecutor<TypeExpenseEntity> {



    default Page<TypeExpenseEntity> readAllByFilters(Pageable pageable, String code, String libelle, Long categorieDepenseId,String sortBy,
                                                   Boolean ascending) {
        var booleanBuilder = new BooleanBuilder();

        Sort sort = Sort.unsorted();
        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QTypeExpenseEntity.typeExpenseEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QTypeExpenseEntity.typeExpenseEntity.libelle.containsIgnoreCase(libelle));
        }

        if(Objects.nonNull(categorieDepenseId)){
            booleanBuilder.and(QTypeExpenseEntity.typeExpenseEntity.categorieDepense.id.eq(categorieDepenseId));
        }
        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}
