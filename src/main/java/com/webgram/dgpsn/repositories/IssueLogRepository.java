package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.entities.QIssueLogEntity;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.RisqueFinancierDto;
import com.webgram.dgpsn.models.responses.StatisticalDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface IssueLogRepository extends JpaRepository<IssueLogEntity, Long>, QuerydslPredicateExecutor<IssueLogEntity> {

    default Page<IssueLogEntity> readAllByFilters(
            Pageable pageable,
            String libelle,
            String description,
            String author,
            Date identificationDate,
            Date deadline,
            Date resolutionDate,
            Long projetId,
            Long assignmentId,
            Long criticityId,
            Long delayImpactId,
            Long financialImpactId,
            Long statusId,
            Long natureId
    ) {
        var booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.libelle.containsIgnoreCase(libelle));
        }
        if (StringUtils.isNotEmpty(description)) {
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.description.containsIgnoreCase(description));
        }
//        if(Objects.nonNull(criticityId)){
//            booleanBuilder.and(QIssueLogEntity.issueLogEntity.criticity.id.eq(criticityId));
//        }
//        if(Objects.nonNull(delayImpactId)){
//            booleanBuilder.and(QIssueLogEntity.issueLogEntity.delayImpact.id.eq(delayImpactId));
//        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.projet.id.eq(projetId));
        }
//        if(Objects.nonNull(financialImpactId)){
//            booleanBuilder.and(QIssueLogEntity.issueLogEntity.financialImpact.id.eq(financialImpactId));
//        }
        if(Objects.nonNull(assignmentId)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.assignment.id.eq(assignmentId));
        }
        if(Objects.nonNull(statusId)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.status.id.eq(statusId));
        }
        if(Objects.nonNull(identificationDate)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.identificationDate.eq(identificationDate));
        }
        if(Objects.nonNull(deadline)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.deadline.eq(deadline));
        }
        if(Objects.nonNull(resolutionDate)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.resolutionDate.eq(resolutionDate));
        }
        if(Objects.nonNull(natureId)){
            booleanBuilder.and(QIssueLogEntity.issueLogEntity.specificNature.id.eq(natureId));
        }

        return findAll(booleanBuilder, pageable);
    }
    @Query("select new com.webgram.dgpsn.models.responses.StatisticalDTO(i.status.libelle, count(i)) from IssueLogEntity i group by i.status.libelle")
    List<StatisticalDTO> findStatIssueLogByStatus();

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(i.status.libelle, COUNT(i)) " +
            "FROM IssueLogEntity i WHERE YEAR(i.identificationDate) = :year GROUP BY i.status.libelle")
    List<StatisticalDTO> findStatIssueLogByYear(@Param("year") Integer year);

    @Query("select new map(i.status.libelle as label, count(i) as value) " +
            "from IssueLogEntity i " +
            "where year(i.identificationDate) >= :startYear " +
            "group by i.status.libelle")
    List<Map<String, Object>> findStatIssueLogByLastFiveYears(@Param("startYear") Integer startYear);

    @Query("select count(i) from IssueLogEntity i where i.status.code = 'RES'")
    Long countResolvedIssueLog();

    @Query("select i from IssueLogEntity i where i.status.code = 'ECR'")
    List<IssueLogEntity> findStatIssueLogsNotResolved();

//    @Query("SELECT new sn.webg.suivievaluation.models.responses.RisqueFinancierDto(i.projet.name, SUM(i.risqueFinancier)) " +
////            "FROM IssueLogEntity i " +
////            "GROUP BY i.projet.name")
////    List<RisqueFinancierDto> getFinancialRiskByProject();
@Query("SELECT new com.webgram.dgpsn.models.responses.RisqueFinancierDto(i.projet.name, SUM(i.risqueFinancier)) " +
        "FROM IssueLogEntity i " +
        "WHERE i.projet.type = :typeProjet " +
        "GROUP BY i.projet.name")
List<RisqueFinancierDto> getFinancialRiskByProject(@Param("typeProjet") TypeProjet typeProjet);
    default List<IssueLogEntity> readListIssueLog(Long projectId) {
        var booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(QIssueLogEntity.issueLogEntity.projet.id.eq(projectId));
        return (List<IssueLogEntity>) findAll(booleanBuilder);
    }


@Query("SELECT i FROM IssueLogEntity i " +
        "WHERE i.status.code IN ('ENCOUR', 'BLOQ') " +
        "AND i.projet.id = :projetId " +
        "AND FUNCTION('YEAR', i.identificationDate) = :annee " +
        "AND (:startDate IS NULL OR i.identificationDate >= :startDate) " +
        "AND (:endDate IS NULL OR i.identificationDate <= :endDate)")
    List<IssueLogEntity> findOpenIssues(
        @Param("projetId") Long projetId,
        @Param("annee") Integer annee,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
);
    @Query("SELECT i FROM IssueLogEntity i " +
            "WHERE i.status.code = 'CLOS' " +
            "AND i.projet.id = :projetId " +
            "AND FUNCTION('YEAR', i.identificationDate) = :annee " +
            "AND (:startDate IS NULL OR i.identificationDate >= :startDate) " +
            "AND (:endDate IS NULL OR i.identificationDate <= :endDate)")
    List<IssueLogEntity> findClosedIssues(
            @Param("projetId") Long projetId,
            @Param("annee") Integer annee,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("SELECT s.nom, COUNT(i) FROM IssueLogEntity i JOIN i.supervisor s GROUP BY s.nom")
    List<Object[]> countIssueLogBySupervisor();

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalDTO(il.specificNature.libelle, COUNT(il)) " +
            "FROM IssueLogEntity il " +
            "GROUP BY il.specificNature.libelle")
    List<StatisticalDTO> countIssuesByNature();


}
