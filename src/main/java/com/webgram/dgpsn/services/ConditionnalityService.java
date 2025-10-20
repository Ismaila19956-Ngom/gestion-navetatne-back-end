package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.ConditionnalityDTO;

import java.io.PrintWriter;
import java.util.Date;

public interface ConditionnalityService {
    ConditionnalityDTO create(ConditionnalityDTO conditionnalityDTO);
    ConditionnalityDTO update(ConditionnalityDTO conditionnalityDTO);
    ConditionnalityDTO read(Long conditionnalityId);
    void delete(Long conditionnalityId);
    Page<ConditionnalityDTO> readAll(
            Pageable pageable,
            String libelle,
            Long conditionnalityTypeId,
            Long stateProgressId,
            Long agentId,
            Long projetId,
            Date date
    );

    void importConditionnalite(MultipartFile file, Long projectId);

    void exportConditionnalite(PrintWriter writer);
}
