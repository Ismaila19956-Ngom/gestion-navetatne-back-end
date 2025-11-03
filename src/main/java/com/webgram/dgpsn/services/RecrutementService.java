package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.RecrutementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RecrutementService {


    CandidatDTO addCandidat(Long idRecrutement, CandidatDTO dto);

    List<CandidatDTO> getCandidatsRecrutements(Long idRecrutement);


    CandidatDTO updateCandidat(Long idRecrutement, Long idCandidat, CandidatDTO dto);

    RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO);
    RecrutementDTO updateRecrutement(RecrutementDTO recrutementDTO);
    void deleteRecrutement(Long id);
    RecrutementDTO readRecrutement(Long id);
    Page<RecrutementDTO> readAllRecrutement(Map<String, String> searchParams, Pageable pageable);
    RecrutementDTO updateStatut(Long id, StatutType statutType);


}