package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.StationDTO;

public interface StationService {

    StationDTO create(StationDTO stationDTO);

    StationDTO update(StationDTO stationDTO);

    StationDTO read(Long stationId);

    void delete(Long stationId);

    Page<StationDTO> readAll(
            Pageable pageable,
            String code,
            String name,
            Long typeId,
            Long regionId,
            Long departementId,
            String sortBy,
            Boolean ascending
    );
}