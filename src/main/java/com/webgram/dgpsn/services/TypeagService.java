package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.TypeagDto;

public interface TypeagService {

    TypeagDto create(TypeagDto typeag);

    TypeagDto update(TypeagDto typeag);

    TypeagDto read(Long id);

    void delete(Long id);

    Page<TypeagDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }