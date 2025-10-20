package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.IndicatorProjetDTO;

import java.io.PrintWriter;

public interface IndicatorProjetService {
    IndicatorProjetDTO create(IndicatorProjetDTO indicatorProjetDTO);
    IndicatorProjetDTO update(IndicatorProjetDTO indicatorProjetDTO);
    IndicatorProjetDTO read(Long indicatorProjetId);
    void delete(Long indicatorProjetId);
    Page<IndicatorProjetDTO> readAll(
            Pageable pageable,
            Double targetValue,
            Long indicatorId,
            Long projetId,
            Long periodicityId
    );
    void importIndicator(MultipartFile file, Long projectId);
    void exportIndicator(PrintWriter writer);
}
