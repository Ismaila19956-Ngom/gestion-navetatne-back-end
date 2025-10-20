package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QQualiteAirEntity;
import com.webgram.dgpsn.entities.QualiteAirEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface QualiteAirRepository extends JpaRepository<QualiteAirEntity, Long>, QuerydslPredicateExecutor<QualiteAirEntity> {

    default Page<QualiteAirEntity> readAllByFiltering(
            Pageable pageable,
            Long stationId,
            Date measurementDate,
            String iqa,
            Long mainPollutantId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(stationId)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.station.id.eq(stationId));
        }
        if (Objects.nonNull(measurementDate)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.measurementDate.eq(measurementDate));
        }
        if (StringUtils.isNotEmpty(iqa)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.iqa.containsIgnoreCase(iqa));
        }
        if (Objects.nonNull(mainPollutantId)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.mainPollutant.id.eq(mainPollutantId));
        }
        if (Objects.nonNull(preparedBy)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.preparedBy.containsIgnoreCase(preparedBy));
        }
        if (Objects.nonNull(preparationDate)) {
            booleanBuilder.and(QQualiteAirEntity.qualiteAirEntity.preparationDate.eq(preparationDate));
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

    @Query("SELECT AVG(CASE qa.iqa " +
            "WHEN 'Bon' THEN 25.0 " +
            "WHEN 'Moyen' THEN 75.0 " +
            "WHEN 'Modéré' THEN 125.0 " +
            "WHEN 'Mauvais' THEN 175.0 " +
            "WHEN 'Très Mauvais' THEN 250.0 " +
            "ELSE 0.0 END) " +
            "FROM QualiteAirEntity qa WHERE qa.measurementDate >= :startDate")
    Double findAverageIqaSince(Date startDate);

    @Query("SELECT COUNT(DISTINCT qa.station) FROM QualiteAirEntity qa WHERE qa.measurementDate >= :startDate")
    Long countActiveStationsSince(Date startDate);

    @Query("SELECT COUNT(qa) FROM QualiteAirEntity qa WHERE qa.measurementDate >= :startDate")
    Long countMeasurementsSince(Date startDate);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(CAST(qa.measurementDate AS date), AVG(CASE qa.iqa " +
            "WHEN 'Bon' THEN 25.0 " +
            "WHEN 'Moyen' THEN 75.0 " +
            "WHEN 'Modéré' THEN 125.0 " +
            "WHEN 'Mauvais' THEN 175.0 " +
            "WHEN 'Très Mauvais' THEN 250.0 " +
            "ELSE 0.0 END)) " +
            "FROM QualiteAirEntity qa WHERE qa.measurementDate >= :startDate " +
            "GROUP BY CAST(qa.measurementDate AS date) ORDER BY CAST(qa.measurementDate AS date) ASC")
    List<DataPoint<Date, Double>> findIqaTrend(Date startDate);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(s.region.libelle, AVG(CASE qa.iqa " +
            "WHEN 'Bon' THEN 25.0 " +
            "WHEN 'Moyen' THEN 75.0 " +
            "WHEN 'Modéré' THEN 125.0 " +
            "WHEN 'Mauvais' THEN 175.0 " +
            "WHEN 'Très Mauvais' THEN 250.0 " +
            "ELSE 0.0 END)) " +
            "FROM QualiteAirEntity qa JOIN qa.station s " +
            "WHERE s.region IS NOT NULL " +
            "GROUP BY s.region.libelle")
    List<DataPoint<String, Double>> findAverageIqaByRegion();

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(mp.libelle, COUNT(qa)) " +
            "FROM QualiteAirEntity qa JOIN qa.mainPollutant mp " +
            "GROUP BY mp.libelle")
    List<DataPoint<String, Long>> countByMainPollutant();
}