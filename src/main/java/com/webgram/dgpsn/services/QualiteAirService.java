package com.webgram.dgpsn.services;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.QualiteAirDTO;

import java.io.IOException;
import java.util.Date;

public interface QualiteAirService {

    QualiteAirDTO create(QualiteAirDTO qualiteAirDTO, MultipartFile[] bulletinsMonthly, MultipartFile[] bulletinsQuarterly, MultipartFile[] bulletinsAnnual, MultipartFile[] analysisReports) throws IOException;

    QualiteAirDTO update(QualiteAirDTO qualiteAirDTO, MultipartFile[] bulletinsMonthly, MultipartFile[] bulletinsQuarterly, MultipartFile[] bulletinsAnnual, MultipartFile[] analysisReports) throws IOException;

    QualiteAirDTO read(Long qualiteAirId);

    void delete(Long qualiteAirId);

    Page<QualiteAirDTO> readAll(
            Pageable pageable,
            Long stationId,
            Date measurementDate,
            String iqa,
            Long mainPollutantId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    );

    Resource downloadFile(Long qualiteAirId, String docType) throws IOException;
}