package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.PreparationDTO;

import java.io.PrintWriter;
import java.util.Date;

public interface PreparationService {
    PreparationDTO create(PreparationDTO preparationDTO);
    PreparationDTO update(PreparationDTO preparationDTO);
    PreparationDTO read(Long preparationId);
    void delete(Long preparationId);
    Page<PreparationDTO> readAll(
            Pageable pageable,
            String libelle,
            Date deadline,
            Long phaseId,
            Long agentId,
            Long projetId
    );

    void importPreparation(MultipartFile file, Long porjectId);
    void exportPreparation(PrintWriter writer);
}
