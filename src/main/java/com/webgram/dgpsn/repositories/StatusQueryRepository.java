package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.QStatusQueryEntity;
import com.webgram.dgpsn.entities.StatusQueryEntity;

import java.util.Date;
import java.util.Objects;

@Repository
public interface StatusQueryRepository extends JpaRepository<StatusQueryEntity, Long>, QuerydslPredicateExecutor<StatusQueryEntity> {

    default Page<StatusQueryEntity> readAllByFilters(Pageable pageable, String libelle, String responsable
        , Date identificationDate, Date deadline, Long queryId) {
        var booleanBuilder = new BooleanBuilder();
        if(StringUtils.isNotEmpty(libelle)){
            booleanBuilder.and(QStatusQueryEntity.statusQueryEntity.libelle.containsIgnoreCase(libelle));
        }
        if(StringUtils.isNotEmpty(responsable)){
            booleanBuilder.and(QStatusQueryEntity.statusQueryEntity.responsable.containsIgnoreCase(responsable));
        }
        if(Objects.nonNull(identificationDate)){
            booleanBuilder.and(QStatusQueryEntity.statusQueryEntity.identificationDate.eq(identificationDate));
        }
        if(Objects.nonNull(deadline)){
            booleanBuilder.and(QStatusQueryEntity.statusQueryEntity.deadline.eq(deadline));
        }

        if(Objects.nonNull(queryId)){
            booleanBuilder.and(QStatusQueryEntity.statusQueryEntity.query.id.eq(queryId));
        }

        return findAll(booleanBuilder, pageable);
    }
}
