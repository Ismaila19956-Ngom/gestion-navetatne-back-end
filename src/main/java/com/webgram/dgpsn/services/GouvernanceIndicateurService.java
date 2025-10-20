package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.GouvernanceIndicateurDTO;


public interface GouvernanceIndicateurService {
    GouvernanceIndicateurDTO create(GouvernanceIndicateurDTO gouvernanceIndicateurDTO);
    GouvernanceIndicateurDTO update(GouvernanceIndicateurDTO gouvernanceIndicateurDTO);
    GouvernanceIndicateurDTO read(Long gouvernanceIndicateurId);
    void delete(Long gouvernanceIndicateurId);
    Page<GouvernanceIndicateurDTO> readAll(
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
