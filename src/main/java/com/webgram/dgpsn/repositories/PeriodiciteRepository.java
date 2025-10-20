package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.PeriodiciteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface PeriodiciteRepository extends JpaRepository<PeriodiciteEntity, Long>, QuerydslPredicateExecutor<PeriodiciteEntity> {
    List<PeriodiciteEntity> findByPeriodeId(Long periodeId);
}