package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.ProcedurenominationDto;

public interface ProcedurenominationService {

    ProcedurenominationDto create(ProcedurenominationDto procedurenomination);

    ProcedurenominationDto update(ProcedurenominationDto procedurenomination);

    ProcedurenominationDto read(Long id);

    void delete(Long id);

    Page<ProcedurenominationDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }