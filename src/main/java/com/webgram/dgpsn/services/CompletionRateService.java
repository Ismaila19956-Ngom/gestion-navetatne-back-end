package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.Period;
import com.webgram.dgpsn.models.CompletionRateDTO;
import com.webgram.dgpsn.models.responses.CompletionRateResponse;

import java.util.Date;

public interface CompletionRateService {
    CompletionRateDTO create(CompletionRateDTO completionRateDTO);
    CompletionRateDTO update(CompletionRateDTO completionRateDTO);
    CompletionRateDTO read(Long id);
    void delete(Long id);
    Page<CompletionRateDTO> readAll(
            Pageable pageable, Long projetId
            , Period period, Double targetValue, Double valueReched, Integer year, Date startDate, Date endDate
    );
    CompletionRateResponse readCompletionRateByManagementUnit(Long managementUnitId, Integer year);


}
