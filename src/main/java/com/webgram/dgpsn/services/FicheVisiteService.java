package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.FicheVisiteDTO;

import java.util.List;
import java.util.Map;

public interface FicheVisiteService {
    FicheVisiteDTO create(FicheVisiteDTO ficheVisiteDTO);
    FicheVisiteDTO update(FicheVisiteDTO ficheVisiteDTO);
    FicheVisiteDTO read(Long ficheVisiteId);
    void delete(Long ficheVisiteId);
    Page<FicheVisiteDTO> readAllFicheVisites(Map<String, String> searchParams, Pageable pageable);
    List<FicheVisiteDTO> createMultiple(List<FicheVisiteDTO> ficheVisiteDTOs);
}