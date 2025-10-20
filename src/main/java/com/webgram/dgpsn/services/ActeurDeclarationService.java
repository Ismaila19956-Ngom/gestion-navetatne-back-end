package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ActeurDeclarationDTO;

import java.util.Map;

public interface ActeurDeclarationService {
    ActeurDeclarationDTO create(ActeurDeclarationDTO acteurDeclarationDTO);
    ActeurDeclarationDTO update(ActeurDeclarationDTO acteurDeclarationDTO);
    ActeurDeclarationDTO read(Long acteurDeclarationId);
    void delete(Long acteurDeclarationId);
    Page<ActeurDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}