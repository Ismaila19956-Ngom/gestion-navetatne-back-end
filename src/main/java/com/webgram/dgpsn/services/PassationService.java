package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PassationDTO;

import java.util.Map;

public interface PassationService {
    PassationDTO create(PassationDTO passationDTO);
    PassationDTO update(PassationDTO passationDTO);
    PassationDTO read(Long passationId);
    void delete(Long passationId);
    Page<PassationDTO> readAllPassations(Map<String, String> searchParams, Pageable pageable);
}