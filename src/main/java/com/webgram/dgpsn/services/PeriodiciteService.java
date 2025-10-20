package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.PeriodiciteDto;

public interface PeriodiciteService {

    PeriodiciteDto create(PeriodiciteDto periodicite);

    PeriodiciteDto update(PeriodiciteDto periodicite);

    PeriodiciteDto read(Long id);

    void delete(Long id);

    Page<PeriodiciteDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<PeriodiciteDto> readByPeriodeId(Long periodeId);

}