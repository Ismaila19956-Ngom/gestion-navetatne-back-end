package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.QLabelEntity;
import com.webgram.dgpsn.entities.enums.ReferentielType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Long>, QuerydslPredicateExecutor<LabelEntity> {

    default Page<LabelEntity> readByFiltering(Pageable pageable, ReferentielType referentielType, String label, String code, String sortDirection, String sortProperty) {
        var booleanBuilder = new BooleanBuilder();
        var direction = Sort.Direction.DESC;
        var property = "id";

        if(Objects.nonNull(referentielType)) {
            booleanBuilder.and(QLabelEntity.labelEntity.referentielType.eq(referentielType));
        }
        if(StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QLabelEntity.labelEntity.libelle.containsIgnoreCase(label));
        }
        if(StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QLabelEntity.labelEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotBlank(sortDirection)) {
            direction = sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
        if(StringUtils.isNotBlank(sortProperty)) {
            property = sortProperty;
        }

        Sort sortLabel = Sort.by(direction, property);

        Pageable pageSorted = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortLabel);

        return findAll(booleanBuilder, pageSorted);
    }

    Optional<LabelEntity> findByCode(String code);

    @Query("select l from LabelEntity l where l.id in(:ids)")
    Set<LabelEntity> findAllByIds(@Param("ids") List<Long> ids);

    Optional<LabelEntity> findByCodeAndLibelle(String code, String libelle);


}
