package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EnvironmentalConformiteDTO;


public interface EnvironmentalConformiteService {
    EnvironmentalConformiteDTO create(EnvironmentalConformiteDTO environmentalConformiteDTO);
    EnvironmentalConformiteDTO update(EnvironmentalConformiteDTO environmentalConformiteDTO);
    EnvironmentalConformiteDTO read(Long environmentalConformiteId);
    void delete(Long environmentalConformiteId);
    Page<EnvironmentalConformiteDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String etat,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeReferenceId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
