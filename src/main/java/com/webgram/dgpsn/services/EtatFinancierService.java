package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EtatFinancierDto;

import java.util.List;
import java.util.Map;

public interface EtatFinancierService {

    EtatFinancierDto create(EtatFinancierDto etatFinancierDto);

    EtatFinancierDto update(EtatFinancierDto etatFinancierDto);

    EtatFinancierDto read(Long id);

    void delete(Long id);

    Page<EtatFinancierDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<EtatFinancierDto> readByEntrepriseId(Long entrepriseId);

}