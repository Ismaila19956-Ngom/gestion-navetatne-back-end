package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.WorkflowStepValidationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkflowStepValidationRepository extends JpaRepository<WorkflowStepValidationEntity, Long>, QuerydslPredicateExecutor<WorkflowStepValidationEntity> {
    Optional<WorkflowStepValidationEntity> findByWorkflowStepId(Long stepId);
}
