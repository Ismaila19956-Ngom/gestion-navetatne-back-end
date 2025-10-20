package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.RepertoireDeclarationDTO;

import java.util.Map;

public interface RepertoireDeclarationService {
    RepertoireDeclarationDTO create(RepertoireDeclarationDTO repertoireDeclarationDTO);
    RepertoireDeclarationDTO update(RepertoireDeclarationDTO repertoireDeclarationDTO);
    RepertoireDeclarationDTO read(Long repertoireDeclarationId);
    void delete(Long repertoireDeclarationId);
    Page<RepertoireDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}