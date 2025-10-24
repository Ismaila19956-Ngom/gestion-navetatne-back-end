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
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
// import com.webgram.dgpsn.entities.QPlanComptableElementEntity; // Import QueryDSL simulé
import java.util.List;
import java.util.Objects;

@Repository
public interface PlanComptableElementRepository extends JpaRepository<PlanComptableElementEntity, Long>, QuerydslPredicateExecutor<PlanComptableElementEntity> {

    default Page<PlanComptableElementEntity> readAllByFiltering(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            String sortBy,
            Boolean ascending
    ) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();
        // QPlanComptableElementEntity qPlanComptableElementEntity = QPlanComptableElementEntity.planComptableElementEntity;

        if(Objects.nonNull(idsToIgnore)) {
            // booleanBuider.and(qPlanComptableElementEntity.id.notIn(idsToIgnore)); [12]
        }
        if(Objects.nonNull(type)) {
            // booleanBuider.and(qPlanComptableElementEntity.type.eq(type));
        }
        if(StringUtils.isNotEmpty(code)) {
            // booleanBuider.and(qPlanComptableElementEntity.code.containsIgnoreCase(code)); [12]
        }
        if(StringUtils.isNotEmpty(libelle)) {
            // booleanBuider.and(qPlanComptableElementEntity.libelle.containsIgnoreCase(libelle)); [12]
        }

        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if((Objects.nonNull(ascending))) {
            if (Boolean.TRUE.equals(ascending)) { sort = sort.ascending(); }
            else { sort = sort.descending(); }
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        // return findAll(booleanBuider, pageRequest);
        return findAll(pageRequest);
    }
}