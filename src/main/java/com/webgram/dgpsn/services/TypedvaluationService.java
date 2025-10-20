package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.TypedvaluationDto;

public interface TypedvaluationService {

    TypedvaluationDto create(TypedvaluationDto typedvaluation);

    TypedvaluationDto update(TypedvaluationDto typedvaluation);

    TypedvaluationDto read(Long id);

    void delete(Long id);

    Page<TypedvaluationDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }