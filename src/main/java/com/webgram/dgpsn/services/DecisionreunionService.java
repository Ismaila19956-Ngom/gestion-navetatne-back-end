package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.DecisionreunionDto;

public interface DecisionreunionService {

    DecisionreunionDto create(DecisionreunionDto decisionreunion);

    DecisionreunionDto update(DecisionreunionDto decisionreunion);

    DecisionreunionDto read(Long id);

    void delete(Long id);

    Page<DecisionreunionDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<DecisionreunionDto> readByReunionId(Long reunionId);

}