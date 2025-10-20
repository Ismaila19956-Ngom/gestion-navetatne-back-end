package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.DepartementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface DepartementRepository extends JpaRepository<DepartementEntity, Long>, QuerydslPredicateExecutor<DepartementEntity> {
    List<DepartementEntity> findByRegionId(Long regionId);
}