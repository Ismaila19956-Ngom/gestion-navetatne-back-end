package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.MilieuxPollutionDTO;

import java.util.Map;

public interface MilieuxPollutionService {
    MilieuxPollutionDTO create(MilieuxPollutionDTO milieuxPollutionDTO);
    MilieuxPollutionDTO update(MilieuxPollutionDTO milieuxPollutionDTO);
    MilieuxPollutionDTO read(Long milieuxPollutionId);
    void delete(Long milieuxPollutionId);
    Page<MilieuxPollutionDTO> readAll(Map<String, String> searchParams, Pageable pageable);
    MilieuxPollutionDTO updateStatut(Long id, StatutType typeStatut);
//    void importActor(MultipartFile file, Long projectId);
//    void exportActor(PrintWriter writer);
}
