package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SuiviSurveillanceDTO;

public interface SuiviSurveillanceService {
    SuiviSurveillanceDTO create(SuiviSurveillanceDTO suiviSurveillanceDTO);
    SuiviSurveillanceDTO update(Long id, SuiviSurveillanceDTO suiviSurveillanceDTO);
    void delete(Long id);
    Page<SuiviSurveillanceDTO> readAll(Long promoteurId, String intitule, Pageable pageable);
}