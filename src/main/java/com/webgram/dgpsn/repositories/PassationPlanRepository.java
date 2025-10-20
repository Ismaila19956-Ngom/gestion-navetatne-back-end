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
import com.webgram.dgpsn.entities.PassationPlanEntity;
import com.webgram.dgpsn.entities.QPassationPlanEntity;

import java.util.Objects;
import java.util.Optional;

@Repository
public interface PassationPlanRepository extends JpaRepository<PassationPlanEntity, Long>, QuerydslPredicateExecutor<PassationPlanEntity> {
  Optional<PassationPlanEntity> findById(long id);
//    Optional<List<PassationPlanEntity>> findByTypeStructure(PassationPlanEntity passationPlanEntity);
    default Page<PassationPlanEntity> readAllByFiltering(Pageable pageable, String reference, String libelle,Long managementUnitId, String sortBy, Boolean ascending) {
        var booleanBuider = new BooleanBuilder();

        Sort sort = Sort.unsorted();

        if(StringUtils.isNotEmpty(reference)) {
            booleanBuider.and(QPassationPlanEntity.passationPlanEntity.reference.containsIgnoreCase(reference));
        }

        if(StringUtils.isNotEmpty(libelle)) {
            booleanBuider.and(QPassationPlanEntity.passationPlanEntity.libelle.containsIgnoreCase(libelle));
        }

        if(Objects.nonNull(managementUnitId)) {
            booleanBuider.and(QPassationPlanEntity.passationPlanEntity.managementUnit.id.eq(managementUnitId));
        }


        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }


}
