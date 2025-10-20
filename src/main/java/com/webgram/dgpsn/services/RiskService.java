package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.RiskDTO;

import java.io.PrintWriter;
import java.util.Date;

public interface RiskService {
    RiskDTO create(RiskDTO riskDTO);
    RiskDTO update(RiskDTO riskDTO);
    RiskDTO read(Long issueLogId);
    void delete(Long issueLogId);
    Page<RiskDTO> readAll(
            Pageable pageable,
            String libelle,
            String author,
            String criticity,
            Long delayImpactId,
            Long projetId,
            Long financialImpactId,
            Long statusId,
            Date identificationDate,
            Date resolutionDate,
            Double probability,
            Long natureId
    );
    void importRisk(MultipartFile file, Long projectId);
    void export(PrintWriter writer);
}
