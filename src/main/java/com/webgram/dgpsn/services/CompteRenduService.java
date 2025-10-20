package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.CompteRenduDTO;

import java.util.Map;

public interface CompteRenduService {
    CompteRenduDTO create(CompteRenduDTO compteRenduDTO);
    CompteRenduDTO update(CompteRenduDTO compteRenduDTO);
    CompteRenduDTO read(Long compteRenduId);
    void delete(Long compteRenduId);
    Page<CompteRenduDTO> readAll(Map<String,String> searchParams, Pageable pageable);
}
