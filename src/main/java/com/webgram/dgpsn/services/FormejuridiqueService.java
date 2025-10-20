package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.FormejuridiqueDto;

public interface FormejuridiqueService {

    FormejuridiqueDto create(FormejuridiqueDto formejuridique);

    FormejuridiqueDto update(FormejuridiqueDto formejuridique);

    FormejuridiqueDto read(Long id);

    void delete(Long id);

    Page<FormejuridiqueDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }