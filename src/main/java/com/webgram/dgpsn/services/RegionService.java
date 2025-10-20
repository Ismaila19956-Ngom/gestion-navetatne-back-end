package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.RegionDto;

public interface RegionService {

    RegionDto create(RegionDto region);

    RegionDto update(RegionDto region);

    RegionDto read(Long id);

    void delete(Long id);

    Page<RegionDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }