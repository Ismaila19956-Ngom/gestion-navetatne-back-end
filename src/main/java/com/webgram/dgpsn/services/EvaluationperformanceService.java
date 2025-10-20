package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.EvaluationperformanceDto;

public interface EvaluationperformanceService {

    EvaluationperformanceDto create(EvaluationperformanceDto evaluationperformance);

    EvaluationperformanceDto update(EvaluationperformanceDto evaluationperformance);

    EvaluationperformanceDto read(Long id);

    void delete(Long id);

    Page<EvaluationperformanceDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<EvaluationperformanceDto> readByEntrepriseId(Long entrepriseId);

}