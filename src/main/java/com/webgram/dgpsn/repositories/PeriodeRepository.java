package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.PeriodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface PeriodeRepository extends JpaRepository<PeriodeEntity, Long>, QuerydslPredicateExecutor<PeriodeEntity> {
}