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
import com.webgram.dgpsn.entities.PassationMarketCritereEntity;
import com.webgram.dgpsn.entities.QPassationMarketCritereEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface PassationMarketCritereRepository extends JpaRepository<PassationMarketCritereEntity, Long>, QuerydslPredicateExecutor<PassationMarketCritereEntity> {
  Optional<PassationMarketCritereEntity> findById(long id);

  Optional<List<PassationMarketCritereEntity>> findByPassationMarketEntityId(long id);
    default Page<PassationMarketCritereEntity> readAllByFiltering(Pageable pageable, Double ponderation, String expectedValue,Long passationMarketId, String sortBy, Boolean ascending) {
        var booleanBuider = new BooleanBuilder();

        Sort sort = Sort.unsorted();

        if(StringUtils.isNotEmpty(expectedValue)) {
            booleanBuider.and(QPassationMarketCritereEntity.passationMarketCritereEntity.expectedValue.containsIgnoreCase(expectedValue));
        }

        if(Objects.nonNull(ponderation)) {
            booleanBuider.and(QPassationMarketCritereEntity.passationMarketCritereEntity.ponderation.eq(ponderation));
        }

        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }

        if(Objects.nonNull(passationMarketId)){
            booleanBuider.and(QPassationMarketCritereEntity.passationMarketCritereEntity.passationMarketEntity.id.eq(passationMarketId));
        }

        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return findAll(booleanBuider, pageRequest);
    }


}
