package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.InstructionDTO;
import com.webgram.dgpsn.models.requests.UpdateEtapeDTO;

public interface InstructionService {
    InstructionDTO create(InstructionDTO instructionDTO);
    InstructionDTO update(Long id, InstructionDTO instructionDTO);
    void delete(Long id);
    Page<InstructionDTO> readAll(Long promoteurId, Long regionId, String intitule, Pageable pageable);
    InstructionDTO updateEtape(Long instructionId, UpdateEtapeDTO etapeDTO);

}