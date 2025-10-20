package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


import com.webgram.dgpsn.models.CessionacquisitionDto;

public interface CessionacquisitionService {

    CessionacquisitionDto create(CessionacquisitionDto cessionacquisition);

    CessionacquisitionDto update(CessionacquisitionDto cessionacquisition);

    CessionacquisitionDto read(Long id);

    void delete(Long id);

    Page<CessionacquisitionDto> readAll(Map<String, String> searchParams, Pageable pageable);

    
    List<CessionacquisitionDto> readByEntrepriseId(Long entrepriseId);

}