package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.RapportDTO;

import java.util.Map;

public interface RapportDeclarationService {
    RapportDTO create(RapportDTO rapportDTO);
    RapportDTO update(RapportDTO rapportDTO);
    RapportDTO read(Long rapportId);
    void delete(Long rapportId);
    Page<RapportDTO> readAll(Map<String,String> searchParams, Pageable pageable);
}
