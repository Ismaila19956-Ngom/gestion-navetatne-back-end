package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.FicheRenseignementDTO;

import java.util.List;
import java.util.Map;

public interface FicheRenseignementService {
    FicheRenseignementDTO create(FicheRenseignementDTO ficheRenseignementDTO);
    FicheRenseignementDTO update(FicheRenseignementDTO ficheRenseignementDTO);
    FicheRenseignementDTO read(Long ficheRenseignementId);
    void delete(Long ficheRenseignementId);
    Page<FicheRenseignementDTO> readAllFicheRenseignements(Map<String, String> searchParams, Pageable pageable);
    List<FicheRenseignementDTO> createMultiple(List<FicheRenseignementDTO> ficheRenseignementDTOs);
}