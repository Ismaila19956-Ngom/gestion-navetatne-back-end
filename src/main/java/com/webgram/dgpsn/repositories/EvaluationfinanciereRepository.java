package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.EvaluationfinanciereEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface EvaluationfinanciereRepository extends JpaRepository<EvaluationfinanciereEntity, Long>, QuerydslPredicateExecutor<EvaluationfinanciereEntity> {
    List<EvaluationfinanciereEntity> findByEntrepriseId(Long entrepriseId);
}