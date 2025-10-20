package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.EvaluationfinanciereDto;

public interface EvaluationfinanciereService {

    EvaluationfinanciereDto create(EvaluationfinanciereDto evaluationfinanciere);

    EvaluationfinanciereDto update(EvaluationfinanciereDto evaluationfinanciere);

    EvaluationfinanciereDto read(Long id);

    void delete(Long id);

    Page<EvaluationfinanciereDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<EvaluationfinanciereDto> readByEntrepriseId(Long entrepriseId);

}