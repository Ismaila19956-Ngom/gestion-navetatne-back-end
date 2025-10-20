package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EnvironmentalImpactDTO;


public interface EnvironmentalImpactService {
    EnvironmentalImpactDTO create(EnvironmentalImpactDTO environmentalImpactDTO);
    EnvironmentalImpactDTO update(EnvironmentalImpactDTO environmentalImpactDTO);
    EnvironmentalImpactDTO read(Long environmentalImpactId);
    void delete(Long environmentalImpactId);
    Page<EnvironmentalImpactDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
