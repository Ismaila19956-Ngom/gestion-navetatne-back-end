package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.WorkflowStepValidationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowStepValidationUserRepository extends JpaRepository<WorkflowStepValidationUserEntity, Long>, QuerydslPredicateExecutor<WorkflowStepValidationUserEntity> {
    List<WorkflowStepValidationUserEntity> findAllByUserIdAndWorkflowStepValidationId(Long userId, Long validationId);

    List<WorkflowStepValidationUserEntity> findAllByWorkflowStepValidationId(Long stepValidationId);

    void deleteAllByWorkflowStepValidationId(Long stepValidationId);

    @Query("select config from WorkflowStepValidationUserEntity config where config.workflowStepValidation.workflowStep.id = :stepId and config.user.id = :userId")
    Optional<WorkflowStepValidationUserEntity> findUserConfig(@Param("stepId") Long stepId, @Param("userId") Long userId);
}