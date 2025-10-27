package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.RealisationDTO;

import java.time.LocalDate;
import java.util.List;

public interface RealisationService {
    RealisationDTO create(RealisationDTO realisationDTO);
    List<RealisationDTO> createMultiple(List<RealisationDTO> realisationDTOs);
    RealisationDTO update(RealisationDTO realisationDTO);
    RealisationDTO read(Long realisationId);
    List<RealisationDTO> findByBudgetId(Long budgetId);
    void delete(Long realisationId);
    Page<RealisationDTO> readAll(
            Pageable pageable,
            String code,
            Long realisationsId,
            Double montant,
            LocalDate date,
            String fournisseur,
            String numeroBon,
            String numeroBE,
            String numeroMandat,
            String description,
            Long ligneBudgetaireId
    );
}