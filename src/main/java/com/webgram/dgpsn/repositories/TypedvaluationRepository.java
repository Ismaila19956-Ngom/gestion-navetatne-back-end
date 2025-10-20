package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.TypedvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface TypedvaluationRepository extends JpaRepository<TypedvaluationEntity, Long>, QuerydslPredicateExecutor<TypedvaluationEntity> {
}