package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface SpecificNatureRepository extends JpaRepository<SpecificNatureEntity, Long>, QuerydslPredicateExecutor<SpecificNatureEntity> {
    Optional<SpecificNatureEntity> findByCode(String code);

    default Page<SpecificNatureEntity> readAllByFilters(Pageable pageable, String code, String libelle, Long natureId) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QSpecificNatureEntity.specificNatureEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QSpecificNatureEntity.specificNatureEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(natureId)) {
            booleanBuider.and(QSpecificNatureEntity.specificNatureEntity.nature.id.eq(natureId));
        }
        return findAll(booleanBuider, pageable);
    }
}
