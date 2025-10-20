package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SuiviInventaireDTO;

import java.util.Map;

public interface SuiviInventaireService {
    SuiviInventaireDTO create(SuiviInventaireDTO suiviInventaireDTO);
    SuiviInventaireDTO update(SuiviInventaireDTO suiviInventaireDTO);
    SuiviInventaireDTO read(Long suiviInventaireId);
    void delete(Long suiviInventaireId);
    Page<SuiviInventaireDTO> readAllSuiviInventaires(Map<String, String> searchParams, Pageable pageable);
   }