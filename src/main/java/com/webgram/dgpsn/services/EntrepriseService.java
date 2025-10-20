package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.EntrepriseDto;

public interface EntrepriseService {

    EntrepriseDto create(EntrepriseDto entreprise);

    EntrepriseDto update(EntrepriseDto entreprise);

    EntrepriseDto read(Long id);

    void delete(Long id);

    Page<EntrepriseDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }