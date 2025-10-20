package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.Priority;
import com.webgram.dgpsn.models.AlerteDTO;
import com.webgram.dgpsn.models.IssueLogDTO;

import java.util.Date;

public interface AlerteService {
  //  List<AlerteDTO> getAlertNotRead(long userId);
   Page<AlerteDTO> readAll(Pageable pageable, String message, Date date, String critere, Date endDate, Boolean read, Priority priority, String username);
    void generateAlertCreateIssueLog(IssueLogDTO issueLogDTO);
    void setAlertToRead(Long alertId);


}
