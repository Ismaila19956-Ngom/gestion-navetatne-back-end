package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.DecisionagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface DecisionagRepository extends JpaRepository<DecisionagEntity, Long>, QuerydslPredicateExecutor<DecisionagEntity> {
    List<DecisionagEntity> findByAssemblegeneralId(Long assemblegeneralId);
}