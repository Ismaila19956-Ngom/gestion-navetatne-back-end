package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.TacheEntity.StatutTache;
import com.webgram.dgpsn.models.TacheDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public interface TacheService {
    TacheDto create(TacheDto dto);
    TacheDto read(Long id);
    TacheDto update(TacheDto dto);
    void delete(Long id);
    Page<TacheDto> readAll(Map<String, String> searchParams, Pageable pageable);
    List<TacheDto> readByActiviteId(Long activiteId);
    TacheDto updateStatut(Long id, StatutTache statut);
    void exportTache(PrintWriter writer);
}
