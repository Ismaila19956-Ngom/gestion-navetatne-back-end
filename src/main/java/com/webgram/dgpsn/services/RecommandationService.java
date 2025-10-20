package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.RecommandationDTO;

public interface RecommandationService {
    RecommandationDTO create(RecommandationDTO recommandationDTO);
    RecommandationDTO update(RecommandationDTO recommandationDTO);
    RecommandationDTO read(Long recommandationId);
    void delete(Long recommandationId);
    Page<RecommandationDTO> readAll(
            Pageable pageable,
            String libelle,
            String responsable,
            String deadline,
            Long issueLogId,
            Long riskId,
            Long assignmentId,
            Long statusId,
            Long projectId
    );
}
