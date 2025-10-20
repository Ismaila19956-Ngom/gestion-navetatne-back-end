package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EvaluationDTO;

import java.util.Map;

public interface EvaluationService {
    EvaluationDTO create(EvaluationDTO evaluationDTO);
    EvaluationDTO update(EvaluationDTO evaluationDTO);
    EvaluationDTO read(Long evaluationId);
    void delete(Long evaluationId);
    Page<EvaluationDTO> readAllEvaluations(Map<String, String> searchParams, Pageable pageable);
}