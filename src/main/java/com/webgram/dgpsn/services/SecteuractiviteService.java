package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.SecteuractiviteDto;

public interface SecteuractiviteService {

    SecteuractiviteDto create(SecteuractiviteDto secteuractivite);

    SecteuractiviteDto update(SecteuractiviteDto secteuractivite);

    SecteuractiviteDto read(Long id);

    void delete(Long id);

    Page<SecteuractiviteDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }