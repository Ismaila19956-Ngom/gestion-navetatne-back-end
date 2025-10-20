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
import com.webgram.dgpsn.entities.QStatusEntity;
import com.webgram.dgpsn.entities.StatusEntity;
import com.webgram.dgpsn.entities.enums.StatusType;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Long>, QuerydslPredicateExecutor<StatusEntity> {
    Optional<StatusEntity> findByCode(String code);
    Stream<StatusEntity> findByStatusType(StatusType statusType);

    default Page<StatusEntity> readAllByFilters(Pageable pageable, String code, String libelle, StatusType statusType,String sortBy, Boolean ascending) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();
        if(StringUtils.isNotEmpty(code)){
            booleanBuilder.and(QStatusEntity.statusEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QStatusEntity.statusEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(statusType)){
            booleanBuilder.and(QStatusEntity.statusEntity.statusType.eq(statusType));
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
