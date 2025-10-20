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
import com.webgram.dgpsn.entities.IndicatorEntity;
import com.webgram.dgpsn.entities.QIndicatorEntity;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface IndicatorRepository extends JpaRepository<IndicatorEntity, Long>, QuerydslPredicateExecutor<IndicatorEntity> {
    Optional<IndicatorEntity> findByCode(String code);

    default Page<IndicatorEntity> readAllByFilters(Pageable pageable, String code, String libelle, Long typeId, Long indicatorTypeId,String sortBy,
                                                   Boolean ascending) {
        var booleanBuilder = new BooleanBuilder();

        Sort sort = Sort.unsorted();
        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QIndicatorEntity.indicatorEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QIndicatorEntity.indicatorEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(typeId)){
            booleanBuilder.and(QIndicatorEntity.indicatorEntity.unit.id.eq(typeId));
        }
        if(Objects.nonNull(indicatorTypeId)){
            booleanBuilder.and(QIndicatorEntity.indicatorEntity.indicatorType.id.eq(indicatorTypeId));
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
