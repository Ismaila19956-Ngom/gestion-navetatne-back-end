package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.RecrutementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface RecrutementService {


    RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO);
    RecrutementDTO updateRecrutement(RecrutementDTO recrutementDTO);
    void deleteRecrutement(Long id);
    RecrutementDTO readRecrutement(Long id);
    Page<RecrutementDTO> readAllRecrutement(Map<String, String> searchParams, Pageable pageable);
    RecrutementDTO updateStatut(Long id, StatutType statutType);
}