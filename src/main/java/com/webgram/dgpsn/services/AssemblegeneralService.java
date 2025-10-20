package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.AssemblegeneralDto;

public interface AssemblegeneralService {

    AssemblegeneralDto create(AssemblegeneralDto assemblegeneral);

    AssemblegeneralDto update(AssemblegeneralDto assemblegeneral);

    AssemblegeneralDto read(Long id);

    void delete(Long id);

    Page<AssemblegeneralDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<AssemblegeneralDto> readByEntrepriseId(Long entrepriseId);

}