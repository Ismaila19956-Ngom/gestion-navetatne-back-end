package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.models.WorkflowStepDTO;

import java.util.Map;

public interface WorkflowStepService {
    WorkflowStepDTO createWorkflowStep(WorkflowStepDTO workflowStepDTO);
    WorkflowStepDTO updateWorkflowStep(WorkflowStepDTO workflowStepDTO);
    WorkflowStepDTO readWorkflowStep(Long workflowStepId);
    void deleteWorkflowStep(Long workflowStepId);
    Page<WorkflowStepDTO> readAllWorkflowStep(Map<String, String> searchParams, int page, int size);
    WorkflowStepEntity getNextStep(Long workflowId, Integer stepOrdre);
}
