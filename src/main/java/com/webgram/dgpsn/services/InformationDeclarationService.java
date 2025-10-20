package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.InformationDeclarationDTO;

import java.util.Map;

public interface InformationDeclarationService {
    InformationDeclarationDTO create(InformationDeclarationDTO informationDeclarationDTO);
    InformationDeclarationDTO update(InformationDeclarationDTO informationDeclarationDTO);
    InformationDeclarationDTO read(Long informationDeclarationId);
    void delete(Long informationDeclarationId);
    Page<InformationDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable);
}