package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStepEntity, Long>, QuerydslPredicateExecutor<WorkflowStepEntity> {
    Optional<WorkflowStepEntity> findByCode(String code);
    List<WorkflowStepEntity> findByWorkflowId(Long workflowId);
    boolean existsByOrdreAndWorkflowId(Integer ordre, Long workflowId);
    Optional<WorkflowStepEntity> findTopByWorkflowIdOrderByOrdreAsc(Long workflowId);
//    @Query("select step from WorkflowStepEntity step where step.workflow.id = :workflowId and step.ordre > :stepOrdre order by step.ordre asc")
    Optional<WorkflowStepEntity> findFirstByWorkflowIdAndOrdreGreaterThanOrderByOrdreAsc (@Param("workflowId") Long workflowId, @Param("stepOrdre") Integer stepOrdre);

    Optional<WorkflowStepEntity> findByWorkflowIdAndOrdre(Long workflowId, Integer ordre);
    Optional<WorkflowStepEntity> findTopByWorkflow_TypeOrderByOrdreDesc(WorkflowType type);

}
