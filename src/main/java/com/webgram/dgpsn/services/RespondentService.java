package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.RespondentDTO;

import java.util.Date;


public interface RespondentService {
    RespondentDTO create(RespondentDTO respondentDTO);
    RespondentDTO update(RespondentDTO respondentDTO);
    RespondentDTO read(Long respondentId);
    void delete(Long respondentId);
    Page<RespondentDTO> readAll(Pageable pageable, String firstName, String lastName, String phone, Date startDate, Date endDate, Long fundingId);
}
