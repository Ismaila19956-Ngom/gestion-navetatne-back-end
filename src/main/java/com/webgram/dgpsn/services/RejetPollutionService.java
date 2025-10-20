package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.RejetPollutionDTO;

import java.util.Map;

public interface RejetPollutionService {
    RejetPollutionDTO create(RejetPollutionDTO rejetPollutionDTO);
    RejetPollutionDTO update(RejetPollutionDTO rejetPollutionDTO);
    RejetPollutionDTO read(Long rejetPollutionId);
    void delete(Long rejetPollutionId);
    Page<RejetPollutionDTO> readAll(Map<String, String> searchParams, Pageable pageable);
    RejetPollutionDTO updateStatut(Long id, StatutType typeStatut);
//    void importActor(MultipartFile file, Long projectId);
//    void exportActor(PrintWriter writer);
}
