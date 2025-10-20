package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EnvironmentalIndicateurDTO;


public interface EnvironmentalIndicateurService {
    EnvironmentalIndicateurDTO create(EnvironmentalIndicateurDTO environmentalIndicateurDTO);
    EnvironmentalIndicateurDTO update(EnvironmentalIndicateurDTO environmentalIndicateurDTO);
    EnvironmentalIndicateurDTO read(Long environmentalIndicateurId);
    void delete(Long environmentalIndicateurId);
    Page<EnvironmentalIndicateurDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String valeur,
            String source,
            String startDate,
            String endDate,
            Long indicateurId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
