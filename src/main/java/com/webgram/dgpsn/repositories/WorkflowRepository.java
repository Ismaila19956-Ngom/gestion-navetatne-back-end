package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.WorkflowEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowEntity, Long>, QuerydslPredicateExecutor<WorkflowEntity> {
    Optional<WorkflowEntity> findByCode(String code);
    Optional<WorkflowEntity> findByType(WorkflowType type);
    boolean existsByTypeAndIdNot(WorkflowType type, Long id);

}
