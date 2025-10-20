package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.MembreDto;

public interface MembreService {

    MembreDto create(MembreDto membre);

    MembreDto update(MembreDto membre);

    MembreDto read(Long id);

    void delete(Long id);

    Page<MembreDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<MembreDto> readByConseiladministratifId(Long conseiladministratifId);

}