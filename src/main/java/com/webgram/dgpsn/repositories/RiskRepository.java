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
import com.webgram.dgpsn.entities.QRiskEntity;
import com.webgram.dgpsn.entities.RiskEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public interface RiskRepository extends JpaRepository<RiskEntity, Long>, QuerydslPredicateExecutor<RiskEntity> {

    default Page<RiskEntity> readAllByFilters(Pageable pageable, String libelle, String author, String criticity
        , Long delayImpactId, Long projetId, Long financialImpactId, Long statusId, Date identificationDate, Date resolutionDate, Double probability, Long natureId) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(libelle)) {
            booleanBuilder.and(QRiskEntity.riskEntity.libelle.containsIgnoreCase(libelle));
        }
        if (StringUtils.isNotEmpty(author)) {
            booleanBuilder.and(QRiskEntity.riskEntity.author.containsIgnoreCase(author));
        }
//        if(StringUtils.isNotEmpty(criticity)){
//            booleanBuilder.and(QRiskEntity.riskEntity.criticity.eq(criticity));
//        }
//        if(Objects.nonNull(delayImpactId)){
//            booleanBuilder.and(QRiskEntity.riskEntity.delayImpact.id.eq(delayImpactId));
//        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QRiskEntity.riskEntity.projet.id.eq(projetId));
        }
//        if(Objects.nonNull(financialImpactId)){
//            booleanBuilder.and(QRiskEntity.riskEntity.financialImpact.id.eq(financialImpactId));
//        }
        if(Objects.nonNull(statusId)){
            booleanBuilder.and(QRiskEntity.riskEntity.status.id.eq(statusId));
        }
        if(Objects.nonNull(identificationDate)){
            booleanBuilder.and(QRiskEntity.riskEntity.identificationDate.eq(identificationDate));
        }
        if(Objects.nonNull(resolutionDate)){
            booleanBuilder.and(QRiskEntity.riskEntity.resolutionDate.eq(resolutionDate));
        }
        if(Objects.nonNull(probability)){
            booleanBuilder.and(QRiskEntity.riskEntity.probability.eq(probability));
        }
        if(Objects.nonNull(natureId)){
            booleanBuilder.and(QRiskEntity.riskEntity.nature.id.eq(natureId));
        }
       
        return findAll(booleanBuilder, pageable);
    }

    @Query("select r from RiskEntity r where r.id in(:ids)")
    Set<RiskEntity> findAllByIds(@Param("ids") List<Long> ids);
}
