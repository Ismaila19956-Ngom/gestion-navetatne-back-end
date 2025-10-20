package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SuivDeclarationDTO;

import java.util.Map;

public interface SuivDeclarationService {
    SuivDeclarationDTO create(SuivDeclarationDTO suivDeclarationDTO);
    SuivDeclarationDTO update(SuivDeclarationDTO suivDeclarationDTO);
    SuivDeclarationDTO read(Long suivDeclarationId);
    void delete(Long suivDeclarationId);
    Page<SuivDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}