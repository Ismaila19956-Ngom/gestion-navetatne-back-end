package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.OrdonnancementDTO;

import java.util.Map;

public interface OrdonnancementService {
    OrdonnancementDTO create(OrdonnancementDTO ordonnancementDTO);
    OrdonnancementDTO update(OrdonnancementDTO ordonnancementDTO);
    OrdonnancementDTO read(Long ordonnancementId);
    void delete(Long ordonnancementId);
    Page<OrdonnancementDTO> readAllOrdonnancements(Map<String, String> searchParams, Pageable pageable);
   }