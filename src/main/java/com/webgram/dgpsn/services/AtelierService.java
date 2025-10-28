package com.webgram.dgpsn.services;
import com.webgram.dgpsn.models.AtelierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface AtelierService {
    AtelierDTO create(AtelierDTO dto);
    AtelierDTO update(AtelierDTO dto);
    AtelierDTO read(Long id);
    void delete(Long id);
    Page<AtelierDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}
