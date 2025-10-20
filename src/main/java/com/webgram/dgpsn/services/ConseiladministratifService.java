package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.ConseiladministratifDto;

public interface ConseiladministratifService {

    ConseiladministratifDto create(ConseiladministratifDto conseiladministratif);

    ConseiladministratifDto update(ConseiladministratifDto conseiladministratif);

    ConseiladministratifDto read(Long id);

    void delete(Long id);

    Page<ConseiladministratifDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<ConseiladministratifDto> readByEntrepriseId(Long entrepriseId);

}