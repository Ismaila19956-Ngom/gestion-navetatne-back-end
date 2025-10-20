package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.DepenseDto;

public interface DepenseService {

    DepenseDto create(DepenseDto depense);

    DepenseDto update(DepenseDto depense);

    DepenseDto read(Long id);

    void delete(Long id);

    Page<DepenseDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<DepenseDto> readByEntrepriseId(Long entrepriseId);

}