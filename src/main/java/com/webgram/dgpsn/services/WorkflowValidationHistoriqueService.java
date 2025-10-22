package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import com.webgram.dgpsn.entities.WorkflowStepEntity;
import com.webgram.dgpsn.models.WorkflowValidationHistoriqueDTO;

import java.util.Map;
import java.util.Optional;

public interface WorkflowValidationHistoriqueService {
    WorkflowValidationHistoriqueDTO createHistorique(WorkflowValidationHistoriqueDTO historiqueDTO);
//    WorkflowValidationHistoriqueDTO updateHistorique(WorkflowValidationHistoriqueDTO historiqueDTO);
    WorkflowValidationHistoriqueDTO readHistorique(Long historiqueId);
    void deleteHistorique(Long historiqueId);
    Page<WorkflowValidationHistoriqueDTO> readAllHistorique(Map<String, String> searchParams, int page, int size);
    Optional<WorkflowStepEntity> findLastValidatedStepForLcr(int year, int month);
    Optional<WorkflowStepEntity> findLastValidatedStepForNsfr(int year, int month);

}
