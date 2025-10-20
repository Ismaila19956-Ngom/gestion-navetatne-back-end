package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.IndicatorDTO;

public interface IndicatorService {
    IndicatorDTO create(IndicatorDTO indicatorDTO);
    IndicatorDTO update(IndicatorDTO indicatorDTO);
    IndicatorDTO read(Long indicatorId);
    void delete(Long indicatorId);
    Page<IndicatorDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Long unitId,
            Long indicatorTypeId,
            String sortBy,
            Boolean ascending
    );
}
