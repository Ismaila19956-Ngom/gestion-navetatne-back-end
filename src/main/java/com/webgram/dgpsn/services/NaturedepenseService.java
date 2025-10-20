package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


import com.webgram.dgpsn.models.NaturedepenseDto;

public interface NaturedepenseService {

    NaturedepenseDto create(NaturedepenseDto naturedepense);

    NaturedepenseDto update(NaturedepenseDto naturedepense);

    NaturedepenseDto read(Long id);

    void delete(Long id);

    Page<NaturedepenseDto> readAll(Map<String, String> searchParams, Pageable pageable);

    }