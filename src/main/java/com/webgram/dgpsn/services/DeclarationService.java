package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.DeclarationDTO;

import java.util.Map;

public interface DeclarationService {
    DeclarationDTO create(DeclarationDTO declarationDTO);
    DeclarationDTO update(DeclarationDTO declarationDTO);
    DeclarationDTO read(Long declarationId);
    void delete(Long declarationId);
    Page<DeclarationDTO> readAll(Map<String,String> searchParams, Pageable pageable);
}
