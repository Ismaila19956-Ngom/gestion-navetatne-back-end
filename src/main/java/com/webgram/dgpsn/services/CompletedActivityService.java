package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.CompletedActivityDTO;

import java.util.Date;

public interface CompletedActivityService {
    CompletedActivityDTO create(CompletedActivityDTO completedActivityDTO);
    CompletedActivityDTO update(CompletedActivityDTO completedActivityDTO);
    CompletedActivityDTO read(Long completedActivityId);
    void delete(Long completedActivityId);
    Page<CompletedActivityDTO> readAll(Pageable pageable, String code, String libelle, Date dateDebut, Date dateFin, Long issueLogId);
}
