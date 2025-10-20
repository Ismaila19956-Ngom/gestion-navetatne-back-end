package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.DetailType;
import com.webgram.dgpsn.models.ImpactsAndObjectiveDTO;


public interface ImpactsAndObjectiveService {
    ImpactsAndObjectiveDTO create(ImpactsAndObjectiveDTO impactsAndObjectiveDTO);
    ImpactsAndObjectiveDTO update(ImpactsAndObjectiveDTO impactsAndObjectiveDTO);
    ImpactsAndObjectiveDTO read(Long managementUnitId);
    void delete(Long labelId);
    Page<ImpactsAndObjectiveDTO> readAll(Pageable pageable, DetailType detailType, String description, Long managementUnitId);
}
