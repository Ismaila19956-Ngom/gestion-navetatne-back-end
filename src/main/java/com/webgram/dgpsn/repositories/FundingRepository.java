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
import com.webgram.dgpsn.entities.FundingEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QFundingEntity;
import com.webgram.dgpsn.models.responses.StatisticalDTO;
import com.webgram.dgpsn.models.responses.StatisticalFundingDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface FundingRepository extends JpaRepository<FundingEntity, Long>, QuerydslPredicateExecutor<FundingEntity> {

    List<FundingEntity> findAllByProjetId(Long projetId);

    default Page<FundingEntity> readAllByFilters(
            Pageable pageable,
            String financingAgreement,
            Double amount,
            String cash,
            Double rate,
            Double equivalence,
            String approvalDate,
            String closingDate,
            String extentionDate,
            Long fundingTypeId,
            Long projetId,
            Long partnerProjetId,
            String sortBy,
            Boolean ascending
//            Long structureId
    ) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        Sort sort=Sort.unsorted();
        if(Objects.nonNull(financingAgreement)){
            booleanBuilder.and(QFundingEntity.fundingEntity.financingAgreement.eq(financingAgreement));
        }
        if(Objects.nonNull(amount)){
            booleanBuilder.and(QFundingEntity.fundingEntity.amount.eq(amount));
        }
        if(StringUtils.isNotEmpty(cash)){
            booleanBuilder.and(QFundingEntity.fundingEntity.cash.eq(cash));
        }
        if(Objects.nonNull(rate)){
            booleanBuilder.and(QFundingEntity.fundingEntity.rate.eq(rate));
        }
        if(Objects.nonNull(equivalence)){
            booleanBuilder.and(QFundingEntity.fundingEntity.equivalence.eq(equivalence));
        }
        if(Objects.nonNull(approvalDate)){
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(approvalDate);

            booleanBuilder.and(QFundingEntity.fundingEntity.approvalDate.eq(formatStartDate));
        }
        if(Objects.nonNull(closingDate)){
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(closingDate);

            booleanBuilder.and(QFundingEntity.fundingEntity.closingDate.eq(formatStartDate));
        }

        if(Objects.nonNull(extentionDate)){
            var formatStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(extentionDate);

            booleanBuilder.and(QFundingEntity.fundingEntity.extentionDate.eq(formatStartDate));
        }
        if(Objects.nonNull(fundingTypeId)){
            booleanBuilder.and(QFundingEntity.fundingEntity.fundingType.id.eq(fundingTypeId));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QFundingEntity.fundingEntity.projet.id.eq(projetId));
        }
        if(Objects.nonNull(partnerProjetId)){
            booleanBuilder.and(QFundingEntity.fundingEntity.partnerProjet.id.eq(partnerProjetId));
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

    @Query("select sum(f.equivalence) from FundingEntity f ")
    Long getTotalFinancement();

    List<FundingEntity> findByProjet(ManagementUnitEntity projet);

//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalFundingDTO(f.projet.subSectors.libelle, avg(f.equivalence)) from FundingEntity f group by f.projet.subSectors.libelle")
//    List<StatisticalFundingDTO> findAverageFundingBySector();


//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalFundingDTO(f.projet.subSector.libelle, sum(f.equivalence)) from FundingEntity f group by f.projet.subSector.libelle")
//    List<StatisticalFundingDTO> findTotalFundingBySector();

    @Query("select new com.webgram.dgpsn.models.responses.StatisticalDTO(f.partnerProjet.structure.nom, count(f.projet)) from FundingEntity f group by f.partnerProjet.structure.nom")
    List<StatisticalDTO> findStatByPartner();

    @Query("select max(f.equivalence) from FundingEntity f")
    Optional<Long> calculateTotalFunding();

    //Issa sow
    @Query("select max(f.equivalence) from FundingEntity f")
    Optional<Double> readTotalFunding();

//    @Query("select new sn.webg.suivievaluation.models.landingPage.ProjectFundingDTO(f.projet.id, f.projet.name, f.projet.description, f.projet.dateDebut, f.projet.dateFin, f.projet.equivalence, f.projet.subSector.sector.libelle, f.projet.subSector.libelle, f.projet.structure.nom, avg(f.equivalence))" +
//            "from FundingEntity f group by f.projet.id order by f.projet.id desc")
//    List<ProjectFundingDTO> averageFundingByProject();

//@Query("select new com.webgram.dgpsn.models.responses.StatisticalFundingDTO(f.partnerProjet.structure.code, avg(datediff(current_date, f.projet.actualStartDate) / 365)) from FundingEntity f group by f.partnerProjet.structure.code")
//List<StatisticalFundingDTO> avgAgeProjectByPartner();

//    @Query("select new sn.webg.suivievaluation.models.responses.StatisticalFundingDTO(f.partnerProjet.structure.nom, sum(f.equivalence)) from FundingEntity f group by f.partnerProjet.structure.nom")
//    List<StatisticalFundingDTO> findTotalFundingByPartner();

}
