package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SubSectorRepository extends JpaRepository<SubSectorEntity, Long>, QuerydslPredicateExecutor<SubSectorEntity> {
    Optional<SubSectorEntity> findByCode(String code);

    Set<SubSectorEntity> findAllByIdIn(Set<Long> ids);

    default Page<SubSectorEntity> readAllByFilters(Pageable pageable, String code, String libelle, Long sectorId) {
        var booleanBuider = new BooleanBuilder();

        if(StringUtils.isNotEmpty(code)) {
            booleanBuider.and(QSubSectorEntity.subSectorEntity.code.containsIgnoreCase(code));
        }
        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QSubSectorEntity.subSectorEntity.libelle.containsIgnoreCase(libelle));
        }
        if(Objects.nonNull(sectorId)) {
            booleanBuider.and(QSubSectorEntity.subSectorEntity.sector.id.eq(sectorId));
        }
        return findAll(booleanBuider, pageable);
    }

    @Query("select count(s.id) from SubSectorEntity s")
    Optional<Long> countTotalSubSector();
}
