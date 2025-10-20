package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.models.PollutionManagerDTO;

import java.util.Map;

public interface PollutionManagerService {
    PollutionManagerDTO create(PollutionManagerDTO pollutionManagerDTO);
    PollutionManagerDTO update(PollutionManagerDTO pollutionManagerDTO);
    PollutionManagerDTO read(Long pollutionManagerId);
    void delete(Long pollutionManagerId);
    Page<PollutionManagerDTO> readAll(Map<String, String> searchParams, Pageable pageable);
    PollutionManagerDTO updateStatut(Long id, StatutType typeStatut);

//    void importActor(MultipartFile file, Long projectId);
//    void exportActor(PrintWriter writer);
}
