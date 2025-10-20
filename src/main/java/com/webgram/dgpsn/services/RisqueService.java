package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.RisqueDto;

public interface RisqueService {

    RisqueDto create(RisqueDto risque);

    RisqueDto update(RisqueDto risque);

    RisqueDto read(Long id);

    void delete(Long id);

    Page<RisqueDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }