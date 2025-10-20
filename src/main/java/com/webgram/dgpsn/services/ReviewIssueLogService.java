package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.requests.ReviewIssueLogDTO;
import com.webgram.dgpsn.models.requests.ReviewIssueLogListDTO;

import java.util.Date;

public interface ReviewIssueLogService {
    void create(ReviewIssueLogListDTO reviewIssueLogListDTO);
    void create(ReviewIssueLogDTO reviewIssueLogDTO);
    void delete(Long reviewIssuelogId);
    Page<ReviewIssueLogDTO> readAll(
            Pageable pageable,Long reviewId, Long issueLogId,String libelle,
            Date identificationDate,Date resolutionDate, Long criticityId, Long statusId, Long natureId);
}
