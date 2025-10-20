package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.models.IssueLogDTO;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

public interface IssueLogService {
    IssueLogDTO create(IssueLogDTO issueLogDTO);
//    IssueLogDTO updateIssueLogRisk(IssueLogRisksDTO issueLogRisksDTO);
//    void deleteIssueLogRisk(Long issueLogRiskId);
    IssueLogDTO update(IssueLogDTO issueLogDTO);
    IssueLogDTO read(Long issueLogId);
    void delete(Long issueLogId);
    Page<IssueLogDTO> readAll(
            Pageable pageable,
            String libelle,
            String description,
            String author,
            Date identificationDate,
            Date deadline,
            Date resolutionDate,
            Long projetId,
            Long assignmentId,
            Long criticityId,
            Long delayImpactId,
            Long financialImpactId,
            Long statusId,
            Long natureId
    );

    void importIssuelog(MultipartFile file, Long projectId);

    void export(PrintWriter writer);

    List<IssueLogDTO> readListIssueLog(Long projectId);
    void valid(IssueLogDTO issueLogDTO);


}
