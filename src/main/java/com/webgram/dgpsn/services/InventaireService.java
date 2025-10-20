package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.InventaireDTO;

import java.util.Map;

public interface InventaireService {
    InventaireDTO create(InventaireDTO inventaireDTO);
    InventaireDTO update(InventaireDTO inventaireDTO);
    InventaireDTO read(Long inventaireId);
    void delete(Long inventaireId);
    Page<InventaireDTO> readAllInventaires(Map<String, String> searchParams, Pageable pageable);
    }