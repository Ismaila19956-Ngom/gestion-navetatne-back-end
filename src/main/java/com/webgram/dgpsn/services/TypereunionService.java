package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.TypereunionDto;

public interface TypereunionService {

    TypereunionDto create(TypereunionDto typereunion);

    TypereunionDto update(TypereunionDto typereunion);

    TypereunionDto read(Long id);

    void delete(Long id);

    Page<TypereunionDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }