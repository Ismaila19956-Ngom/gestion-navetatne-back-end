package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.EvaluationperformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface EvaluationperformanceRepository extends JpaRepository<EvaluationperformanceEntity, Long>, QuerydslPredicateExecutor<EvaluationperformanceEntity> {
    List<EvaluationperformanceEntity> findByEntrepriseId(Long entrepriseId);
}