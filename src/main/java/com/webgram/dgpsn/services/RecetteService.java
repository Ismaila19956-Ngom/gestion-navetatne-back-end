package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.RecetteDto;

public interface RecetteService {

    RecetteDto create(RecetteDto recette);

    RecetteDto update(RecetteDto recette);

    RecetteDto read(Long id);

    void delete(Long id);

    Page<RecetteDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<RecetteDto> readByEntrepriseId(Long entrepriseId);

}