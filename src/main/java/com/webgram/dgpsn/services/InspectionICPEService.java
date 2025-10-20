package com.webgram.dgpsn.services;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.InspectionICPEDTO;

import java.io.IOException;
import java.util.Date;

public interface InspectionICPEService {

    InspectionICPEDTO create(InspectionICPEDTO inspectionICPEDTO, MultipartFile[] photos, MultipartFile[] documents, MultipartFile[] analysisReports) throws IOException;

    InspectionICPEDTO update(InspectionICPEDTO inspectionICPEDTO, MultipartFile[] photos, MultipartFile[] documents, MultipartFile[] analysisReports) throws IOException;

    InspectionICPEDTO read(Long inspectionICPEId);

    void delete(Long inspectionICPEId);

    Page<InspectionICPEDTO> readAll(
            Pageable pageable,
            String code,
            Date dateInspection,
            Long typeInspectionId,
            String ref,
            Long etablissementId,
            Long teamLeadId,
            Long complianceLevelId,
            Long environmentalRiskId,
            String preparedBy,
            Date preparationDate,
            String sortBy,
            Boolean ascending
    );

    Resource downloadFile(Long inspectionICPEId, String docType) throws IOException;
}