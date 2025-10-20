package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EngagementDTO;

import java.util.Map;

public interface EngagementService {
    EngagementDTO create(EngagementDTO engagementDTO);
    EngagementDTO update(EngagementDTO engagementDTO);
    EngagementDTO read(Long engagementId);
    void delete(Long engagementId);
    Page<EngagementDTO> readAllEngagements(Map<String, String> searchParams, Pageable pageable);
}