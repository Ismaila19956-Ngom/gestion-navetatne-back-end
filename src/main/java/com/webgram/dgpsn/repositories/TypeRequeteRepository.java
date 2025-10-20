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

import java.util.Objects;
import java.util.Optional;

@Repository
public interface TypeRequeteRepository extends JpaRepository<TypeRequeteEntity, Long>, QuerydslPredicateExecutor<TypeRequeteEntity> {
    Optional<IndicatorEntity> findByCode(String code);

    default Page<TypeRequeteEntity>
    readAllByFilters(Pageable pageable,
                     String code,
                     String libelle,
                     Long categorieRequeteId,
                     String sortBy,
                     Boolean ascending) {
        var booleanBuilder = new BooleanBuilder();

        Sort sort = Sort.unsorted();
        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QTypeRequeteEntity.typeRequeteEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QTypeRequeteEntity.typeRequeteEntity.libelle.containsIgnoreCase(libelle));
        }

        if(Objects.nonNull(categorieRequeteId)){
            booleanBuilder.and(QTypeRequeteEntity.typeRequeteEntity.categorieRequete.id.eq(categorieRequeteId));
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
