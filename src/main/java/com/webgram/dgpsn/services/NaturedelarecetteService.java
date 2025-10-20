package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.NaturedelarecetteDto;

public interface NaturedelarecetteService {

    NaturedelarecetteDto create(NaturedelarecetteDto naturedelarecette);

    NaturedelarecetteDto update(NaturedelarecetteDto naturedelarecette);

    NaturedelarecetteDto read(Long id);

    void delete(Long id);

    Page<NaturedelarecetteDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }