package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.DecisionagDto;

public interface DecisionagService {

    DecisionagDto create(DecisionagDto decisionag);

    DecisionagDto update(DecisionagDto decisionag);

    DecisionagDto read(Long id);

    void delete(Long id);

    Page<DecisionagDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<DecisionagDto> readByAssemblegeneralId(Long assemblegeneralId);

}