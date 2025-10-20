package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.CompletionRateEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QCompletionRateEntity;
import com.webgram.dgpsn.entities.enums.Period;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public interface CompletionRateRepository extends JpaRepository<CompletionRateEntity, Long>, QuerydslPredicateExecutor<CompletionRateEntity> {

    default Page<CompletionRateEntity> readAllByFilters(Pageable pageable, Long projetId
        , Period period, Double targetValue, Double valueReched, Integer year, Date startDate, Date endDate) {
        var booleanBuilder = new BooleanBuilder();

        if(Objects.nonNull(year)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.year.eq(year));
        }
        if(Objects.nonNull(targetValue)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.targetValue.eq(targetValue));
        }
        if(Objects.nonNull(valueReched)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.valueReched.eq(valueReched));
        }
        if(Objects.nonNull(projetId)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.managementUnit.id.eq(projetId));
        }
        if(Objects.nonNull(period)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.period.eq(period));
        }
        if(Objects.nonNull(startDate)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.startDate.eq(startDate));
        }
        if(Objects.nonNull(endDate)){
            booleanBuilder.and(QCompletionRateEntity.completionRateEntity.endDate.eq(endDate));
        }
        return findAll(booleanBuilder, pageable);
    }

    List<CompletionRateEntity> findByManagementUnitAndYear(ManagementUnitEntity managementUnit, Integer year);

    @Query("select c " +
            "from CompletionRateEntity c where c.managementUnit.parent.id = :projectId and c.period = :period and c.year = :year")
    List<CompletionRateEntity> getTauxByProject(@Param("projectId") Long projectId, @Param("period") Period period, @Param("year") Integer year);


}
