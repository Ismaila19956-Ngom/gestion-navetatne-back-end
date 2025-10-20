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
import com.webgram.dgpsn.entities.MarketFileEntity;
import com.webgram.dgpsn.entities.QMarketFileEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface MarketFileRepository extends JpaRepository<MarketFileEntity, Long>, QuerydslPredicateExecutor<MarketFileEntity> {
  Optional<MarketFileEntity> findById(long id);

    @Query("SELECT DISTINCT mf FROM MarketFileEntity mf LEFT JOIN FETCH mf.noteFileEntities")
    List<MarketFileEntity> findAllWithNotes();
    default Page<MarketFileEntity> readAllByFiltering(Pageable pageable, String fileNumber, String email,Long passationMarketId, String sortBy, Boolean ascending) {
        var booleanBuider = new BooleanBuilder();

        Sort sort = Sort.unsorted();

        if(StringUtils.isNotEmpty(fileNumber)) {
            booleanBuider.and(QMarketFileEntity.marketFileEntity.fileNumber.containsIgnoreCase(fileNumber));
        }

        if(StringUtils.isNotEmpty(email)) {
            booleanBuider.and(QMarketFileEntity.marketFileEntity.email.containsIgnoreCase(email));
        }
        if(Objects.nonNull(passationMarketId)) {
            booleanBuider.and(QMarketFileEntity.marketFileEntity.passationMarket.id.eq(passationMarketId));
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
