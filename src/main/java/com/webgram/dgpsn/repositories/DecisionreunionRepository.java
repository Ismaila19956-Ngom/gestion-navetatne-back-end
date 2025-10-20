package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.DecisionreunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface DecisionreunionRepository extends JpaRepository<DecisionreunionEntity, Long>, QuerydslPredicateExecutor<DecisionreunionEntity> {
    List<DecisionreunionEntity> findByReunionId(Long reunionId);
}