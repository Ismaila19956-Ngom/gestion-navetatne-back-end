package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.FundingTypeConfig;
import com.webgram.dgpsn.models.responses.StatisticalFundingDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface FundingConfigRepository extends JpaRepository<FundingConfigEntity, Long>, QuerydslPredicateExecutor<FundingConfigEntity> {

    default Page<FundingConfigEntity> readAllByFiltering(
            Pageable pageable,
            String libelle,
            String annee,
            FundingTypeConfig fundingTypeConfig,
            String startingDate,
            String endingDate,
            String estimatedAmount,
            String actualAmount,
            Long managementUnitId
    ) throws ParseException {
        var booleanBuider = new BooleanBuilder();

        if (Objects.nonNull(libelle)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.libelle.equalsIgnoreCase(libelle));
        }
        if (Objects.nonNull(annee)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.annee.equalsIgnoreCase(annee));
        }
        if (Objects.nonNull(fundingTypeConfig)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.fundingTypeConfig.eq(fundingTypeConfig));
        }

        if (Objects.nonNull(startingDate)) {
            var formatStartingDate = new SimpleDateFormat("yyyy-MM-dd").parse(startingDate);
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.startingDate.eq(formatStartingDate));
        }
        if (Objects.nonNull(endingDate)) {
            var formatEndingDate = new SimpleDateFormat("yyyy-MM-dd").parse(endingDate);
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.endingDate.eq(formatEndingDate));
        }
        if (Objects.nonNull(estimatedAmount)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.estimatedAmount.equalsIgnoreCase(estimatedAmount));
        }
        if (Objects.nonNull(actualAmount)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.actualAmount.stringValue().equalsIgnoreCase(actualAmount));;
        }

        if (Objects.nonNull(managementUnitId)) {
            booleanBuider.and(QFundingConfigEntity.fundingConfigEntity.managementUnit.id.eq(managementUnitId));
        }

        return findAll(booleanBuider, pageable);
    }

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(p.managementUnit.name, SUM(p.actualAmount)) FROM FundingConfigEntity p WHERE p.managementUnit.type = com.webgram.dgpsn.entities.enums.TypeProjet.PROJECT AND " +
            "p.fundingTypeConfig = com.webgram.dgpsn.entities.enums.FundingTypeConfig.FINANCING_NEED " +
            "GROUP BY p.managementUnit.name")
    List<StatisticalFundingDTO> totalBesoinsByProjects();

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(p.managementUnit.name, SUM(p.actualAmount)) FROM FundingConfigEntity p WHERE p.managementUnit.type = com.webgram.dgpsn.entities.enums.TypeProjet.PROJECT AND " +
            "p.fundingTypeConfig = com.webgram.dgpsn.entities.enums.FundingTypeConfig.MOBILISATION " +
            "GROUP BY p.managementUnit.name")
    List<StatisticalFundingDTO> totalMobilisationsByProjects();

    @Query("SELECT new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(p.managementUnit.name, SUM(p.actualAmount)) FROM FundingConfigEntity p WHERE p.managementUnit.type = com.webgram.dgpsn.entities.enums.TypeProjet.PROJECT AND " +
            "p.fundingTypeConfig = com.webgram.dgpsn.entities.enums.FundingTypeConfig.EXECUTION " +
            "GROUP BY p.managementUnit.name")
    List<StatisticalFundingDTO> totalExecutionByProjects();

//    @Query("SELECT f FROM FundingConfigEntity f " +
//            "WHERE f.fundingTypeConfig = 'FINANCING_NEED' " +
//            "AND f.managementUnit.id = :projetId " +
//            "AND f.annee = :annee " +
//            "AND (:startDate IS NULL OR f.startingDate >= :startDate) " +
//            "AND (:endDate IS NULL OR f.endingDate <= :endDate)")
//    List<FundingConfigEntity> findFundingConfigs(
//            @Param("projetId") Long projetId,
//            @Param("annee") String annee,
//            @Param("startDate") Date startDate,
//            @Param("endDate") Date endDate
//    );
@Query("SELECT f FROM FundingConfigEntity f " +
        "WHERE f.fundingTypeConfig = 'FINANCING_NEED' " +
        "AND f.managementUnit.id = :projetId " +
        "AND f.annee = :annee " +
        "AND (:startDate IS NULL OR f.startingDate <= :endDate) " +
        "AND (:endDate IS NULL OR f.endingDate >= :startDate)")
List<FundingConfigEntity> findFundingConfigs(
        @Param("projetId") Long projetId,
        @Param("annee") String annee,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
);

    @Query("SELECT SUM(f.actualAmount) FROM FundingConfigEntity f WHERE f.fundingTypeConfig = :fundingTypeConfig AND f.managementUnit.id = :projectId AND f.annee = :year")
    Double totalFundingConfigByProjectAndYear(@Param("fundingTypeConfig") FundingTypeConfig fundingTypeConfig, @Param("projectId") Long projectId, @Param("year") String year);

    @Query("SELECT SUM(f.actualAmount) FROM FundingConfigEntity f WHERE f.fundingTypeConfig = :fundingTypeConfig AND f.managementUnit.id = :projectId")
    Double totalFundingConfigByProject(@Param("fundingTypeConfig") FundingTypeConfig fundingTypeConfig, @Param("projectId") Long projectId);

}
