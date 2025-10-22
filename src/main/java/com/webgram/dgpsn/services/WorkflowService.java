package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import com.webgram.dgpsn.models.WorkflowDTO;

import java.util.Map;

public interface WorkflowService {
    WorkflowDTO createWorkflow(WorkflowDTO workflowDTO);
    WorkflowDTO updateWorkflow(WorkflowDTO workflowDTO);
    WorkflowDTO readWorkflow(Long workflowId);
    void deleteWorkflow(Long workflowId);
    Page<WorkflowDTO> readAllWorkflow(Map<String, String> searchParams, int page, int size);
}
