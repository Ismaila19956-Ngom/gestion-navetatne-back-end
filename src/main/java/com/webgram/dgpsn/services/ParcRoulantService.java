package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ParcRoulantDTO;

import java.util.Map;

public interface ParcRoulantService {
    ParcRoulantDTO create(ParcRoulantDTO parcRoulantDTO);
    ParcRoulantDTO update(ParcRoulantDTO parcRoulantDTO);
    ParcRoulantDTO read(Long parcRoulantId);
    void delete(Long parcRoulantId);
    Page<ParcRoulantDTO> readAllParcRoulants(Map<String, String> searchParams, Pageable pageable);
   }