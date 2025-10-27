package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.entities.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecrutementRepository extends JpaRepository<RecrutementEntity, Long>, QuerydslPredicateExecutor<RecrutementEntity> {
}