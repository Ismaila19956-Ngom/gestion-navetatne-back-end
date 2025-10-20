package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.JournalDTO;

import java.time.LocalDateTime;

public interface JournalService {
   Page<JournalDTO> readAll(Pageable pageable, String user, String login, String actionType, LocalDateTime creationDate, String critere, LocalDateTime endDate);


}
