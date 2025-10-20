package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.PeriodeDto;

public interface PeriodeService {

    PeriodeDto create(PeriodeDto periode);

    PeriodeDto update(PeriodeDto periode);

    PeriodeDto read(Long id);

    void delete(Long id);

    Page<PeriodeDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }