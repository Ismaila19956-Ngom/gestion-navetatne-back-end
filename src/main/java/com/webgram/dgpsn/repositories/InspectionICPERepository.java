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
import com.webgram.dgpsn.entities.InspectionICPEEntity;
import com.webgram.dgpsn.entities.QInspectionICPEEntity;
import com.webgram.dgpsn.models.responses.DataPoint;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface InspectionICPERepository extends JpaRepository<InspectionICPEEntity, Long>, QuerydslPredicateExecutor<InspectionICPEEntity> {

    default Page<InspectionICPEEntity> readAllByFiltering(
            Pageable pageable,
            String code,
            Date dateInspection,
            Long typeInspectionId,
            String ref,
            Long etablissementId,
            Long teamLeadId,
            Long complianceLevelId,
            Long environmentalRiskId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuilder = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if (Objects.nonNull(code)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.code.containsIgnoreCase(code));
        }
        if (Objects.nonNull(dateInspection)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.dateInspection.eq(dateInspection));
        }
        if (Objects.nonNull(typeInspectionId)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.typeInspection.id.eq(typeInspectionId));
        }
        if (Objects.nonNull(ref)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.ref.containsIgnoreCase(ref));
        }
        if (Objects.nonNull(etablissementId)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.etablissement.id.eq(etablissementId));
        }
        if (Objects.nonNull(teamLeadId)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.teamLead.id.eq(teamLeadId));
        }
        if (Objects.nonNull(complianceLevelId)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.complianceLevel.id.eq(complianceLevelId));
        }
        if (Objects.nonNull(environmentalRiskId)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.environmentalRisk.id.eq(environmentalRiskId));
        }
        if (Objects.nonNull(preparedBy)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.preparedBy.containsIgnoreCase(preparedBy));
        }
        if (Objects.nonNull(preparationDate)) {
            booleanBuilder.and(QInspectionICPEEntity.inspectionICPEEntity.preparationDate.eq(preparationDate));
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

    @Query("SELECT COUNT(i) FROM InspectionICPEEntity i WHERE YEAR(i.dateInspection) = :year")
    Long countByYear(int year);

    @Query("SELECT COUNT(i) FROM InspectionICPEEntity i WHERE i.complianceLevel.libelle IN ('Conforme', 'Partiellement conforme')")
    Long countConformes();

    @Query("SELECT COUNT(i) FROM InspectionICPEEntity i WHERE i.environmentalRisk.libelle = 'Élevé'")
    Long countRisquesEleves();

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(FUNCTION('DATE_FORMAT', i.dateInspection, '%Y-%m'), COUNT(i)) " +
            "FROM InspectionICPEEntity i WHERE i.dateInspection >= :oneYearAgo " +
            "GROUP BY FUNCTION('DATE_FORMAT', i.dateInspection, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', i.dateInspection, '%Y-%m')")
    List<DataPoint<String, Long>> countInspectionsMensuelles(Date oneYearAgo);

    @Query("SELECT new com.webgram.dgpsn.models.responses.DataPoint(cl.libelle, COUNT(i)) " +
            "FROM InspectionICPEEntity i JOIN i.complianceLevel cl " +
            "GROUP BY cl.libelle")
    List<DataPoint<String, Long>> countByComplianceLevel();

}