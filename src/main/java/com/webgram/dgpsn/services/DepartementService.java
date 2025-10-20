package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.DepartementDto;

public interface DepartementService {

    DepartementDto create(DepartementDto departement);

    DepartementDto update(DepartementDto departement);

    DepartementDto read(Long id);

    void delete(Long id);

    Page<DepartementDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<DepartementDto> readByRegionId(Long regionId);

}