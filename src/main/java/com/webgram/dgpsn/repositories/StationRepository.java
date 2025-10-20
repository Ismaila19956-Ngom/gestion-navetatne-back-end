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
import com.webgram.dgpsn.entities.QStationEntity;
import com.webgram.dgpsn.entities.StationEntity;

import java.util.Objects;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long>, QuerydslPredicateExecutor<StationEntity> {

    default Page<StationEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            String name,
            Long typeId,
            Long regionId,
            Long departementId,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(code)) {
            booleanBuilder.and(QStationEntity.stationEntity.code.containsIgnoreCase(code));
        }
        if (Objects.nonNull(name)) {
            booleanBuilder.and(QStationEntity.stationEntity.name.containsIgnoreCase(name));
        }
        if (Objects.nonNull(typeId)) {
            booleanBuilder.and(QStationEntity.stationEntity.type.id.eq(typeId));
        }
        if (Objects.nonNull(regionId)) {
            booleanBuilder.and(QStationEntity.stationEntity.region.id.eq(regionId));
        }
        if (Objects.nonNull(departementId)) {
            booleanBuilder.and(QStationEntity.stationEntity.departement.id.eq(departementId));
        }
        if (StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if (Objects.nonNull(ascending) && ascending) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuilder, pageRequest);
    }
}