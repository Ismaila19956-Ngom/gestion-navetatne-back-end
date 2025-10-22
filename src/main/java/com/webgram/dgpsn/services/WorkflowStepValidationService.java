package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import com.webgram.dgpsn.models.WorkflowStepValidationDTO;

import java.util.Map;

public interface WorkflowStepValidationService {
    WorkflowStepValidationDTO createWorkflowStepValidation(WorkflowStepValidationDTO workflowStepValidationDTO);
    WorkflowStepValidationDTO updateWorkflowStepValidation(WorkflowStepValidationDTO workflowStepValidationDTO);
    WorkflowStepValidationDTO readWorkflowStepValidation(Long workflowStepValidationId);
    void deleteWorkflowStepValidation(Long workflowStepValidationId);
    Page<WorkflowStepValidationDTO> readAllWorkflowStepValidation(Map<String, String> searchParams, int page, int size);
    WorkflowStepValidationDTO readWorkflowUsers(Long profileId, Long workflowStepId);
}
