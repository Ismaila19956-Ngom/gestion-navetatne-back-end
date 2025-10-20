package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.TdrDTO;

public interface TdrService {
    TdrDTO create(TdrDTO tdrDTO);

    TdrDTO update(Long id, TdrDTO tdrDTO);

    void delete(Long id);

    Page<TdrDTO> readAll(Long instructionId, String intitule, Pageable pageable);
}