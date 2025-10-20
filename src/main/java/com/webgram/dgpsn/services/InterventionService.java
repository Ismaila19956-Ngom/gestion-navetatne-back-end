package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.InterventionDTO;

import java.util.Map;

public interface InterventionService {
    InterventionDTO create(InterventionDTO interventionDTO);
    InterventionDTO update(InterventionDTO interventionDTO);
    InterventionDTO read(Long interventionId);
    void delete(Long declarationId);
    Page<InterventionDTO> readAll(Map<String,String> searchParams, Pageable pageable);
}
