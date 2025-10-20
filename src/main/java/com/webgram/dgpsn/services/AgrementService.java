package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.AgrementDTO;

public interface AgrementService {
    AgrementDTO create(AgrementDTO agrementDTO);
    AgrementDTO update(Long id, AgrementDTO agrementDTO);
    void delete(Long id);
    Page<AgrementDTO> readAll(Long promoteurId, String objet, Pageable pageable);
}