package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.DecaissementDTO;

import java.util.Map;

public interface DecaissementService {
    DecaissementDTO create(DecaissementDTO decaissementDTO);
    DecaissementDTO update(DecaissementDTO decaissementDTO);
    DecaissementDTO read(Long decaissementId);
    void delete(Long decaissementId);
    Page<DecaissementDTO> readAllDecaissements(Map<String, String> searchParams, Pageable pageable);
  }